package com.banksoft.XinChengShop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import com.banksoft.XinChengShop.ui.base.XCBaseFragment;

import java.util.List;

/**
 * Created by Robin on 2016/4/8.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List dataList;

    public ViewPagerAdapter(FragmentManager fm, List dataList) {
        super(fm);
        this.dataList = dataList;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        dataList.remove(position);
    }

    @Override
    public Fragment getItem(int i) {
        return (Fragment) dataList.get(i);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ((XCBaseFragment)dataList.get(position)).getTitle();
       //return "";
    }

    @Override
    public int getCount() {
        return dataList.size();
    }
}
