package com.project;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewUpdate extends AppCompatActivity {
    ListView ListView;
    String ServerURL;
    List<String> IdList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_update);
        ServerURL = "https://getatyourservice.info/project/view_all.php";

        ListView = (ListView)findViewById(R.id.listView);

        new GetHttpResponse(ViewUpdate.this).execute();


        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ViewUpdate.this, DetailsUpdate.class);
                intent.putExtra("ListViewValue", IdList.get(position).toString());
                startActivity(intent);
                finish();
            }
        });
    }


    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

        String JSonResult;

        List<Phones> phonesList;

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
            HttpServicesClass httpServicesClass = new HttpServicesClass(ServerURL);
            try
            {
                httpServicesClass.ExecutePostRequest();

                if(httpServicesClass.getResponseCode() == 200)
                {
                    JSonResult = httpServicesClass.getResponse();

                    if(JSonResult != null)
                    {
                        JSONArray jsonArray = null;

                        try {
                            jsonArray = new JSONArray(JSonResult);

                            JSONObject jsonObject;

                            Phones phones;

                            phonesList = new ArrayList<Phones>();

                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                phones = new Phones();

                                jsonObject = jsonArray.getJSONObject(i);

                                IdList.add(jsonObject.getString("id").toString());

                                phones.PhoneName = jsonObject.getString("name").toString();

                                phonesList.add(phones);

                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    Toast.makeText(context, httpServicesClass.getErrorMessage(), Toast.LENGTH_SHORT).show();
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
            ListView.setVisibility(View.VISIBLE);

            ListAdapterClass adapter = new ListAdapterClass(phonesList, context);

            ListView.setAdapter(adapter);

        }
    }
}

