package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;

import java.util.List;

/**
 * Created by Robin on 2016/8/12.
 */
public class ExpressBillAdapter extends BaseMyAdapter {
    public ExpressBillAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    protected View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.express_bill_list_item_layout,null);
    }

    private class ExpressBillHolder extends BusinessHolder {
        private TextView shopName;
        private TextView total;
        TextView myDispatch;// 订单创建未付款
        private LinearLayout productContent;
        private LinearLayout toolLayout;
        private LinearLayout orderItemLayout;

    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        ExpressBillHolder holder = new ExpressBillHolder();
        holder.shopName = (TextView) cellView.findViewById(R.id.shop_name);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        return null;
    }
}
