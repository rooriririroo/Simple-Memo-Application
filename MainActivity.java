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

    SQLiteDatabase db;
    DBHelper helper;
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
        helper = new DBHelper(this);

        listView.setAdapter(adapter);
        //listItemArrayList.add(new ListItem("2018년12월9일","hello"));

        load_values();
    }

    public void writeButton (View v) {
        Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
        startActivity(intent);
    }

    public void load_values() {

        db = helper.getReadableDatabase();
        int recordCount = -1;
        if(db != null) {
            Cursor c = db.rawQuery(ContractDB.SQL_SELECT,null);
            recordCount = c.getCount();
            listItemArrayList.clear();
            for(int i = 0; i<recordCount; i++) {
                c.moveToNext();
                String dateStr = c.getString(1);
                String textStr = c.getString(2);
                listItemArrayList.add(new ListItem(dateStr,textStr));
            }
            c.close();
            adapter.notifyDataSetChanged();
        }

        //original
        /*
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(ContractDB.SQL_SELECT,null);

        if(cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            while(cursor.moveToNext()) {
                String output_date = cursor.getString(1);
                String output_write = cursor.getString(2);
                listItemArrayList.add(new ListItem(output_date, output_write));
            }
            cursor.close();
        }
        */
        //listItemArrayList.clear();
        // adapter.notifyDataSetChanged();
    }
}


