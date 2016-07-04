package com.banksoft.XinChengShop.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import com.banksoft.XinChengShop.adapter.OrderLisAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.entity.OrderVO;
import com.banksoft.XinChengShop.type.OrderMaster;
import com.banksoft.XinChengShop.ui.OrderInfoActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseListFragment;
import com.banksoft.XinChengShop.utils.JSONHelper;

import java.util.ArrayList;

/**
 * Created by Robin on 2015/2/2.
 */
public class OrderListFragment extends XCBaseListFragment {
    private String type;
    private XCBaseActivity activity;
    private String orderMaster;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = (String) getArguments().get(IntentFlag.ORDER_TYPE);
        orderMaster =  getArguments().getString(IntentFlag.Order_MASTER,"");
        activity = (XCBaseActivity) getActivity();
    }

    @Override
    public void request() {
        url = ControlUrl.ORDER_LIST_URL;
        if(OrderMaster.SELLER.name().equals(orderMaster)){//卖家订单
            params = "status="+type+"&shopId="+activity.member.getShop().getId();
        }else{

            params = "status="+type+"&memberId="+activity.member.getMember().getId();
        }

        jsonType = JSONHelper.ORDER_LIST_DATA;
        bailaAdapter = new OrderLisAdapter(mContext,new ArrayList());
        xListView.setDividerHeight(20);
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
        Intent intent = new Intent(mContext,OrderInfoActivity.class);
        intent.putExtra(IntentFlag.ORDER_VO,(OrderVO)bailaAdapter.getItem(position - 1));
        startActivityForResult(intent,Activity.RESULT_FIRST_USER);
    }
}
