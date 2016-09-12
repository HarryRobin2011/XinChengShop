package com.banksoft.XinChengShop.ui.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import com.banksoft.XinChengShop.adapter.ApplyWithDrawHistoryAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.ui.base.XCBaseListFragment;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by Robin on 2016/9/12.
 */
public class ApplyWithDrawHistoryFragment extends XCBaseListFragment{
    @Override
    public void request() {
        url = ControlUrl.XC_APPLY_WITH_DRAW_HISTORY_LIST_URL;
        params = "memberId="+mActivity.member.getMember().getId();
        jsonType = JSONHelper.XC_APPLY_WITH_DRAW_HISTORY_LIST_DATA;
      //  bailaAdapter = new ApplyWithDrawHistoryAdapter();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onAdapterCLick(View view, int position) {

    }

    @Override
    public Fragment getInstance() {
        return null;
    }

    @Override
    public CharSequence getTitle() {
        return null;
    }
}
