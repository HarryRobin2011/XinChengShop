package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.entity.MemberRateVO;

import java.util.List;

/**
 * Created by Robin on 2016/7/28.
 */
public class WithDrawTariffAdapter extends BaseMyAdapter{
    public WithDrawTariffAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    private class WithDrawTariffHolder extends BusinessHolder{
        private TextView name;
        private TextView tariff;
    }
    @Override
    protected View createCellView() {
        return mInflater.inflate(R.layout.with_draw_tariff_item_layout,null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        WithDrawTariffHolder holder = new WithDrawTariffHolder();
        holder.name = (TextView) cellView.findViewById(R.id.name);
        holder.tariff = (TextView) cellView.findViewById(R.id.tariff);
        return holder;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        WithDrawTariffHolder holder = (WithDrawTariffHolder) cellHolder;
        MemberRateVO memberRateVO = (MemberRateVO) dataList.get(position);
        holder.name.setText(memberRateVO.getName());
        holder.tariff.setText(""+memberRateVO.getRate());
        return cellView;
    }
}
