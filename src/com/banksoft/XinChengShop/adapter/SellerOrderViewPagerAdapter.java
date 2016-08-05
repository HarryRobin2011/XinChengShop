package com.banksoft.XinChengShop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import com.banksoft.XinChengShop.type.OrderStatus;
import com.banksoft.XinChengShop.ui.fragment.SellerOrderListFragment;

import java.util.List;

/**
 * Created by Robin on 2016/8/5.
 */
public class SellerOrderViewPagerAdapter extends FragmentPagerAdapter{
    private List dataList;

    public SellerOrderViewPagerAdapter(FragmentManager fm, List dataList) {
        super(fm);
        this.dataList = dataList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        SellerOrderListFragment sellerOrderListFragment = SellerOrderListFragment.newInstance((OrderStatus) dataList.get(position));
        return sellerOrderListFragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int i) {
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ((OrderStatus)dataList.get(position)).getName();
    }

    @Override
    public int getCount() {
        return dataList == null?0:dataList.size();
    }
}
