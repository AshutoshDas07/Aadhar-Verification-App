package com.example.verifyuraadhar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    TextView status_text,age_text,number_text,gender_text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        status_text=findViewById(R.id.status);
        age_text=findViewById(R.id.age_container);
        gender_text=findViewById(R.id.gender_container);
        number_text=findViewById(R.id.mobile_container);
        Intent i=getIntent();
        String statusset=i.getStringExtra("status_text");
        String ageset=i.getStringExtra("age_text");
        String genderset=i.getStringExtra("gender_text");
        String numberset=i.getStringExtra("number_text");
        status_text.setText(statusset);
        age_text.setText(ageset);
        gender_text.setText(genderset);
        number_text.setText(numberset);
    }
}
