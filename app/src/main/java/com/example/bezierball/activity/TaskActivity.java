package com.example.bezierball.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bezierball.R;
import com.example.bezierball.custom_view.BezierBall;

public class TaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        BezierBall bezierBall = findViewById(R.id.bezierBall);
    }
}
