package com.project;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
public class UpdateActivity extends AppCompatActivity {

    String ServerUrl = "https://getatyourservice.info/project/update.php";
    ProgressDialog progressDialog;
    String finalResult;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    EditText name, price, storage, description;
    Button UpdateButton;
    String txtId, txtName, txtPrice, txtStorage, txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name=(EditText)findViewById(R.id.name);
        price=(EditText)findViewById(R.id.price);
        storage=(EditText)findViewById(R.id.storage);
        description=(EditText)findViewById(R.id.description);

        UpdateButton = (Button)findViewById(R.id.buttonUpdate);

        txtId = getIntent().getStringExtra("id");
        txtName = getIntent().getStringExtra("name");
        txtPrice = getIntent().getStringExtra("price");
        txtStorage = getIntent().getStringExtra("storage");
        txtDescription = getIntent().getStringExtra("description");

        name.setText(txtName);
        price.setText(txtPrice);
        storage.setText(txtStorage);
        description.setText(txtDescription);

        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetDataFromEditText();

              RecordUpdate(txtId,txtName,txtPrice, txtStorage, txtDescription);

            }
        });
    }

    public void GetDataFromEditText(){
        txtName = name.getText().toString();
        txtPrice = price.getText().toString();
        txtStorage = storage.getText().toString();
        txtDescription= description.getText().toString();
    }

    public void RecordUpdate(final String ID, final String Name, final String Price, final String Storage, final String Description){

        class RecordUpdateClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(UpdateActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(UpdateActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("id",params[0]);

                hashMap.put("name",params[1]);

                hashMap.put("price",params[2]);

                hashMap.put("storage",params[3]);

                hashMap.put("description",params[4]);

                finalResult = httpParse.postRequest(hashMap, ServerUrl);

                return finalResult;
            }
        }

        RecordUpdateClass RecordUpdateClass = new RecordUpdateClass();

        RecordUpdateClass.execute(ID,Name,Price,Storage,Description);
    }
}