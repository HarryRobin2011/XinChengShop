package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

/**
 * Created by admin on 2016/7/6.
 */
public class SettingActivity extends XCBaseActivity implements View.OnClickListener {
    private LinearLayout checkLoginPasswrod, checkPayPassword, checkTelphone, checkEmail, about, quit;
    private TextView title;
    private ImageView back;

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
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
    }

    @Override
    protected void initData() {
        title.setText(R.string.setting);
        back.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initOperate() {
        checkLoginPasswrod.setOnClickListener(this);
        checkPayPassword.setOnClickListener(this);
        checkTelphone.setOnClickListener(this);
        checkEmail.setOnClickListener(this);
        about.setOnClickListener(this);
        quit.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_FIRST_USER) {
            if (resultCode == Activity.RESULT_OK) {// 更新登录成功，重新登陆
                setResult(Activity.RESULT_OK);
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_login_password:
                Intent intent = new Intent(mContext, UpdateLoginPasswordActivity.class);
                startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                break;
            case R.id.check_pay_password:
                Intent payIntent = new Intent(mContext, UpdatePayPasswordActivity.class);
                startActivityForResult(payIntent, RESULT_FIRST_USER);
                break;
            case R.id.check_telphone:
                Intent telephoneIntent = new Intent(mContext, UpdateTelephoneActivity.class);
                startActivityForResult(telephoneIntent, Activity.RESULT_FIRST_USER);
                break;
            case R.id.check_email:
                Intent emailIntent = new Intent(mContext, UpdateEmailActivity.class);
                startActivityForResult(emailIntent, Activity.RESULT_FIRST_USER);
                break;
            case R.id.about:
                Intent settingIntent = new Intent(mContext, AboutActivity.class);
                startActivityForResult(settingIntent, Activity.RESULT_FIRST_USER);
                break;
            case R.id.quit:
                clearLogin();
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.title_back_button:
                finish();
                break;
        }
    }
}
