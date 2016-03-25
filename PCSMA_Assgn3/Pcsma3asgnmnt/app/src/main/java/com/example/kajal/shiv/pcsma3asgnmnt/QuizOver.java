package com.example.kajal.shiv.pcsma3asgnmnt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class QuizOver extends AppCompatActivity {

    TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_over);

        status=(TextView) findViewById(R.id.status);
        Intent data = getIntent();
        status.setText((data.getExtras().get("status")).toString());
    }

}
