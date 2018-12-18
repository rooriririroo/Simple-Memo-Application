package com.example.soyeonlee.myapplication10;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Calendar;

public class ModifyActivity extends AppCompatActivity {

    SQLiteDatabase db;
    DBHelper helper;
    DatePicker datePicker;
    final int DIALOG_DATE = 1;
    TextView date_text2;
    ImageView imageView2;
    EditText edit_text2;
    String dateModify;
    String writeModify;
    String imageModify;
    int positionModify;

    private int mYear;
    private int mMonth;
    private int mDay;

    String input_image;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        date_text2 = (TextView) findViewById(R.id.date_text2);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        edit_text2 = (EditText) findViewById(R.id.edit_text2);


        Intent intent = getIntent();

        dateModify = intent.getStringExtra("dateModify");
        date_text2.setText(dateModify);

        imageModify = intent.getStringExtra("imageModify");
        Glide.with(this).load(imageModify).into(imageView2);
        input_image = imageModify;

        writeModify = intent.getStringExtra("writeModify");
        edit_text2.setText(writeModify);

        positionModify = intent.getIntExtra("positionModify",0);

        helper = new DBHelper(this);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        //updateDate();
    }

    //DATE버튼 클릭 이벤트
    public void dateClick2 (View v) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,mDateSetListener,mYear,mMonth,mDay);
        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
        //showDialog(DIALOG_DATE);
    }

    public void updateDate() {
        date_text2.setText(String.format("%d년 %02d월 %02d일",mYear,mMonth+1,mDay));
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            //dbDate = Integer.toString(mYear) + "년" + Integer.toString(mMonth+1) + "월" + Integer.toString(mDay) +"일";
            updateDate();
        }
    };

    /*
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this,mDateSetListener,mYear,mMonth,mDay);
    }*/

    public void imageClick2 (View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,2);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == 2) {

            if(resultCode == RESULT_OK) {

                try {
                    uri = data.getData();
                    imageView2.setImageURI(uri);
                    input_image = uri.toString();//
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }


    //취소버튼 클릭 이벤트
    public void cancelClick2 (View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("지금 나가면 글이 저장되지 않습니다.\n나가시겠습니까?");

        // 글 저장 안하고 나가기
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                //Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                //startActivity(intent);
            }
        });

        // 기존 글쓰기 창으로 돌아가기
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    //저장버튼 클릭 이벤트
    public void saveClick2 (View v) {
        save_values(positionModify);
        Toast.makeText(getApplicationContext(),"수정",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    public void save_values(int p) {
        db = helper.getWritableDatabase();
        String sqlSelect = ContractDB.SQL_SELECT;
        Cursor c = db.rawQuery(sqlSelect,null);
        c.moveToPosition(p);
        int id = c.getInt(0);

        String input_date = date_text2.getText().toString();

        //input_image = uri.toString();

        /*
        if(input_image == imageModify) {
            input_image = imageModify;
        }
        else {
            input_image = uri.toString();
        }*/

        String input_write = edit_text2.getText().toString();


        String sqlUpdate = ContractDB.SQL_UPDATE + " DATE" + "=" +"'"+ input_date + "'" + ","
                + " IMAGE" + "=" + "'" + input_image + "'" + ","+ " WRITE" + "=" + "'" + input_write + "'"
                + " WHERE" + " _ID" + "=" + id;
        db.execSQL(sqlUpdate);
        c.close();
        db.close();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("지금 나가면 글이 저장되지 않습니다.\n나가시겠습니까?");

        // 글 저장 안하고 나가기
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                //Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                //startActivity(intent);
            }
        });

        // 기존 글쓰기 창으로 돌아가기
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
}
