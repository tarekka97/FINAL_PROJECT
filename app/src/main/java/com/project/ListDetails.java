package com.project;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;


public class ListDetails extends AppCompatActivity {

    HttpParse httpParse = new HttpParse();
    ProgressDialog pDialog;
    String ServerUrl =  "https://getatyourservice.info/project/filter_data.php";
    HashMap<String,String> hashMap = new HashMap<>();
    String ParseResult ;
    HashMap<String,String> ResultHash = new HashMap<>();
    String FinalJSonObject ;
    TextView name,price,storage, description;
    String txtName, txtPrice, txtStorage, txtDescription;
    String TempItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_details);

        name = (TextView)findViewById(R.id.name);
        price = (TextView)findViewById(R.id.price);
        storage = (TextView)findViewById(R.id.storage);
        description= (TextView)findViewById(R.id.description);


        TempItem = getIntent().getStringExtra("ListViewValue");

        HttpWebCall(TempItem);

    }


    public void HttpWebCall(final String PreviousListViewClickedItem){

        class HttpWebCallFunction extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = ProgressDialog.show(ListDetails.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                pDialog.dismiss();

                FinalJSonObject = httpResponseMsg ;

                new GetHttpResponse(ListDetails.this).execute();

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
                            txtDescription= jsonObject.getString("description").toString();

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
        protected void onPostExecute(Void result) {
            name.setText(txtName);
            price.setText(txtPrice);
            storage.setText(txtStorage);
            description.setText(txtDescription);

        }
    }
}

