package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.banksoft.XinChengShop.model.ViewHolde;


import java.util.List;

/**
 * Created by 侯恩硕 on 2016/4/21.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    //固定的数据写出来
    private Context context =null;
    private List<T> listdata =null;
    private LayoutInflater inflater =null;
    private int layoutid ;
    //需要其他对象 要当参数传入
    public CommonAdapter(Context context, List<T> listdata,int layoutid) {
        this.context = context;
        this.listdata = listdata;
        this.inflater = LayoutInflater.from(context);
        this.layoutid =layoutid;
    }

    @Override
    public int getCount() {
        return listdata.size();
    }

    @Override
    public T getItem(int position) {
        return listdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //固定的部分写死，变化的抽取出来
        ViewHolde viewholde =ViewHolde.getViewHolde(context,parent,convertView,layoutid,position);
        convert(viewholde,getItem(position));//变化的部分
        return viewholde.getContentview();
    }

    //实现此方法即可，因为还有viewholde 和 数据 是变化的
    public abstract void convert(ViewHolde viewHolde, T item);

}
