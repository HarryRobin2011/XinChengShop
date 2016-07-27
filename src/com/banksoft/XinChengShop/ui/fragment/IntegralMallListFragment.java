package com.banksoft.XinChengShop.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import com.banksoft.XinChengShop.adapter.IntegralMallListAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.entity.ShopProductListVO;
import com.banksoft.XinChengShop.ui.ScoreProductInfoActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseListFragment;
import com.banksoft.XinChengShop.utils.JSONHelper;

import java.util.ArrayList;

/**
 * Created by harry_robin on 15/11/25.
 */
public class IntegralMallListFragment extends XCBaseListFragment{
    private XCBaseActivity activity;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (XCBaseActivity) getActivity();
    }

    @Override
    public void request() {
        url = ControlUrl.XC_SHOP_SCORE_PRODUCT_LIST_URL;
        params = "";
        jsonType = JSONHelper.XC_SHOP_SCORE_PRODUCT_LIST_DATAL;
        bailaAdapter = new IntegralMallListAdapter(mContext,new ArrayList());
        setListDao();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Activity.RESULT_FIRST_USER){
            switch (resultCode){
                case Activity.RESULT_OK:
                    request();
                    break;
                case Activity.RESULT_CANCELED:
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
        return null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext,ScoreProductInfoActivity.class);
        intent.putExtra(IntentFlag.PRODUCT_ID, ((ShopProductListVO) bailaAdapter.getItem(position - 1)).getId());
        startActivityForResult(intent,Activity.RESULT_FIRST_USER);
    }

    @Override
    public void onAdapterCLick(View view, int position) {

    }
}
