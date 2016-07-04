package com.banksoft.XinChengShop.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.type.CommonType;
import com.banksoft.XinChengShop.ui.fragment.CommonListFragment;

/**
 * Created by harry_robin on 16/1/24.
 */
public class CommonTypeAdapter extends FragmentPagerAdapter{
    private CommonType[] commonTypes;
    public CommonTypeAdapter(FragmentManager fm, CommonType[] commonTypes) {
        super(fm);
        this.commonTypes = commonTypes;
    }

    @Override
    public Fragment getItem(int position) {
        CommonListFragment commonListFragment = new CommonListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IntentFlag.COMMON_TYPE,commonTypes[position].name());
        commonListFragment.setArguments(bundle);
        return commonListFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return commonTypes[position].getName();
    }

    @Override
    public int getCount() {
        return commonTypes == null?0:commonTypes.length;
    }
}
