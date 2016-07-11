package com.banksoft.XinChengShop.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import com.banksoft.XinChengShop.adapter.CollectShopAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.entity.ShopCollectionVO;
import com.banksoft.XinChengShop.ui.ShopInfoActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseListFragment;
import com.banksoft.XinChengShop.utils.JSONHelper;

import java.util.ArrayList;

/**
 * Created by harry_robin on 15/11/23.
 */
public class ShopCollectListFragment extends XCBaseListFragment{
    private XCBaseActivity activity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (XCBaseActivity) getActivity();
    }

    @Override
    public void request() {
        url = ControlUrl.XC_SHOP_COLLECT_LIST_URL;
        params = "memberId="+activity.member.getMember().getId();
        jsonType = JSONHelper.SHOP_COLLECT_LIST_DATA;
        bailaAdapter = new CollectShopAdapter(mContext,new ArrayList());
        xListView.setDividerHeight(20);
        cacheFlag = false;
        setListDao();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Activity.RESULT_FIRST_USER){
            switch (resultCode){
                case Activity.RESULT_OK:  //�����ɹ�
                    request();
                    break;
                case Activity.RESULT_CANCELED: //ȡ�����
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
        return "店铺";
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext,ShopInfoActivity.class);
        intent.putExtra(IntentFlag.SHOP_ID,((ShopCollectionVO)bailaAdapter.getItem(position - 1)).getShopId());
        startActivityForResult(intent,Activity.RESULT_FIRST_USER);
    }

    @Override
    public void onAdapterCLick(View view, int position) {

    }
}
