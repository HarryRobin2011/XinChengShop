package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.entity.ExpressCompanyCell;

import java.util.List;

/**
 * Created by Robin on 2016/9/8.
 */
public class LogisticsCompanyListAdapter extends com.banksoft.XinChengShop.adapter.base.BaseMyAdapter {
    public LogisticsCompanyListAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    private class LogisticsCompanyListHolder extends BusinessHolder{
        private TextView name;
    }

    @Override
    protected View createCellView() {
        return mInflater.inflate(R.layout.logistics_company_list_item_layout,null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        LogisticsCompanyListHolder holder = new LogisticsCompanyListHolder();
        holder.name = (TextView) cellView.findViewById(R.id.name);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        LogisticsCompanyListHolder holder = (LogisticsCompanyListHolder) cellHolder;
        ExpressCompanyCell expressCompanyCell = (ExpressCompanyCell) dataList.get(position);
        holder.name.setText(expressCompanyCell.getName());
        return cellView;
    }
}
