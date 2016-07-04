package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;

import java.util.List;

/**
 * Created by Robin on 2016/3/31.
 */
public class SearchAutoCompleteAdapter extends BaseMyAdapter {
    public SearchAutoCompleteAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    protected View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.search_auto_complete__item_layout,null);
    }

    private class SearchAutoCompleteHolder extends BusinessHolder{
        private TextView name;
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        SearchAutoCompleteHolder holder = new SearchAutoCompleteHolder();
        holder.name = (TextView) cellView.findViewById(R.id.name);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        SearchAutoCompleteHolder holder = (SearchAutoCompleteHolder) cellHolder;
        String dataStr = (String) dataList.get(position);
        holder.name.setText(dataStr);
        return cellView;
    }
}
