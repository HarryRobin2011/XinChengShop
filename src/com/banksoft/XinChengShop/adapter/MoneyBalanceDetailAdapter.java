package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.entity.BalanceChangeRecord;
import com.banksoft.XinChengShop.type.BalanceChangeType;
import com.banksoft.XinChengShop.utils.TimeUtils;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by Robin on 2016/4/8.
 *资金明细adapter
 */
public class MoneyBalanceDetailAdapter extends BaseMyAdapter {


    public MoneyBalanceDetailAdapter(Context mContext, ArrayList arrayList) {
        super(mContext,arrayList);
    }

    public class MoneyBalanceDetailHolder extends BusinessHolder{
        private TextView operType,balance,time,money;
    }
    @Override
    protected View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.money_detail_item_list_layout,null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        MoneyBalanceDetailHolder holder = new MoneyBalanceDetailHolder();
        holder.operType = (TextView) cellView.findViewById(R.id.operType);
        holder.balance = (TextView) cellView.findViewById(R.id.balance);
        holder.time = (TextView) cellView.findViewById(R.id.time);
        holder.money = (TextView) cellView.findViewById(R.id.money);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        MoneyBalanceDetailHolder holder = (MoneyBalanceDetailHolder) cellHolder;
        BalanceChangeRecord record = (BalanceChangeRecord) dataList.get(position);
        holder.operType.setText(getType(record.getType()));
        holder.time.setText(TimeUtils.getTimeStr(record.getCreateTime(), TimeUtils.TimeType.Day));
        holder.balance.setText("余额："+record.getBalance());
        String changeMoney = "";
        if(record.isAddStatus()){//增加
            changeMoney = "+"+String.valueOf(record.getChangeBalance()) ;
        }else{
            changeMoney = "-"+String.valueOf(record.getChangeBalance()) ;
        }
        holder.money.setText(changeMoney);
        return cellView;
    }

    private String getType(String type){
        try {
            return BalanceChangeType.valueOf(type).getTitle();
        }catch (Exception e){
            return "";
        }

    }
}
