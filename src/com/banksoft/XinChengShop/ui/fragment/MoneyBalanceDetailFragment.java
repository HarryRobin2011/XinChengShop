package com.banksoft.XinChengShop.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import com.banksoft.XinChengShop.adapter.CollectProductAdapter;
import com.banksoft.XinChengShop.adapter.MoneyBalanceDetailAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.entity.ProductCollectionVO;
import com.banksoft.XinChengShop.ui.ShopProductInfoActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseListFragment;
import com.banksoft.XinChengShop.utils.JSONHelper;

import java.util.ArrayList;

/**
 * Created by Robin on 2016/4/8.
 * 账户金额明细  充值明细和 提现明细
 */
public class MoneyBalanceDetailFragment extends XCBaseListFragment {
    private XCBaseActivity activity;
    public Type currentType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (XCBaseActivity) getActivity();
    }

    @Override
    public void request() {
        if(Type.RECHARGE.equals(currentType)){// 充值明细
            url = ControlUrl.XC_MEMBER_MONEY_LIST;
            params = "memberId="+activity.member.getMember().getId();
            jsonType = JSONHelper.BALANCE_CHANGE_RECORD_DATA;
            bailaAdapter = new MoneyBalanceDetailAdapter(mContext,new ArrayList());

        }else if(Type.CASHOUT.equals(currentType)){// 提现明细
            url = ControlUrl.XC_WITH_DRAW_LIST_URL;
            params = "memberId="+activity.member.getMember().getId();
            jsonType = JSONHelper.BALANCE_CHANGE_RECORD_DATA;
            bailaAdapter = new MoneyBalanceDetailAdapter(mContext,new ArrayList());
        }
        xListView.setDividerHeight(1);
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

        if(Type.RECHARGE.equals(currentType)){// 充值明细
            return "充值明细";

        }else if(Type.CASHOUT.equals(currentType)){// 提现记录
           return "提现记录";
        }
        return "";
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onAdapterCLick(View view, int position) {

    }

    public static enum Type{
        RECHARGE,//充值明细
        CASHOUT;//提现明细

    }
}
