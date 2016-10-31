package com.banksoft.XinChengShop.ui.fragment.takeout;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.ui.AddMemberAddressListActivity;
import com.banksoft.XinChengShop.ui.LoginActivity;
import com.banksoft.XinChengShop.ui.takeout.TakeOutMainActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseFragment;

/**
 * Created by harry_robin on 16/3/18.
 */
public class TakeOutMySelfFragment extends XCBaseFragment implements View.OnClickListener {
    private TakeOutMainActivity activity;
    private TextView account;
    private TextView addressManager;
    private int LOGIN = 100;
    private int ADDRESS_MANAGER = 101;
    @Override
    public View initContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.take_out_my_self_layout,null);
    }

    @Override
    public void initView(View view) {
        activity = (TakeOutMainActivity) getActivity();
        account = (TextView) view.findViewById(R.id.account);
        activity.nearbyLayout.setVisibility(View.GONE);
        activity.title.setTextColor(getResources().getColor(R.color.text_black));
        activity.title.setText(R.string.my_self);
        addressManager = (TextView) view.findViewById(R.id.address_manager);
    }

    @Override
    public void initData() {
        if(activity.isLogin()){// 登陆了
            account.setText(activity.member.getMember().getAccount());
        }else {// 没有登陆
            account.setText(R.string.click_login);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 100){
            if(activity.isLogin()){// 登陆了
                account.setText(activity.member.getMember().getAccount());
                super.onActivityResult(requestCode, resultCode, data);
            }else {// 没有登陆
                account.setText(R.string.click_login);
            }
        }
    }

    @Override
    public void initOperation() {
         account.setOnClickListener(this);
         addressManager.setOnClickListener(this);
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
    public void onClick(View v) {
          switch (v.getId()){
              case R.id.account://登陆
                  if(!activity.isLogin()){//是否登陆
                     //没有登录
                      Intent intent = new Intent(mContext, LoginActivity.class);
                      startActivityForResult(intent,100);
                  }
                  break;
              case R.id.address_manager://收货地址管理
                  if(activity.isLogin()){
                      Intent intent = new Intent(mContext, AddMemberAddressListActivity.class);
                      startActivityForResult(intent,101);
                  }else{
                      Intent intent = new Intent(mContext, LoginActivity.class);
                      startActivityForResult(intent,100);
                  }

                  break;
          }
    }
}
