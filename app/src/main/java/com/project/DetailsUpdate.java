package com.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DetailsUpdate extends AppCompatActivity {
    HttpParse httpParse = new HttpParse();
    ProgressDialog pDialog;
    String ServerUrl =  "https://getatyourservice.info/project/filter_data.php";
    HashMap<String,String> hashMap = new HashMap<>();
    String ParseResult ;
    HashMap<String,String> ResultHash = new HashMap<>();
    String FinalJSonObject ;
    TextView name,price, storage, description;
    String txtName, txtPrice, txtStorage, txtDescription;
    Button UpdateButton;
    String TempItem;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_update);

        name=(TextView)findViewById(R.id.name);
        price=(TextView)findViewById(R.id.price);
        storage=(TextView)findViewById(R.id.storage);
        description=(TextView)findViewById(R.id.description);

        UpdateButton = (Button)findViewById(R.id.buttonUpdate);

        TempItem = getIntent().getStringExtra("ListViewValue");
        HttpWebCall(TempItem);

        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(DetailsUpdate.this,UpdateActivity.class);

                intent.putExtra("id", TempItem);
                intent.putExtra("name", txtName);
                intent.putExtra("price", txtPrice);
                intent.putExtra("storage", txtStorage);
                intent.putExtra("description", txtDescription);

                startActivity(intent);

                finish();

            }
        });
    }

    public void HttpWebCall(final String PreviousListViewClickedItem){

        class HttpWebCallFunction extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = ProgressDialog.show(DetailsUpdate.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                pDialog.dismiss();
                FinalJSonObject = httpResponseMsg ;
                new DetailsUpdate.GetHttpResponse(DetailsUpdate.this).execute();

            }

            @Override
            protected String doInBackground(String... params) {

                ResultHash.put("PhoneID",params[0]);

                ParseResult = httpParse.postRequest(ResultHash, ServerUrl);

                return ParseResult;
            }
        }

        HttpWebCallFunction httpWebCallFunction = new HttpWebCallFunction();

        httpWebCallFunction.execute(PreviousListViewClickedItem);
    }

    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

        public GetHttpResponse(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            try
            {
                if(FinalJSonObject != null)
                {
                    JSONArray jsonArray = null;

                    try {
                        jsonArray = new JSONArray(FinalJSonObject);

                        JSONObject jsonObject;

                        for(int i=0; i<jsonArray.length(); i++)
                        {
                            jsonObject = jsonArray.getJSONObject(i);

                            txtName = jsonObject.getString("name").toString() ;
                            txtPrice = jsonObject.getString("price").toString() ;
                            txtStorage = jsonObject.getString("storage").toString() ;
                            txtDescription= jsonObject.getString("description").toString() ;

                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            name.setText(txtName);
            price.setText(txtPrice);
            storage.setText(txtStorage);
            description.setText(txtDescription);

        }
    }
}
