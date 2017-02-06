package com.banksoft.XinChengShop.adapter.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.banksoft.XinChengShop.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Robin on 2015/1/28.
 */
public abstract class BaseMyAdapter extends BaseAdapter implements View.OnClickListener{
    public List dataList;
    public Context mContext;
    public LayoutInflater mInflater;
    private OnAdapterClickListener onAdapterClickListener;
    public SparseArray<View> dataMap;
    private ViewHolder.Callback callback;


    public BaseMyAdapter(Context context, List dataList){
        this.dataList = dataList;
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        dataMap = new SparseArray<>();
    }

    public void setMoreDataList(List dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void setDataList(List dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public class BusinessHolder{
       public int position;
    }

    protected abstract View createCellView();
    public abstract BusinessHolder createCellHolder(View cellView);
    protected abstract View buildData(int position,View cellView,BusinessHolder cellHolder);


    @Override
    public int getCount() {
        return dataList == null? 0:dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View cellView, ViewGroup parent) {
        BusinessHolder holder = null;
        if(dataMap.get(position) == null){
            cellView = createCellView();
            holder = createCellHolder(cellView);
            cellView.setTag(R.id.tag,holder);
            dataMap.put(position,cellView);
        }else{
            cellView = dataMap.get(position);
            holder = (BusinessHolder) cellView.getTag(R.id.tag);
        }
        holder.position = position;
        buildData(position,cellView,holder);
        return cellView;
    }

    public void setOnAdapterClickListener(OnAdapterClickListener onAdapterClickListener) {
        this.onAdapterClickListener = onAdapterClickListener;
    }

    public interface OnAdapterClickListener{
        public void onAdapterCLick(View view, int position);
    }


    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        onAdapterClickListener.onAdapterCLick(v,position);
    }

    public void remove(int position){
        dataList.remove(position);
        dataMap.clear();
        notifyDataSetChanged();
    }
}
