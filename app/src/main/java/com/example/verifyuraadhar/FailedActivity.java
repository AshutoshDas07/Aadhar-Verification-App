package com.example.verifyuraadhar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FailedActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failedresult);
        textView=findViewById(R.id.failed_status);
        Intent i=getIntent();
        textView.setText(i.getStringExtra("status"));
    }
}
