package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.LoginDao;
import com.banksoft.XinChengShop.dao.RegisterDao;
import com.banksoft.XinChengShop.entity.MemberBO;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.model.MemberData;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.type.MergeType;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.utils.Check;
import com.banksoft.XinChengShop.utils.MD5Factory;
import com.banksoft.XinChengShop.widget.ClearEditText;
import com.banksoft.XinChengShop.widget.MyProgressDialog;

/**
 * Created with IntelliJ IDEA.
 * User: Harry
 * Date: 14-6-26
 * Time: 下午9:51
 * To change this template use File | Settings | File Templates.
 * 会员注册页面
 */
public class RegisterActivity extends XCBaseActivity implements View.OnClickListener{
    private TextView title;
    private ClearEditText account;
    private ClearEditText password;
    private ClearEditText confirmPassword;
    private ClearEditText phoneNumber;
    private ClearEditText checkCode;
    private ClearEditText email;
    private Button registerBtn,sendCheckCode;
    private MyProgressDialog progressDialog;
    private MemberBO memberBO;
    private RegisterDao registerDao;
    private ImageView back;
    private int second = 60;
    private BaseData<String> checkData;
    private MergeType operaType;
    private String openID;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:  //开始倒计时
                    if(second <= 0){
                        mHandler.sendEmptyMessage(1);
                        return;
                    }
                    second --;
                    sendCheckCode.setClickable(false);
                    sendCheckCode.setText(String.valueOf(second));
                    mHandler.sendEmptyMessageDelayed(0,1000);
                    break;
                case 1:
                    second = 60;
                    sendCheckCode.setClickable(true);
                    sendCheckCode.setText(R.string.send_check_code);
                    checkCode.setVisibility(View.GONE);
                    phoneNumber.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.register_layout);
    }

    @Override
    public void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        account = (ClearEditText) findViewById(R.id.account);
        password = (ClearEditText) findViewById(R.id.password);
        confirmPassword = (ClearEditText) findViewById(R.id.password_again);
        phoneNumber = (ClearEditText) findViewById(R.id.telPhone);
        registerBtn = (Button) findViewById(R.id.register);
        sendCheckCode = (Button) findViewById(R.id.send_check_code);
        checkCode = (ClearEditText) findViewById(R.id.check_code);
        email = (ClearEditText) findViewById(R.id.member_email);
    }

    @Override
    public void initData() {
        try {
            operaType = (MergeType) getIntent().getSerializableExtra(IntentFlag.TYPE);
            openID = getIntent().getStringExtra(IntentFlag.OPEN_ID);
        }catch (Exception e){
            e.printStackTrace();
        }

        back.setVisibility(View.VISIBLE);
        title.setText(R.string.member_register);
    }

    @Override
    protected void initOperate() {
        registerBtn.setOnClickListener(this);
        sendCheckCode.setOnClickListener(this);
        back.setOnClickListener(this);
    }


    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back_button:
                finish();
                break;
            case R.id.send_check_code:
                String account1 = phoneNumber.getText().toString();
                if ("".equals(account1)) {
                    showWarning(R.string.account_not_empty);
                    return;
                } else if (!Check.isCellphone(account1)) {
                    showWarning(R.string.account_standard);
                    return;
                }
                if (registerDao == null) {
                    registerDao = new RegisterDao(mContext);
                }

                registerDao.mobilePhone = account1;

                new SendThread().execute(registerDao);
                break;
            case R.id.register:
                if (checkInput()) {
                    if (progressDialog == null) {
                        progressDialog = new MyProgressDialog(RegisterActivity.this);
                    }
                    if(registerDao == null){
                       registerDao = new RegisterDao(mContext);
                    }
                    registerDao.mobileCode = checkCode.getText().toString().trim();
                    progressDialog.showDialog(getText(R.string.please_wait).toString());
                    new RequestThread().execute(memberBO);
                }
                break;

        }
    }

    private boolean checkInput() {
        String nameStr = account.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();
        String confirmPasswordStr = confirmPassword.getText().toString().trim();
        String phoneNumberStr = phoneNumber.getText().toString();
        String emailNumStr =  email.getText().toString();
        if ("".equals(nameStr)) {
            showWarning(R.string.account_not_empty);
            return false;
        } else if ("".equals(passwordStr)) {
            showWarning(R.string.password_not_empty);
            return false;
        } else if ("".equals(confirmPasswordStr)) {
            showWarning(R.string.confirm_not_empty);
            return false;
        } else if ("".equals(phoneNumberStr)) {
            showWarning(R.string.phoneNumber_not_empty);
            return false;
        } else if(!confirmPasswordStr.equals(passwordStr)) {
            showWarning(R.string.the_password_not_consistent);
            return false;
        } else if(checkData == null){
            showWarning(R.string.check_code_empty);
            return false;
        } else if("".equals(emailNumStr)){
            showWarning(R.string.email_no_empty);
            return false;
        } else if(!Check.checkEmail(emailNumStr)){
             showWarning(R.string.email_format_error);
            return false;
        }


        if(memberBO == null){
            memberBO = new MemberBO();
        }
        memberBO.setAccount(nameStr);
        memberBO.setPassword(MD5Factory.encoding(passwordStr));
        memberBO.setTelephone(phoneNumberStr);
        memberBO.setEmail(emailNumStr);
       // memberVo.setPayPassword(MD5Factory.encoding(payPasswordStr));
        return true;
    }

    private class RequestThread extends AsyncTask<MemberBO, String, IsFlagData> {

        @Override
        protected IsFlagData doInBackground(MemberBO... params) {
            if(registerDao == null){
                registerDao = new RegisterDao(mContext);
            }
            return registerDao.register(memberBO);
        }

        @Override
        protected void onPostExecute(IsFlagData registerData) {
            super.onPostExecute(registerData);
            if(progressDialog != null){
                progressDialog.dismiss();
            }
            if(registerData != null){
                if(registerData.isSuccess()){
                    if(operaType != null && openID != null){
                        //关联第三方
                        new CorrelationAccountThread().execute(new LoginDao(getApplicationContext()));
                    }else{
                        showWarning(R.string.register_success);
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                }else {
                    showWarning(registerData.getMsg().toString());
                }
            }
        }
    }

    private class SendThread extends AsyncTask<RegisterDao, String, BaseData<String>> {
        @Override
        protected BaseData<String> doInBackground(RegisterDao... params) {
            return params[0].sendCheckCode();
        }

        @Override
        protected void onPostExecute(BaseData<String> stringBaseData) {
            super.onPostExecute(stringBaseData);
            if (stringBaseData != null) {
                if (stringBaseData.isSuccess()) {
                    mHandler.sendEmptyMessage(0);
                    checkCode.setVisibility(View.VISIBLE);
                    phoneNumber.setVisibility(View.GONE);
                    showWarning(R.string.check_code_send_success);
                    checkData = stringBaseData;
                } else {
                    showWarning(checkData.getMsg().toString());
                }
            } else {
                showWarning(R.string.netWork_error);
            }
        }
    }

    private class CorrelationAccountThread extends AsyncTask<LoginDao,String,MemberData>{

        @Override
        protected MemberData doInBackground(LoginDao... params) {
            return params[0].bindingLogin(memberBO.getAccount(),memberBO.getPassword(),openID,operaType);
        }

        @Override
        protected void onPostExecute(MemberData memberData) {
            super.onPostExecute(memberData);
            if(memberData != null){
                if(memberData.isSuccess()){
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
