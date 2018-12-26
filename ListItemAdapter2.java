package com.example.soyeonlee.myapplication10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ListItemAdapter2 extends BaseAdapter {

    Context context;
    private ArrayList<ListItem> listItemArrayList = new ArrayList<>();
    //ArrayList<ListItem> listItemArrayList;
    ListItemAdapter2.ViewHolder viewHolder;

    class ViewHolder {
        TextView list_date;
        TextView list_write;
        ImageView list_image;
    }


    public ListItemAdapter2(Context context, ArrayList<ListItem> listItemArrayList) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item2,null);

            viewHolder = new ListItemAdapter2.ViewHolder();
            viewHolder.list_image = (ImageView) convertView.findViewById(R.id.list_image);
            //viewHolder.list_date = (TextView) convertView.findViewById(R.id.list_date);
            viewHolder.list_write = (TextView) convertView.findViewById(R.id.list_write);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ListItemAdapter2.ViewHolder) convertView.getTag();
        }
        //viewHolder.list_date.setText(listItemArrayList.get(position).getDate());
        Glide.with(context).load(listItemArrayList.get(position).getImage()).error(R.drawable.ap).into(viewHolder.list_image);
        //viewHolder.imageItemView.setImageResource(listItemArrayList.get(position).getImage());
        viewHolder.list_write.setText(listItemArrayList.get(position).getText());

        return convertView;
    }
}
