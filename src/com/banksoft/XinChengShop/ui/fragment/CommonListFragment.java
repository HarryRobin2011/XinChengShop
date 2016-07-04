package com.banksoft.XinChengShop.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import com.banksoft.XinChengShop.adapter.CommonListAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseListFragment;
import com.banksoft.XinChengShop.utils.JSONHelper;

import java.util.ArrayList;

/**
 * Created by harry_robin on 16/1/24.
 */
public class CommonListFragment extends XCBaseListFragment{
    private String type;
    private XCBaseActivity activity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = (String) getArguments().get(IntentFlag.COMMON_TYPE);
        activity = (XCBaseActivity) getActivity();
    }

    @Override
    public void request() {
        url = ControlUrl.XC_COMMON_LIST_URL;
        params =  "type="+type+"&memberId="+activity.member.getMember().getId();
        jsonType = JSONHelper.XC_COMMON_LIST_DATA;
        bailaAdapter = new CommonListAdapter(mContext,new ArrayList());
        xListView.setDividerHeight(10);
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
        return null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
}
