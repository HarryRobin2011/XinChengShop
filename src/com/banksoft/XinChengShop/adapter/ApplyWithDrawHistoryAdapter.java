package com.banksoft.XinChengShop.adapter;

import android.content.Context;
import android.view.View;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;

import java.util.List;

/**
 * Created by Robin on 2016/9/12.
 */
public class ApplyWithDrawHistoryAdapter extends com.banksoft.XinChengShop.adapter.base.BaseMyAdapter {
    public ApplyWithDrawHistoryAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    protected View createCellView() {
        return null;
    }

    private class ApplyWithDrawHistoryHolder extends BusinessHolder{
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        return null;
    }

    @Override
    protected View buildData(int position, View cellView, BusinessHolder cellHolder) {
        return null;
    }
}
