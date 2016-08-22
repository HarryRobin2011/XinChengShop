package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import com.baidu.mapapi.map.Text;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;

import java.util.List;

/**
 * Created by Robin on 2016/8/22.
 */
public class SexSelectAdapter extends BaseMyAdapter{
    public SexSelectAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    protected View createCellView() {
        return LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_checked,null);
    }

    private class SexSelectAdapterHolder extends BusinessHolder{
        private CheckedTextView checkedTextView;
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        SexSelectAdapterHolder holder = new SexSelectAdapterHolder();
        holder.checkedTextView = (CheckedTextView) cellView.findViewById(android.R.id.text1);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        String sex = (String) dataList.get(position);
        SexSelectAdapterHolder holder = (SexSelectAdapterHolder) cellHolder;
        holder.checkedTextView.setText(sex);
        return cellView;
    }
}
