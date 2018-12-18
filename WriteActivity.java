package com.example.soyeonlee.myapplication10;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class WriteActivity extends AppCompatActivity {


    SQLiteDatabase db;
    DBHelper helper;
    DatePicker datePicker;
    final int DIALOG_DATE = 1;
    TextView date_text;
    EditText edit_text;
    ImageView imageView;

    private int mYear;
    private int mMonth;
    private int mDay;

    String input_image;
    Uri uri;

    //Calendar c = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        date_text = (TextView) findViewById(R.id.date_text);
        edit_text = (EditText) findViewById(R.id.edit_text);
        imageView = (ImageView) findViewById(R.id.imageView);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        helper = new DBHelper(this);
        updateDate();
    }

    //DATE버튼 클릭 이벤트
    public void dateClick (View v) {
        Calendar minDate = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,mDateSetListener,mYear,mMonth,mDay);
        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
        //showDialog(DIALOG_DATE);
    }

    public void updateDate() {

        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일",Locale.KOREA);
        //Calendar cal = Calendar.getInstance();
        //date_text.setText(formatter.format(cal.getTime()));
        //Date date = new Date();
        //date_text.setText(formatter.format(date));
        //Date date = new Date();
        //date_text.setText(formatter.format(date));
        date_text.setText(String.format("%d년 %02d월 %02d일",mYear,mMonth+1,mDay));
    }


    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            //Toast.makeText(getApplicationContext(),view.getMonth(),Toast.LENGTH_SHORT).show();
            //dbDate = Integer.toString(mYear) + "년" + Integer.toString(mMonth+1) + "월" + Integer.toString(mDay) +"일";
            updateDate();
        }
    };
/*
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this,mDateSetListener,mYear,mMonth,mDay);
    }*/

    public void imageClick (View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == 1) {

            if(resultCode == RESULT_OK) {

                try {
                    uri = data.getData();
                    imageView.setImageURI(uri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

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
        if(imageView.getDrawable() != null) {
            save_values();
            Toast.makeText(getApplicationContext(),"저장",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "사진을 추가해주세요:-)", Toast.LENGTH_SHORT).show();
        }

        /*
        if(imageView.getDrawable() != null) {
        save_values();
        //Toast.makeText(getApplicationContext(),"저장",Toast.LENGTH_SHORT).show();

            TabFragment1 fragment1 = new TabFragment1();
            Bundle bundle = new Bundle();
            bundle.putString("date",date_text.getText().toString());
            bundle.putString("write",edit_text.getText().toString());
            fragment1.setArguments(bundle);
        }
        else {
            Toast.makeText(getApplicationContext(), "사진을 추가해주세요:-)", Toast.LENGTH_SHORT).show();
        }*/
        //finish();
    }

    public void save_values() {
        db = helper.getWritableDatabase();
        //db.execSQL(ContractDB.SQL_DELETE);

        String input_date = date_text.getText().toString();

        //buri = Uri.parse("android.res://"+this.getPackageName()+"/drawble/ap");
        //String input_image = buri.toString();

        input_image = uri.toString();


        String input_write = edit_text.getText().toString();


        String sqlInsert = ContractDB.SQL_INSERT + " (" + "'" + input_date + "', " + "'" + input_image + "'," + "'" + input_write + "'" + ")";
        //String sqlInsert = ContractDB.SQL_INSERT + " (" + "'" + input_date + "', " + "'" + input_write + "'" + ")";
        db.execSQL(sqlInsert);
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
