package com.example.soyeonlee.myapplication8;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("dateDetail",listItemArrayList.get(position).getDate());
                intent.putExtra("writeDetail",listItemArrayList.get(position).getText());
                intent.putExtra("positionDetail",position);
                startActivity(intent);
            }
        });

        /*
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int p = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("");
                builder.setMessage("글을 삭제하시겠습니까?");

                // 글 삭제하고 나가기
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        delete_values(p);
                        Toast.makeText(getApplicationContext(),"Delete",Toast.LENGTH_SHORT).show();
                    }
                });

                // 기존 상세 내용 창으로 돌아가기
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
                return false;
            }
        });*/
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
            /////////

            Comparator<ListItem> dateSort = new Comparator<ListItem>() {
                @Override
                public int compare(ListItem o1, ListItem o2) {
                    return o2.getDate().compareTo(o1.getDate());
                }
            };
            Collections.sort(listItemArrayList,dateSort);

            /////////
            adapter.notifyDataSetChanged();
        }
    }

    public void delete_values(int p) {
        db = helper.getWritableDatabase();
        Cursor c = null;
        String sqlSelect = ContractDB.SQL_SELECT;
        c = db.rawQuery(sqlSelect,null);
        c.moveToPosition(p);
        int id = c.getInt(0);
        String sqlDelete = ContractDB.SQL_DELETE + id;
        db.execSQL(sqlDelete);
        db.close();

        listItemArrayList.remove(p);


        adapter.notifyDataSetChanged();
    }
}


