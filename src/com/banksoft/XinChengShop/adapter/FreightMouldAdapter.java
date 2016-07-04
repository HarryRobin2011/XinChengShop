package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.entity.ExpressModelBO;
import com.banksoft.XinChengShop.entity.ExpressPriceVO;

import java.util.List;

/**
 * Created by Robin on 2016/4/13.
 */
public class FreightMouldAdapter extends BaseMyAdapter{

    public FreightMouldAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    protected View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.select_express_mould_list_item_layout,null);
    }

    private class FreightMouldHolder extends BusinessHolder{
        public TextView name;
        private TextView price;
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        FreightMouldHolder holder = new FreightMouldHolder();
        holder.name = (TextView) cellView.findViewById(R.id.name);
        holder.price = (TextView) cellView.findViewById(R.id.price);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        FreightMouldHolder holder = (FreightMouldHolder) cellHolder;
        ExpressModelBO expressModelBO = (ExpressModelBO) dataList.get(position);
        holder.name.setText(expressModelBO.getName());
        return cellView;
    }
}
