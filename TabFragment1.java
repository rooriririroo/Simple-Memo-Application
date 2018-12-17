package com.example.soyeonlee.myapplication10;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TabFragment1 extends Fragment {

    /*
    public interface OnItemSelectedListener {
        public void onItemApplySelected(String date, int image, String write);
    }

    private Activity activity; //
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity) {
            this.activity = (Activity)context;
        }
    }*/

    SQLiteDatabase db;
    DBHelper helper;
    ListView listView;
    ListItemAdapter adapter;
    ArrayList<ListItem> listItemArrayList;
    FloatingActionButton floatingActionButton;

    public TabFragment1() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.tab_fragment_1,container,false);

        listItemArrayList = new ArrayList<ListItem>();
        listView = (ListView) rootView.findViewById(R.id.listView);
        adapter = new ListItemAdapter(getContext(),listItemArrayList);
        helper = new DBHelper(getContext());

        listView.setAdapter(adapter);

        load_values();

        //listItemArrayList.add(new ListItem("2018년12월9일","hello"));
        //listItemArrayList.add(new ListItem("2018년12월12일","hi"));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Bundle extra = getArguments();
                //TabFragment1 fragment1 = new TabFragment1();
                //Bundle bundle = new Bundle();
               //bundle.putString("dateModify",listItemArrayList.get(position).getDate());
                //bundle.putString("imageModify",listItemArrayList.get(position).getImage());
                //bundle.putString("writeModify",listItemArrayList.get(position).getText());

                Intent intent = new Intent(getActivity(),DetailActivity.class);
                intent.putExtra("dateDetail",listItemArrayList.get(position).getDate());
                intent.putExtra("imageDetail",listItemArrayList.get(position).getImage());
                intent.putExtra("writeDetail",listItemArrayList.get(position).getText());
                intent.putExtra("positionDetail",position);
                startActivity(intent);
                //fragment1.setArguments(bundle);
               // ((DetailActivity)getActivity()).showDetail("2018-09-11","hellohello");
                //((OnItemSelectedListener)activity).onItemApplySelected(listItemArrayList.get(position).getDate(),listItemArrayList.get(position).getImage(),listItemArrayList.get(position).getText());
                //Toast.makeText(getContext(),position,Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),WriteActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
        //return inflater.inflate(R.layout.tab_fragment_1,container,false);
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
                String imageStr = c.getString(2);
                String textStr = c.getString(3);
                listItemArrayList.add(new ListItem(dateStr,imageStr,textStr));
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


}
