package com.example.soyeonlee.myapplication8;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class WriteActivity extends AppCompatActivity {

    SQLiteDatabase db;
    DBHelper helper;
    DatePicker datePicker;
    final int DIALOG_DATE = 1;
    TextView date_text;
    EditText edit_text;
    String dbDate;

    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        date_text = (TextView) findViewById(R.id.date_text);
        edit_text = (EditText) findViewById(R.id.edit_text);

        helper = new DBHelper(this);
        //db = helper.getWritableDatabase();

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        updateDate();
    }

    //DATE버튼 클릭 이벤트
    public void dateClick (View v) {
        showDialog(DIALOG_DATE);
    }

    public void updateDate() {
        date_text.setText(String.format("%d년 %d월 %d일",mYear,mMonth+1,mDay));
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

    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this,mDateSetListener,mYear,mMonth,mDay);
    }


    //취소버튼 클릭 이벤트
    public void cancelClick (View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("지금 나가면 글이 저장되지 않습니다.\n나가시겠습니까?");

        // 글 저장 안하고 나가기
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
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
    public void saveClick (View v) {
        save_values();
        Toast.makeText(getApplicationContext(),"Save",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        //finish();
    }

    public void save_values() {
        db = helper.getWritableDatabase();
        //db.execSQL(ContractDB.SQL_DELETE);

        String input_date = date_text.getText().toString();
        String input_write = edit_text.getText().toString();

        String sqlInsert = ContractDB.SQL_INSERT + " (" + "'" + input_date + "', " + "'" + input_write + "'" + ")";
        db.execSQL(sqlInsert);
        db.close();
    }

}
