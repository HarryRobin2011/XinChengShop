package com.banksoft.XinChengShop.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.ShopListAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.entity.ShopVO;
import com.banksoft.XinChengShop.type.ShopType;
import com.banksoft.XinChengShop.ui.ShopDetailActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseListFragment;
import com.banksoft.XinChengShop.utils.JSONHelper;

import java.util.ArrayList;

/**
 * Created by admin on 2016/7/20.
 */
public class SelectShopCategoryFragment extends XCBaseListFragment{
    private ShopType type;
    private XCBaseActivity activity;
    private ImageView menu;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (XCBaseActivity) getActivity();
        menu = (ImageView) activity.findViewById(R.id.title_menu_btn);
    }

    @Override
    public void request() {
        type = (ShopType) getArguments().getSerializable(IntentFlag.SHOP_TYPE);
        url = ControlUrl.XC_SHOP_LIST_URL;
        params = "shopSeverType="+type+"&no="+no;
        jsonType = JSONHelper.SHOP_LIST_DATA;
        bailaAdapter = new ShopListAdapter(mContext,new ArrayList());
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
        Intent intent = new Intent(mContext,ShopDetailActivity.class);
        intent.putExtra(IntentFlag.SHOP_ID,((ShopVO)bailaAdapter.getItem(position - 1)).getId());
        startActivityForResult(intent,Activity.RESULT_FIRST_USER);
    }

    @Override
    public void onAdapterCLick(View view, int position) {

    }
}
