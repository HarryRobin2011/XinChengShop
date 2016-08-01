package com.banksoft.XinChengShop.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.FoundPasswordDao;
import com.banksoft.XinChengShop.entity.FoundPassword;
import com.banksoft.XinChengShop.model.FoundPasswordData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.widget.ClearEditText;
import com.banksoft.XinChengShop.widget.MyProgressDialog;

/**
 * Created by Harry on 2016-07-31.
 */
public class FoundPasswordActivity extends XCBaseActivity implements View.OnClickListener{
    private TextView title;
    private ClearEditText account;
    private Button next;
    private ImageView back;
    private FoundPasswordDao foundPasswordDao;
    private MyProgressDialog myProgressDialog;
    @Override
    protected void initContentView() {
        setContentView(R.layout.found_password_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        account = (ClearEditText) findViewById(R.id.account);
        next = (Button) findViewById(R.id.next);
        back = (ImageView) findViewById(R.id.title_back_button);
    }

    @Override
    protected void initData() {
       back.setVisibility(View.VISIBLE);
       back.setOnClickListener(this);
        title.setText(R.string.found_password);
    }

    @Override
    protected void initOperate() {
       next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_button:
                finish();
                break;
            case R.id.next:
                String accountStr = account.getText().toString();
                if(accountStr == null || accountStr.isEmpty()){
                    alert(R.string.account_not_empty);
                    return;
                }

                if(foundPasswordDao == null){
                    foundPasswordDao = new FoundPasswordDao(mContext);
                }
                new MyTask(accountStr).execute(foundPasswordDao);
                break;
        }
    }

    private class MyTask extends AsyncTask<FoundPasswordDao,String,FoundPasswordData>{
        String userName;

        public MyTask(String userName) {
            this.userName = userName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(myProgressDialog == null){
               myProgressDialog = new MyProgressDialog(FoundPasswordActivity.this);
            }
            myProgressDialog.showDialog(R.string.please_wait);
        }

        @Override
        protected FoundPasswordData doInBackground(FoundPasswordDao... params) {
            return params[0].getUserName(userName);
        }

        @Override
        protected void onPostExecute(FoundPasswordData foundPassword) {
            super.onPostExecute(foundPassword);
            myProgressDialog.dismiss();
            if(foundPassword != null){
                if(foundPassword.isSuccess()){
                    Intent intent = new Intent(mContext,ResetPasswordActivity.class);
                    intent.putExtra(IntentFlag.DATA,foundPassword.getData());
                    startActivity(intent);
                }else{
                    alert(foundPassword.getMsg().toString());
                }
            }else{
                alert(R.string.net_error);
            }
        }
    }
}
