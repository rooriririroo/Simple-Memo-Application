package com.example.soyeonlee.myapplication10;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TabFragment1 extends Fragment {

    SQLiteDatabase db;
    DBHelper helper;
    ListView listView;
    ListItemAdapter adapter;
    ArrayList<ListItem> listItemArrayList;
    FloatingActionButton floatingActionButton;

    String input_query;

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(),DetailActivity.class);
                intent.putExtra("dateDetail",listItemArrayList.get(position).getDate());
                intent.putExtra("imageDetail",listItemArrayList.get(position).getImage());
                intent.putExtra("writeDetail",listItemArrayList.get(position).getText());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//
                startActivity(intent);
            }
        });

        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),WriteActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        setHasOptionsMenu(true);

        return rootView;
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(queryTextListener);
        //return true;
    }

    private SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String query) {
            input_query = query;
            load_search_values();
            //Toast.makeText(getContext(),query,Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            //Toast.makeText(getContext(),newText,Toast.LENGTH_SHORT).show();
            if(newText.equals(""))
                load_values();
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int curId = menuItem.getItemId();
        if(curId == R.id.menu_search) {

        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void load_search_values() {

        db = helper.getReadableDatabase();
        int recordCount = -1;
        if(db != null) {
            Cursor c = db.rawQuery(ContractDB.SQL_SELECT,null);
            recordCount = c.getCount();
            listItemArrayList.clear();
            for(int i = 0; i<recordCount; i++) {
                c.moveToNext();

                if(c.getString(1).contains(input_query) || c.getString(3).contains(input_query)) {

                    String dateStr = c.getString(1);
                    String imageStr = c.getString(2);
                    String textStr = c.getString(3);
                    listItemArrayList.add(new ListItem(dateStr,imageStr,textStr));
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
            Collections.sort(listItemArrayList,dateSort);

            /////////
            adapter.notifyDataSetChanged();
        }
    }

}
