package com.example.soyeonlee.myapplication8;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    SQLiteDatabase db;
    DBHelper helper;

    ListItemAdapter adapter;
    ArrayList<ListItem> listItemArrayList;

    TextView date_detailView;
    ImageView image_detailView;
    TextView write_detailView;

    String dateDetail;
    String imageDetail;
    String writeDetail;
    int positionDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        date_detailView = (TextView) findViewById(R.id.date_detailView);
        image_detailView = (ImageView) findViewById(R.id.image_detailView);
        write_detailView = (TextView) findViewById(R.id.write_detailView);

        helper = new DBHelper(this);
        listItemArrayList = new ArrayList<ListItem>();

        Intent intent = getIntent();

        dateDetail = intent.getStringExtra("dateDetail");
        date_detailView.setText(dateDetail);

        imageDetail = intent.getStringExtra("imageDetail");
        Glide.with(this).load(imageDetail).into(image_detailView);

        writeDetail = intent.getStringExtra("writeDetail");
        write_detailView.setText(writeDetail);

        positionDetail = intent.getIntExtra("positionDetail",0);
    }

    public void modifyClick(View v) {
        Intent intent = new Intent(getApplicationContext(),ModifyActivity.class);
        intent.putExtra("dateModify",dateDetail);
        intent.putExtra("imageModify",imageDetail);
        intent.putExtra("writeModify",writeDetail);
        intent.putExtra("positionModify",positionDetail);
        startActivity(intent);
    }

    public void deleteClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("글을 삭제하시겠습니까?");

        // 글 삭제하고 나가기
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                delete_values(positionDetail);
                Toast.makeText(getApplicationContext(),"삭제",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        // 기존 상세 내용 창으로 돌아가기
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    public void delete_values(int p) {
        db = helper.getWritableDatabase();
        String sqlSelect = ContractDB.SQL_SELECT;
        Cursor c = db.rawQuery(sqlSelect,null);
        c.moveToPosition(p);
        int id = c.getInt(0);
        String sqlDelete = ContractDB.SQL_DELETE + id;
        db.execSQL(sqlDelete);
        c.close();//
        db.close();
    }
}
