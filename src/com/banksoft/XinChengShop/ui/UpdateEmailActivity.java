package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.dao.SettingDao;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.model.MemberInfoData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.utils.CommonUtil;
import com.banksoft.XinChengShop.widget.ClearEditText;
import com.banksoft.XinChengShop.widget.MyProgressDialog;

/**
 * Created by admin on 2016/7/6.
 */
public class UpdateEmailActivity extends XCBaseActivity implements View.OnClickListener {
    private ClearEditText oldEmail, newEmail, telephone, checkCode;
    private Button sendCheckCode, update;
    private TextView title;
    private ImageView back;
    private MyProgressDialog progressDialog;
    private String checkCodeStr, emailStr;
    private SettingDao settingDao;

    @Override
    protected void initContentView() {
        setContentView(R.layout.update_email_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        oldEmail = (ClearEditText) findViewById(R.id.email_old);
        newEmail = (ClearEditText) findViewById(R.id.email_new);
        telephone = (ClearEditText) findViewById(R.id.telPhone);
        checkCode = (ClearEditText) findViewById(R.id.check_code);
        sendCheckCode = (Button) findViewById(R.id.send_check_code);
        checkCode = (ClearEditText) findViewById(R.id.check_code);
        update = (Button) findViewById(R.id.titleRightButton);
    }

    @Override
    protected void initData() {
        title.setText(R.string.update_email_title);
        update.setText(R.string.update);
        update.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);

    }

    @Override
    protected void initOperate() {
        back.setOnClickListener(this);
        sendCheckCode.setOnClickListener(this);
        update.setOnClickListener(this);
        checkCodeStr = checkCode.getText().toString();
        if (checkCodeStr == null && checkCodeStr.isEmpty()) {
            alert(R.string.check_code_empty);
            return;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back_button:
                finish();
                break;
            case R.id.send_check_code:
                break;
            case R.id.titleRightButton:
                emailStr = newEmail.getText().toString();
                checkCodeStr = checkCode.getText().toString();
                if (emailStr == null && emailStr.isEmpty()) {
                    alert(R.string.email_no_empty);
                    return;
                } else if (checkCodeStr == null && checkCodeStr.isEmpty()) {
                    alert(R.string.check_code_empty);
                    return;
                }
                if (settingDao == null) {
                    settingDao = new SettingDao(mContext);
                }
                new MyTask().execute(settingDao);
                break;
        }
    }

    private class MyTask extends AsyncTask<SettingDao, String, MemberInfoData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new MyProgressDialog(UpdateEmailActivity.this);
            progressDialog.showDialog(R.string.please_wait);

        }

        @Override
        protected MemberInfoData doInBackground(SettingDao... params) {
            return params[0].updateMemberInfoEmail(member.getMember().getId(), checkCodeStr, emailStr);
        }

        @Override
        protected void onPostExecute(MemberInfoData memberInfoData) {
            super.onPostExecute(memberInfoData);
            if (memberInfoData == null) {
                if (memberInfoData.isSuccess()) {
                    alert(R.string.update_success_resert_login);
                    clearLogin();
                    setResult(Activity.RESULT_OK);
                    CommonUtil.operationKeyboard(getApplicationContext());
                    finish();
                } else {
                    alert(memberInfoData.getMsg().toString());
                }
            } else {
                alert(R.string.net_error);
            }
        }
    }
}
