package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.LoginDao;
import com.banksoft.XinChengShop.model.MemberData;
import com.banksoft.XinChengShop.model.MemberVOData;
import com.banksoft.XinChengShop.type.MergeType;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.utils.MD5Factory;
import com.banksoft.XinChengShop.widget.ClearEditText;

/**
 * Created by Robin on 2016/9/28.
 */
public class BindingLoginActivity extends XCBaseActivity implements View.OnClickListener {
    private TextView title;
    private ClearEditText account, password;
    private Button binding;
    private MergeType currentType;
    private ImageView back;

    private LoginDao loginDao;

    private String accountStr, passwordStr, openId;
    private ProgressDialog progressDialog;

    @Override
    protected void initContentView() {
        setContentView(R.layout.bing_login_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        account = (ClearEditText) findViewById(R.id.account);
        password = (ClearEditText) findViewById(R.id.password);
        binding = (Button) findViewById(R.id.binding_account);
        back = (ImageView) findViewById(R.id.title_back_button);
    }

    @Override
    protected void initData() {
        title.setText(R.string.binding_account);
        back.setVisibility(View.VISIBLE);
        currentType = (MergeType) getIntent().getSerializableExtra(IntentFlag.TYPE);
        openId = (String) getIntent().getSerializableExtra(IntentFlag.OPEN_ID);
    }

    @Override
    protected void initOperate() {
        back.setOnClickListener(this);
        binding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back_button:
                finish();
                break;
            case R.id.binding_account:
                accountStr = account.getText().toString().trim();
                passwordStr = password.getText().toString().trim();
                if (accountStr.isEmpty()) {
                    alert(R.string.account_not_empty);
                    return;
                } else if (passwordStr.isEmpty()) {
                    alert(R.string.password_not_empty);
                    return;
                }
                if (loginDao == null) {
                    loginDao = new LoginDao(mContext);
                }
                new MyTask().execute(loginDao);
                break;
        }
    }

    /**
     * 第三方关联
     */
    private class MyTask extends AsyncTask<LoginDao, String, MemberData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressDialog == null){
                progressDialog = ProgressDialog.show(BindingLoginActivity.this,"",getText(R.string.please_wait));
            }
        }

        @Override
        protected MemberData doInBackground(LoginDao... params) {
            return params[0].bindingLogin(accountStr, MD5Factory.encoding(passwordStr), openId, currentType);
        }

        @Override
        protected void onPostExecute(MemberData memberData) {
            super.onPostExecute(memberData);
            progressDialog.dismiss();
            if(memberData!= null){
                if(memberData.isSuccess()){
                    alert(R.string.bind_success);
                    saveLogin(memberData.getData());
                    setResult(Activity.RESULT_OK);
                    finish();
                }else{
                    alert(memberData.getMsg().toString());
                }
            }else{
                alert(R.string.net_error);
            }
        }
    }
}
