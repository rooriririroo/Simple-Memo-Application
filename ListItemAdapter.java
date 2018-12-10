package com.example.soyeonlee.myapplication8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListItemAdapter extends BaseAdapter {

    Context context;
    private ArrayList<ListItem> listItemArrayList = new ArrayList<>();
    //ArrayList<ListItem> listItemArrayList;
    ViewHolder viewHolder;

    class ViewHolder {
        TextView dateView;
        TextView textView;
    }


    public ListItemAdapter(Context context, ArrayList<ListItem> listItemArrayList) {
        this.context = context;
        this.listItemArrayList = listItemArrayList;
    }

    // 리스트뷰가 몇 개의 아이템을 가지고 있는지 알려줌
    @Override
    public int getCount()
    {
        return this.listItemArrayList.size();
        //return (this.listItemArrayList == null) ? 0 : this.listItemArrayList.size();
    }

    // 현재 어떤 아이템인지 알려줌. ArrayList의 객체 중 position에 해당하는 객체 가져옴
    @Override
    public Object getItem(int position) {
        return this.listItemArrayList.get(position);
    }

    // 현재 어떤 position인지 알려줌
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 리스트뷰에서 아이템과 xml을 연결하여 화면에 띄워줌. convertView에 list_item.xml을 불러옴
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            // LayoutInflater 클래스를 이용하면 다른 클래스에서도 xml을 가져올 수 있음
            //from(context,)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.dateView = (TextView) convertView.findViewById(R.id.dateView);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.dateView.setText(listItemArrayList.get(position).getDate());
        viewHolder.textView.setText(listItemArrayList.get(position).getText());

        return convertView;
    }
}
