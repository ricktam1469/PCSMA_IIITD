package com.neha.iiitdapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_details);
    }

    public void qqq(View v){
        Intent intent=new Intent(Activity2.this, MainActivity.class);
        startActivity(intent);
        finish();


    }
}
