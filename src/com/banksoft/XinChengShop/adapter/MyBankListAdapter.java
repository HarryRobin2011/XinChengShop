package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.entity.Bank;

import java.util.List;

/**
 * Created by Robin on 2016/7/26.
 */
public class MyBankListAdapter extends BaseMyAdapter{
    public MyBankListAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    private class MyBankListHolder extends BusinessHolder{
        private TextView name;
        private TextView type;
        private TextView cardNumber;
    }

    @Override
    protected View createCellView() {
        return mInflater.inflate(R.layout.my_bank_list_item_layout,null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        MyBankListHolder holder = new MyBankListHolder();
        holder.name = (TextView) cellView.findViewById(R.id.name);
        holder.type = (TextView) cellView.findViewById(R.id.type);
        holder.cardNumber = (TextView) cellView.findViewById(R.id.my_bank_card_number);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        Bank bank = (Bank) dataList.get(position);
        MyBankListHolder holder = (MyBankListHolder) cellHolder;
        holder.name.setText(bank.getBankName());
        holder.type.setText(bank.getName());
        holder.cardNumber.setText(bank.getNo());
        return cellView;
    }
}
