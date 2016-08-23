package com.banksoft.XinChengShop.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.OrderLisAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.OrderListDao;
import com.banksoft.XinChengShop.entity.OrderVO;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.type.OrderMaster;
import com.banksoft.XinChengShop.type.OrderStatus;
import com.banksoft.XinChengShop.ui.OrderInfoActivity;
import com.banksoft.XinChengShop.ui.PayActivity;
import com.banksoft.XinChengShop.ui.PublishCommentActivity;
import com.banksoft.XinChengShop.ui.ShopDetailActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseListFragment;
import com.banksoft.XinChengShop.utils.JSONHelper;
import com.banksoft.XinChengShop.widget.MyProgressDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robin on 2016/8/23.
 * 退货返修
 */
public class OrderReturnListFragment extends XCBaseListFragment{
    private String type;
    private XCBaseActivity activity;
    private OrderListDao orderListDao;
    private MyProgressDialog myProgressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (XCBaseActivity) getActivity();
    }

    @Override
    public void request() {
        type = (String) getArguments().get(IntentFlag.ORDER_STATUS);
        url = ControlUrl.ORDER_LIST_URL;
        params = "status=" + type + "&memberId=" + activity.member.getMember().getId();
        jsonType = JSONHelper.ORDER_LIST_DATA;
        bailaAdapter = new OrderLisAdapter(mContext, new ArrayList());
        xListView.setDividerHeight(20);
        setListDao();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Activity.RESULT_FIRST_USER) {
            switch (resultCode) {
                case Activity.RESULT_OK:  //�����ɹ�
                    onRefresh();
                    break;
                case Activity.RESULT_CANCELED: //ȡ�����
                    onRefresh();
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
//        Intent intent = new Intent(mContext,OrderInfoActivity.class);
//        intent.putExtra(IntentFlag.ORDER_VO,(OrderVO)bailaAdapter.getItem(position - 1));
//        startActivityForResult(intent,Activity.RESULT_FIRST_USER);
    }


    @Override
    public void onAdapterCLick(View view, int position) {

    }
}
