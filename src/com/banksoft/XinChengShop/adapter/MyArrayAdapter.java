package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;

import java.util.List;

/**
 * Created by Robin on 2016/11/16.
 */
public class MyArrayAdapter extends ArrayAdapter {
    public MyArrayAdapter(Context context, int resource, Object[] objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1,null);
        }
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setTextColor(getContext().getResources().getColor(R.color.text_black_light));
        textView.setText(getItem(position).toString());
        return convertView;
    }
}
