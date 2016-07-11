package com.banksoft.XinChengShop.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import com.banksoft.XinChengShop.adapter.CommonListAdapter;
import com.banksoft.XinChengShop.adapter.OrderLisAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.entity.ShopCollectionVO;
import com.banksoft.XinChengShop.ui.ShopInfoActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseListFragment;
import com.banksoft.XinChengShop.utils.JSONHelper;

import java.util.ArrayList;

/**
 * Created by Robin on 2016/4/24.
 */
public class ShopProductAssessListFragment extends XCBaseListFragment{
    private XCBaseActivity activity;
    private String productId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (XCBaseActivity) getActivity();
    }

    @Override
    public void request() {
        productId = getArguments().getString(IntentFlag.PRODUCT_ID);
        url = ControlUrl.XC_PRODUCT_ASSESS_FRONT_DTO_LIST_URL;
        params = "productId="+productId;
        jsonType = JSONHelper.PRODUCT_ASSETS_FRONT_DTO_LIST_DATA;
        bailaAdapter = new CommonListAdapter(mContext,new ArrayList());
        xListView.setDividerHeight(10);
        cacheFlag = false;
        setListDao();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Activity.RESULT_FIRST_USER){
            switch (resultCode){
                case Activity.RESULT_OK:  //???????
                    request();
                    break;
                case Activity.RESULT_CANCELED: //??????
                    request();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public Fragment getInstance() {
        return null;
    }

    @Override
    public CharSequence getTitle() {
        return "����";
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onAdapterCLick(View view, int position) {

    }
}
