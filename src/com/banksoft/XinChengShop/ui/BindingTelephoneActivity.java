package com.banksoft.XinChengShop.ui;

import android.os.AsyncTask;
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
public class BindingTelephoneActivity extends XCBaseActivity implements View.OnClickListener{
    private TextView title;
    private ImageView back;
    private ClearEditText telephone, checkCode;
    private Button sendCheckCode, update;
    private SettingDao settingDao;
    private MyProgressDialog myProgressDialog;
    private String telephoneStr,checkCodeStr;

    @Override
    protected void initContentView() {
        setContentView(R.layout.bing_telephone_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.titleText);
        telephone = (ClearEditText) findViewById(R.id.telPhone);
        checkCode = (ClearEditText) findViewById(R.id.check_code);
        sendCheckCode = (Button) findViewById(R.id.send_check_code);
        update = (Button) findViewById(R.id.update);
    }

    @Override
    protected void initData() {
        title.setText(R.string.bind_telephone_title);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
    }

    @Override
    protected void initOperate() {
        if(settingDao == null){
            settingDao = new SettingDao(mContext);
        }
        new MyTask().execute(settingDao);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_check_code:

                break;
            case R.id.update:

                break;
        }
    }

    private class MyTask extends AsyncTask<SettingDao,String,IsFlagData>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myProgressDialog = new MyProgressDialog(BindingTelephoneActivity.this);
            myProgressDialog.showDialog(R.string.please_wait);
        }

        @Override
        protected IsFlagData doInBackground(SettingDao... params) {
            return params[0].bindTelephone(member.getMember().getId(),telephoneStr,checkCodeStr);
        }

        @Override
        protected void onPostExecute(IsFlagData isFlagData) {
            super.onPostExecute(isFlagData);
            myProgressDialog.dismiss();
            if(isFlagData == null){
                if(isFlagData.isSuccess()){
                    finish();
                    alert(R.string.update_success);
                }else{
                    alert(isFlagData.getMsg().toString());
                }
            }else{
                alert(R.string.net_error);
            }
        }
    }
}
