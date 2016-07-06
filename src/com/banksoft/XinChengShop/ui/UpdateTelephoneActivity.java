package com.banksoft.XinChengShop.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.dao.SettingDao;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.widget.ClearEditText;
import com.banksoft.XinChengShop.widget.MyProgressDialog;

/**
 * Created by admin on 2016/7/6.
 */
public class UpdateTelephoneActivity extends XCBaseActivity implements View.OnClickListener{
    private TextView title;
    private Button next,sendCheckCode;
    private ClearEditText telephoneEditText;
    private ImageView back;
    private int seconds = 60;
    private MyProgressDialog myProgressDialog;
    private SettingDao settingDao;

    private String checkCode;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(seconds > 0){
                seconds -- ;
                sendCheckCode.setText(String.valueOf(seconds));
                mHandler.sendEmptyMessageDelayed(0,1000);
            }else{
                seconds = 60;
                sendCheckCode.setText(R.string.achieve_check_code);
            }
        }
    };

    @Override
    protected void initContentView() {
        setContentView(R.layout.updata_telephone_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        next = (Button) findViewById(R.id.next);
        sendCheckCode = (Button) findViewById(R.id.send_check_code);
        telephoneEditText = (ClearEditText) findViewById(R.id.telPhone);
        back = (ImageView) findViewById(R.id.title_back_button);
    }

    @Override
    protected void initData() {
        title.setText(R.string.update_pay_password,null);
        back.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initOperate() {
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        sendCheckCode.setOnClickListener(this);
        telephoneEditText.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_button:
                finish();
                break;
            case R.id.send_check_code:
                mHandler.sendEmptyMessageDelayed(0,1000);
                break;
            case R.id.next:
                 if(settingDao == null){
                     settingDao = new SettingDao(mContext);
                 }
                 new MyTask().execute(settingDao);
                break;
        }
    }

    private  class MyTask extends AsyncTask<SettingDao,String,IsFlagData>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myProgressDialog = new MyProgressDialog(UpdateTelephoneActivity.this);
            myProgressDialog.showDialog(R.string.please_wait);
        }

        @Override
        protected IsFlagData doInBackground(SettingDao... params) {
            return params[0].updateMemberTelephone(member.getMember().getId(),checkCode);
        }

        @Override
        protected void onPostExecute(IsFlagData isFlagData) {
            super.onPostExecute(isFlagData);
            myProgressDialog.dismiss();
            if(isFlagData == null){
                if(isFlagData.isSuccess()){
                    Intent intent = new Intent(mContext,BindingTelephoneActivity.class);
                    startActivity(intent);
                }else{
                    alert(R.string.un_bind_success);
                }
            }else{
                alert(R.string.net_error);
            }
        }
    }
}


