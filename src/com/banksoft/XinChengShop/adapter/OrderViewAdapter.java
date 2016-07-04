package com.banksoft.XinChengShop.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.type.OrderStatus;
import com.banksoft.XinChengShop.ui.fragment.OrderListFragment;

/**
 * Created by Robin on 2015/2/3.
 */
public class OrderViewAdapter extends FragmentPagerAdapter {
    private OrderStatus[] orderStatuses;
    public String orderMaster;
    public OrderViewAdapter(FragmentManager fm, OrderStatus[] statuses) {
        super(fm);
        this.orderStatuses = statuses;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        OrderListFragment orderListFragment = new OrderListFragment();
        Bundle bundle = new Bundle();

        bundle.putString(IntentFlag.ORDER_TYPE,orderStatuses[position].name());
        bundle.putString(IntentFlag.Order_MASTER, orderMaster);
        orderListFragment.setArguments(bundle);
        return orderListFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return orderStatuses[position].getName();
    }

    @Override
    public int getCount() {
        return orderStatuses == null?0:orderStatuses.length;
    }
}
