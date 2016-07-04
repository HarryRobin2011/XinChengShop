package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.entity.BalanceChangeRecord;
import com.banksoft.XinChengShop.entity.ScoreOrder;
import com.banksoft.XinChengShop.entity.ScoreRecordVO;
import com.banksoft.XinChengShop.type.BalanceChangeType;
import com.banksoft.XinChengShop.type.ScoreRecordType;
import com.banksoft.XinChengShop.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robin on 2016/4/9.
 */
public class MemberScoreListAdapter extends BaseMyAdapter{
    public MemberScoreListAdapter(Context mContext, ArrayList arrayList) {
        super(mContext,arrayList);
    }

    public class MemberScoreDetailHolder extends BusinessHolder{
        private TextView operType,balance,time,money;
    }
    @Override
    protected View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.money_detail_item_list_layout,null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        MemberScoreDetailHolder holder = new MemberScoreDetailHolder();
        holder.operType = (TextView) cellView.findViewById(R.id.operType);
        holder.balance = (TextView) cellView.findViewById(R.id.balance);
        holder.time = (TextView) cellView.findViewById(R.id.time);
        holder.money = (TextView) cellView.findViewById(R.id.money);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        MemberScoreDetailHolder holder = (MemberScoreDetailHolder) cellHolder;
        ScoreRecordVO scoreRecordVO = (ScoreRecordVO) dataList.get(position);
        holder.operType.setText(getType(scoreRecordVO.getType()));
        holder.time.setText(TimeUtils.getTimeStr(scoreRecordVO.getCreateTime(), TimeUtils.TimeType.Day));
        String changeMoney = "";
        if(scoreRecordVO.getChangeType().equals("ADD")){//增加
            changeMoney = "+"+String.valueOf(scoreRecordVO.getScore()) ;
        }else{
            changeMoney = "-"+String.valueOf(scoreRecordVO.getScore()) ;
        }
        holder.money.setText(changeMoney);
        return cellView;
    }

    private String getType(String type) {
        try {
            return ScoreRecordType.valueOf(type).getTitle();
        } catch (Exception e) {
            return "";
        }
    }
}
