package com.banksoft.XinChengShop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by harry_robin on 16/3/22.
 */
public class TakeOutInfoAdapter extends FragmentPagerAdapter {
    private List fragmentList;
    private String[] titles;
    public TakeOutInfoAdapter(FragmentManager fm,List dataList,String [] titles) {
        super(fm);
        this.fragmentList = dataList;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {

        return (Fragment) fragmentList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return titles == null?0:titles.length;
    }
}
