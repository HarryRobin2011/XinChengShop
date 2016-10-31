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
import com.banksoft.XinChengShop.ui.ShopInfoActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseListFragment;
import com.banksoft.XinChengShop.utils.JSONHelper;

import java.util.ArrayList;

/**
 * Created by harry_robin on 15/12/2.
 */
public class ShopListFragment extends XCBaseListFragment{
    private String type;
    private String no;
    private String name;
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
        type = getArguments().getString(IntentFlag.SHOP_TYPE,"");
        no = getArguments().getString(IntentFlag.NO,"");
        name = getArguments().getString(IntentFlag.NAME,"");

        url = ControlUrl.XC_SHOP_LIST_URL;
        if(type != null && !"".equals(type)){
            params += "shopServerType="+type;
        }
        if(no != null && !"".equals(no)){
            params += "&no="+no;
        }
        if(name != null && !name.isEmpty()){
            params +="&name="+name;
        }
        jsonType = JSONHelper.SHOP_LIST_DATA;
        bailaAdapter = new ShopListAdapter(mContext,new ArrayList());
        setListDao();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Activity.RESULT_FIRST_USER){
            switch (resultCode){
                case Activity.RESULT_OK:  //�����ɹ�
                   onRefresh();
                    break;
                case Activity.RESULT_CANCELED: //ȡ�����
                  //  onRefresh();
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
