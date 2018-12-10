package com.example.soyeonlee.myapplication8;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ListItemAdapter adapter;
    ArrayList<ListItem> listItemArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listItemArrayList = new ArrayList<ListItem>();
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ListItemAdapter( MainActivity.this,listItemArrayList);

        listView.setAdapter(adapter);
        listItemArrayList.add(new ListItem("2018년12월9일","hello"));
        listItemArrayList.add(new ListItem("2018년12월10일","hello"));

    }

    public void writeButton (View v) {
        Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
        startActivity(intent);
    }

}
