package com.banksoft.XinChengShop.ui;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

/**
 * Created by admin on 2016/7/6.
 */
public class SettingActivity extends XCBaseActivity implements View.OnClickListener{
     private LinearLayout checkLoginPasswrod,checkPayPassword,checkTelphone,checkEmail,about,quit;
    @Override
    protected void initContentView() {
        setContentView(R.layout.xc_setting_layout);
    }

    @Override
    protected void initView() {
        checkLoginPasswrod = (LinearLayout) findViewById(R.id.check_login_password);
        checkPayPassword = (LinearLayout) findViewById(R.id.check_pay_password);
        checkTelphone = (LinearLayout) findViewById(R.id.check_telphone);
        checkEmail = (LinearLayout) findViewById(R.id.check_email);
        about = (LinearLayout) findViewById(R.id.about);
        quit = (LinearLayout) findViewById(R.id.quit);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initOperate() {
        checkLoginPasswrod.setOnClickListener(this);
        checkPayPassword.setOnClickListener(this);
        checkTelphone.setOnClickListener(this);
        checkEmail.setOnClickListener(this);
        about.setOnClickListener(this);
        quit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.check_login_password:
                Intent intent = new Intent(mContext,UpdateLoginPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.check_pay_password:
                Intent payIntent = new Intent(mContext,UpdatePayPasswordActivity.class);
                startActivity(payIntent);
                break;
            case R.id.check_telphone:
                Intent telephoneIntent = new Intent(mContext,UpdateTelephoneActivity.class);
                startActivity(telephoneIntent);
                break;
            case R.id.check_email:
                Intent emailIntent = new Intent(mContext,UpdateEmailActivity.class);
                startActivity(emailIntent);
                break;
            case R.id.about:
                break;
            case R.id.quit:
                break;
        }
    }
}
