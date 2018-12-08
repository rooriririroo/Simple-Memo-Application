package com.example.soyeonlee.myapplication7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void writeButton (View v) {
        Intent intent = new Intent(getApplicationContext(),WriteActivity.class);
        startActivity(intent);
    }
}
