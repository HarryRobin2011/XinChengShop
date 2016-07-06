package com.banksoft.XinChengShop.ui;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.dao.SettingDao;
import com.banksoft.XinChengShop.model.MemberVOData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.widget.ClearEditText;
import com.banksoft.XinChengShop.widget.MyProgressDialog;

/**
 * Created by admin on 2016/7/6.
 */
public class UpdateLoginPasswordActivity extends XCBaseActivity implements View.OnClickListener{
    private ClearEditText memberAccount,oldPassword,newPassword;
    private Button update;
    private ImageView back;
    private TextView title;
    private SettingDao settingDao;
    private MyProgressDialog progressDialog;

    private String passwordStr;
    private String oldPasswordStr;

    @Override
    protected void initContentView() {
        setContentView(R.layout.check_login_password_layout);
    }

    @Override
    protected void initView() {
        memberAccount = (ClearEditText) findViewById(R.id.member_account);
        oldPassword = (ClearEditText) findViewById(R.id.old_password);
        newPassword = (ClearEditText) findViewById(R.id.new_password);
        back = (ImageView) findViewById(R.id.title_back_button);
        title = (TextView) findViewById(R.id.titleText);
        update = (Button) findViewById(R.id.update);
    }

    @Override
    protected void initData() {
        title.setText(R.string.update_login_password);
        back.setVisibility(View.VISIBLE);
        memberAccount.setText(member.getMember().getAccount());
        memberAccount.setEnabled(true);
    }

    @Override
    protected void initOperate() {
        back.setOnClickListener(this);
        update.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_button:
                finish();
                break;
            case R.id.update:
                passwordStr = newPassword.getText().toString();
                oldPasswordStr = oldPassword.getText().toString();

                if(passwordStr == null && passwordStr.isEmpty()){
                    alert(R.string.new_password_no_empty);
                    return;
                };
                if(oldPasswordStr == null && oldPasswordStr.isEmpty()){
                    alert(R.string.old_password_no_empty);
                    return;
                }
                if(settingDao == null){
                    settingDao = new SettingDao(mContext);
                }
                new MyTask().execute(settingDao);
                break;
        }
    }

    /**
     *
     */
    private class MyTask extends AsyncTask<SettingDao,String,MemberVOData>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new MyProgressDialog(UpdateLoginPasswordActivity.this);
            progressDialog.showDialog(R.string.please_wait);
        }

        @Override
        protected MemberVOData doInBackground(SettingDao... params) {
            return params[0].updateMemberLoginPassword(member.getMember().getId(),member.getMember().getAccount(),oldPasswordStr,passwordStr);
        }

        @Override
        protected void onPostExecute(MemberVOData memberVOData) {
            super.onPostExecute(memberVOData);
            progressDialog.dismiss();
            if(memberVOData != null){
               alert(R.string.update_success);
                finish();
            }else{
                alert(R.string.net_error);
            }
        }
    }


}
