package com.banksoft.XinChengShop.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import com.banksoft.XinChengShop.adapter.OrderLisAdapter;
import com.banksoft.XinChengShop.adapter.SellerOrderLisAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.type.OrderStatus;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseListFragment;
import com.banksoft.XinChengShop.utils.JSONHelper;

import java.util.ArrayList;

/**
 * Created by Robin on 2016/8/5.
 */
public class SellerOrderListFragment extends XCBaseListFragment {
    private OrderStatus orderStatus;
    private XCBaseActivity baseActivity;


    public static SellerOrderListFragment  newInstance(OrderStatus orderStatus) {
        SellerOrderListFragment sellerOrderListFragment = new SellerOrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentFlag.ORDER_STATUS, orderStatus);
        sellerOrderListFragment.setArguments(bundle);
        return sellerOrderListFragment;
    }

    @Override
    public void request() {
        url = ControlUrl.ORDER_LIST_URL;
        baseActivity = (XCBaseActivity) getActivity();
        orderStatus = (OrderStatus) getArguments().get(IntentFlag.ORDER_STATUS);
        params = "status=" + orderStatus.name() + "&shopId=" + baseActivity.member.getShop().getId();
        jsonType = JSONHelper.ORDER_LIST_DATA;
        bailaAdapter = new SellerOrderLisAdapter(mContext, new ArrayList());
        xListView.setDividerHeight(20);
        setListDao();
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
