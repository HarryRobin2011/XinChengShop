package com.banksoft.XinChengShop.ui;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.ResetDao;
import com.banksoft.XinChengShop.entity.FoundPassword;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.utils.Check;
import com.banksoft.XinChengShop.widget.MyProgressDialog;

/**
 * Created by Harry on 2016-07-31.
 * 密码重置
 */
public class ResetPasswordActivity extends XCBaseActivity implements View.OnClickListener {
    private EditText telephone, checkCode, password, againPassword;
    private ImageView back;
    private TextView title;
    private Button sendCheckCode,ok;
    private ResetDao resetDao;
    private int second = 60;
    private MyProgressDialog myProgressDialog;
    private FoundPassword foundPassword;

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
                    break;
            }
        }
    };

    @Override
    protected void initContentView() {
        setContentView(R.layout.reset_password_layout);
    }

    @Override
    protected void initView() {
        telephone = (EditText) findViewById(R.id.telPhone);
        checkCode = (EditText) findViewById(R.id.check_code);
        password = (EditText) findViewById(R.id.password);
        againPassword = (EditText) findViewById(R.id.password_again);
        back = (ImageView) findViewById(R.id.title_back_button);
        title = (TextView) findViewById(R.id.titleText);
        sendCheckCode = (Button) findViewById(R.id.send_check_code);
        ok = (Button) findViewById(R.id.ok);
    }


    @Override
    protected void initData() {
        foundPassword = (FoundPassword) getIntent().getSerializableExtra(IntentFlag.DATA);
        telephone.setText(foundPassword.getMobilePhone());
        title.setText(R.string.reset_password);
        back.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initOperate() {
        back.setOnClickListener(this);
        sendCheckCode.setOnClickListener(this);
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back_button:
                finish();
                break;
            case R.id.send_check_code:
                String account1 = telephone.getText().toString();
                if ("".equals(account1)) {
                    showWarning(R.string.telephone_no_empty);
                    return;
                } else if (!Check.isCellphone(account1)) {
                    showWarning(R.string.telphone_format_error);
                    return;
                }
                if (resetDao == null) {
                    resetDao = new ResetDao(mContext);
                }

                new SendThread(account1).execute(resetDao);
                break;
            case R.id.ok:
                String checkCodeStr = checkCode.getText().toString();
                if(checkCodeStr == null || checkCodeStr.isEmpty()){
                     alert(R.string.check_code_empty);
                     return;
                }

                if(resetDao == null){
               resetDao = new ResetDao(mContext);
                }
                new ResetTask(checkCodeStr).execute(resetDao);
                break;
        }
    }

    /**
     * 发送验证码
     */
    private class SendThread extends AsyncTask<ResetDao, String, BaseData<String>> {
        String mobileTelephone;

        public SendThread(String mobileTelephone) {
            this.mobileTelephone = mobileTelephone;
        }

        @Override
        protected BaseData<String> doInBackground(ResetDao... params) {
            return params[0].sendCheckCode(mobileTelephone);
        }

        @Override
        protected void onPostExecute(BaseData<String> stringBaseData) {
            super.onPostExecute(stringBaseData);
            if (stringBaseData != null) {
                if (stringBaseData.isSuccess()) {
                    mHandler.sendEmptyMessage(0);
                    showWarning(R.string.check_code_send_success);
                } else {
                    showWarning(stringBaseData.getMsg().toString());
                }
            } else {
                showWarning(R.string.netWork_error);
            }
        }
    }

    private class ResetTask extends AsyncTask<ResetDao,String,IsFlagData>{
        String checkCode;

        public ResetTask(String checkCode) {
            this.checkCode = checkCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(myProgressDialog == null){
               myProgressDialog = new MyProgressDialog(ResetPasswordActivity.this);
            }
            myProgressDialog.showDialog(R.string.please_wait);
        }

        @Override
        protected IsFlagData doInBackground(ResetDao... params) {
            return params[0].resetPassword(foundPassword.getAccountId(),foundPassword.getMobilePhone(),checkCode);
        }

        @Override
        protected void onPostExecute(IsFlagData isFlagData) {
            super.onPostExecute(isFlagData);
            if(isFlagData != null){
                 if(isFlagData.isSuccess()){
                     finish();
                     alert(R.string.password_reset_success);
                 }else{
                     alert(isFlagData.getMsg().toString());
                 }
            }else{
                alert(R.string.net_error);
            }
        }
    }
}
