package com.banksoft.XinChengShop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.banksoft.XinChengShop.type.OrderStatus;
import com.banksoft.XinChengShop.type.OrderType;
import com.banksoft.XinChengShop.ui.fragment.OrderReturnListFragment;

/**
 * Created by Robin on 2016/8/24.
 */
public class OrderReturnViewPagerAdapter extends FragmentPagerAdapter{
    private OrderStatus[] orderStatuses;
    public OrderReturnViewPagerAdapter(FragmentManager fm,OrderStatus[] statuses) {
        super(fm);
        this.orderStatuses = statuses;
    }

    @Override
    public Fragment getItem(int i) {
        OrderReturnListFragment orderReturnListFragment = OrderReturnListFragment.getInstance(orderStatuses[i]);
        return orderReturnListFragment;
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
