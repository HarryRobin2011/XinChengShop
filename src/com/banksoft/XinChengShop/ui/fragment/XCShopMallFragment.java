package com.banksoft.XinChengShop.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.type.ShopType;
import com.banksoft.XinChengShop.ui.XCMainActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseFragment;

/**
 * Created by harry_robin on 15/11/5.
 */
public class XCShopMallFragment extends XCBaseFragment {
    private XCMainActivity activity;
    private ShopListFragment shopListFragment;

    @Override
    public View initContentView() {
        return LinearLayout.inflate(mContext,R.layout.content_layout,null);
    }

    @Override
    public void initView(View view) {
        activity =  (XCMainActivity)getActivity();
    }

    @Override
    public void initData() {
        if(shopListFragment == null){
         shopListFragment = new ShopListFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(IntentFlag.SHOP_TYPE, ShopType.SHOP_SALE.name());
        shopListFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content,shopListFragment).commit();

    }

    @Override
    public void initOperation() {

    }

    @Override
    public Fragment getInstance() {
        return null;
    }

    @Override
    public CharSequence getTitle() {
        return getText(R.string.shop_mall);
    }
}
