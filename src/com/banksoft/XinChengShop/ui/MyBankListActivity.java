package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.MyBankListAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.MyBankDao;
import com.banksoft.XinChengShop.entity.Bank;
import com.banksoft.XinChengShop.model.BankListData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

import java.io.Serializable;

/**
 * Created by Robin on 2016/7/26.
 */
public class MyBankListActivity extends XCBaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    private TextView title;
    private ImageView back;
    private ListView mListView;
    private ProgressBar mProgressBar;
    private MyBankDao myBankDao;
    private MyBankListAdapter myBankListAdapter;
    private Button add;
    private boolean isSelect;// 是否选择用

    @Override
    protected void initContentView() {
        setContentView(R.layout.list_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mListView = (ListView) findViewById(R.id.list_view);
        add = (Button) findViewById(R.id.titleRightButton);
    }

    @Override
    protected void initData() {
        isSelect = getIntent().getBooleanExtra(IntentFlag.IS_SELECT,false);
        title.setText(R.string.my_bank_card);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        add.setOnClickListener(this);
    }

    @Override
    protected void initOperate() {
        mListView.setOnItemClickListener(this);
       if(myBankDao == null){
           myBankDao = new MyBankDao(mContext);
       }
       new MyTask().execute(myBankDao);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_button:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(isSelect){
            Bank bank = (Bank) myBankListAdapter.dataList.get(position);
            Intent intent = new Intent();
            intent.putExtra(IntentFlag.DATA,bank);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }else{
            Intent intent = new Intent(mContext,MyBankEditActivity.class);
            intent.putExtra(IntentFlag.DATA, (Serializable) myBankListAdapter.dataList.get(position));
            startActivity(intent);
        }

    }

    private class MyTask extends AsyncTask<MyBankDao,String,BankListData>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected BankListData doInBackground(MyBankDao... params) {
            return params[0].getBankListData(member.getMember().getId());
        }

        @Override
        protected void onPostExecute(BankListData bankListData) {
            super.onPostExecute(bankListData);
            mProgressBar.setVisibility(View.GONE);
            if(bankListData != null){
               if(bankListData.isSuccess()){
                   if(myBankListAdapter == null){
                       myBankListAdapter = new MyBankListAdapter(mContext,bankListData.getData());
                   }
                   mListView.setAdapter(myBankListAdapter);
               }else{
                   alert(bankListData.getMsg().toString());
               }
            }else{
                alert(R.string.net_error);
            }
        }
    }
}
