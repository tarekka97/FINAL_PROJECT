package com.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InsertActivity extends AppCompatActivity {
    String txtName, txtPrice, txtStorage, txtDescription;
    String ServerURL , finalResult;
    EditText name, price, storage, description;
    Button Insert;
    Intent intent;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        ServerURL = "https://getatyourservice.info/project/insert.php";

        name = (EditText)findViewById(R.id.name);
        price =  (EditText)findViewById(R.id.price);
        storage =  (EditText)findViewById(R.id.storage);
        description =  (EditText)findViewById(R.id.description);
        Insert = findViewById(R.id.InsertButton);

        Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetData();
                Insert(txtName,txtPrice, txtStorage, txtDescription);
                intent = new Intent(InsertActivity.this, ViewActivity.class);
                startActivity(intent);
            }
        });
    }

    public void GetData(){
        txtName = name.getText().toString();
        txtPrice= price.getText().toString();
        txtStorage= storage.getText().toString();
        txtDescription= description.getText().toString();
    }

    public void Insert(final String name,final String price,final String storage, final String description) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                hashMap.put("name",params[0]);
                hashMap.put("price",params[1]);
                hashMap.put("storage",params[2]);
                hashMap.put("description",params[3]);

                finalResult = httpParse.postRequest(hashMap, ServerURL);

                return finalResult;

            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Toast.makeText(InsertActivity.this, "Data inserted successfully", Toast.LENGTH_LONG).show();
            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(name, price, storage, description);
     }
}
