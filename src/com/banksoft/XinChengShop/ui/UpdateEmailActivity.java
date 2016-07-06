package com.banksoft.XinChengShop.ui;

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
import com.banksoft.XinChengShop.widget.ClearEditText;
import com.banksoft.XinChengShop.widget.MyProgressDialog;

/**
 * Created by admin on 2016/7/6.
 */
public class UpdateEmailActivity extends XCBaseActivity implements View.OnClickListener{
    private ClearEditText oldEmail, newEmail, telephone, checkCode;
    private Button sendCheckCode, update;
    private TextView title;
    private ImageView back;
    private MyProgressDialog progressDialog;

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
    }

    @Override
    protected void initData() {
        title.setText(R.string.update_email_title);
        back.setVisibility(View.GONE);
        back.setOnClickListener(this);
    }

    @Override
    protected void initOperate() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_button:
                finish();
                break;
            case R.id.send_check_code:
                break;
            case R.id.update:

                break;
        }
    }

    private class MyTask extends AsyncTask<SettingDao,String,MemberInfoData>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected MemberInfoData doInBackground(SettingDao... params) {
            return null;
        }

        @Override
        protected void onPostExecute(MemberInfoData memberInfoData) {
            super.onPostExecute(memberInfoData);
        }
    }
}
