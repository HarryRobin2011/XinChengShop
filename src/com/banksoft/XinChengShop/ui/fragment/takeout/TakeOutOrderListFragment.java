package com.banksoft.XinChengShop.ui.fragment.takeout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.OrderLisAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.entity.OrderVO;
import com.banksoft.XinChengShop.type.OrderType;
import com.banksoft.XinChengShop.ui.LoginActivity;
import com.banksoft.XinChengShop.ui.OrderInfoActivity;
import com.banksoft.XinChengShop.ui.takeout.TakeOutMainActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseListFragment;
import com.banksoft.XinChengShop.utils.JSONHelper;

import java.util.ArrayList;

/**
 * Created by harry_robin on 16/3/18.
 */
public class TakeOutOrderListFragment extends XCBaseListFragment {
    private TakeOutMainActivity activity;
    private String shopId;
    private String memberId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (TakeOutMainActivity) getActivity();
        if (!activity.isLogin()) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivityForResult(intent, Activity.RESULT_FIRST_USER);
        }

    }






    @Override
    public void request() {
        if(activity.isLogin()){// 登陆了
            url = ControlUrl.ORDER_LIST_URL;

            params = "&memberId=" + activity.member.getMember().getId() + "orderType=" + OrderType.SERVER.name();


            jsonType = JSONHelper.ORDER_LIST_DATA;
            bailaAdapter = new OrderLisAdapter(mContext, new ArrayList());
            xListView.setDividerHeight(10);

            activity.title.setTextColor(getResources().getColor(R.color.text_black));
            activity.title.setText(R.string.order);
            activity.nearbyLayout.setVisibility(View.GONE);
            setListDao();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Activity.RESULT_FIRST_USER) {
            switch (resultCode) {
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
        Intent intent = new Intent(mContext, OrderInfoActivity.class);
        intent.putExtra(IntentFlag.ORDER_VO, (OrderVO) bailaAdapter.getItem(position - 1));
        startActivityForResult(intent, Activity.RESULT_FIRST_USER);
    }


    @Override
    public void onAdapterCLick(View view, int position) {

    }
}
