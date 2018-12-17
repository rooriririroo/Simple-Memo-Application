package com.example.soyeonlee.myapplication10;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.DateFormatDayFormatter;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;


public class TabFragment2 extends Fragment {

    MaterialCalendarView materialCalendarView;
    SQLiteDatabase db;
    DBHelper helper;
    ListView listView;
    ListItemAdapter2 adapter;
    ArrayList<ListItem> listItemArrayList2;

    public TabFragment2() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.tab_fragment_2, container, false);

        listItemArrayList2 = new ArrayList<ListItem>();
        listView = (ListView) rootView.findViewById(R.id.listView);
        adapter = new ListItemAdapter2(getContext(),listItemArrayList2);
        helper = new DBHelper(getContext());

        listView.setAdapter(adapter);
        //listItemArrayList.add(new ListItem("2018.12.02","hello"));

        materialCalendarView = (MaterialCalendarView) rootView.findViewById(R.id.calendarView);
        //materialCalendarView.state().edit().setMinimumDate(CalendarDay.from(2018,11,01));
        materialCalendarView.addDecorators(new SundayDecorator(),new OnDateDecorator());

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                String strDate = String.format(Locale.KOREA,"%d년 %02d월 %02d일",date.getYear(),date.getMonth()+1,date.getDay());
               // listView.setFocusableInTouchMode(true);
               // listView.requestFocus();
                load_values(strDate);
                //Toast.makeText(getContext(),strDate,Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(),DetailActivity.class);
                intent.putExtra("dateDetail",listItemArrayList2.get(position).getDate());
                intent.putExtra("imageDetail",listItemArrayList2.get(position).getImage());
                intent.putExtra("writeDetail",listItemArrayList2.get(position).getText());
                intent.putExtra("positionDetail",position);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public void load_values(String strDate) {

        db = helper.getReadableDatabase();
        int recordCount = -1;
        if(db != null) {
            Cursor c = db.rawQuery(ContractDB.SQL_SELECT,null);
            recordCount = c.getCount();
            listItemArrayList2.clear();
            for(int i = 0; i<recordCount; i++) {
                c.moveToNext();

                if(strDate.equals(c.getString(1))) {

                    String dateStr = c.getString(1);
                    String imageStr = c.getString(2);
                    String textStr = c.getString(3);
                    listItemArrayList2.add(new ListItem(dateStr,imageStr,textStr));
                }
            }
            c.close();
            /////////


            Comparator<ListItem> dateSort = new Comparator<ListItem>() {
                @Override
                public int compare(ListItem o1, ListItem o2) {
                    return o2.getDate().compareTo(o1.getDate());
                }
            };
            Collections.sort(listItemArrayList2,dateSort);

            /////////
            adapter.notifyDataSetChanged();
        }
    }
}
