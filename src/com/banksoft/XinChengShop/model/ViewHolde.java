package com.banksoft.XinChengShop.model;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 侯恩硕 on 2016/4/21.
 * adapter的通用类
 */
public class ViewHolde {
    private SparseArray<View> listview =new SparseArray<View>(); //把所有不确定的控件抽象出一个集合的泛型
    private View contentview =null;//；每个viewholder都要放到view中
    private LayoutInflater inflater =null;

    private ViewHolde(Context context, ViewGroup parent,int layoutid,int position) {
        contentview =LayoutInflater.from(context).inflate(layoutid,null,false);
        contentview.setTag(this);
    }
    /**
     * 返回一个viewHolde对象
     * @param context
     * @param parent
     * @param contentview
     * @param layoutid
     * @param position
     * @return
     */
    public static ViewHolde getViewHolde(Context context, ViewGroup parent,View contentview,int layoutid,int position){
        //判断contentview是否为空 达到复用
        if(contentview == null){
            return new ViewHolde(context,parent,layoutid,position);
        }
        return  (ViewHolde) contentview.getTag();
    }
    /**
     * 返回指定的view
     * @param viewid
     * @param <T>
     * @return
     */
    public <T extends  View> T getView(int viewid){
        //查询指定的view是否为空，为空即添加到集合中
        View view =  listview.get(viewid);
        if(view ==null){
            view =contentview.findViewById(viewid);
            listview.put(viewid,view);
        }
        return (T)view;
    }
    /**
     * 返回得contentview
     * @return
     */
    public View getContentview(){
        return contentview;
    }


    /**把获取控件和设置数据的固定模式 在写到一个方法里*/
    public ViewHolde setText(int viewid,String text){
        TextView view =getView(viewid);
        view.setText(text);
        return this;
    }
}
