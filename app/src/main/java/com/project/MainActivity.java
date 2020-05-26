package com.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton view,insert, delete, update;
    private Button submit;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        view = (RadioButton) findViewById(R.id.view);
        insert = (RadioButton) findViewById(R.id.add);
        delete = (RadioButton) findViewById(R.id.delete);
        update = (RadioButton) findViewById(R.id.update);
        submit = (Button) findViewById(R.id.buttonSubmit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.isChecked()) {
                   intent = new Intent(MainActivity.this, ViewActivity.class);
                } else if (insert.isChecked()) {
                    intent = new Intent(MainActivity.this, InsertActivity.class);
                } else if (delete.isChecked()){
                    intent = new Intent(MainActivity.this, ViewDelete.class);
                } else if (update.isChecked()){
                    intent = new Intent(MainActivity.this, ViewUpdate.class);
                }
                  startActivity(intent);
            }
        });
    }

}
