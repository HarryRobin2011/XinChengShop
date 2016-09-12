package com.banksoft.XinChengShop.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.WithDrawTariffAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.MyBankDao;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

import java.io.Serializable;

/**
 * Created by Robin on 2016/7/28.
 */
public class WithDrawTariffActivity extends XCBaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    private ImageView back;
    private ListView listView;
    private TextView title;
    private WithDrawTariffAdapter withDrawTariffAdapter;
    private MyBankDao myBankDao;
    private ProgressBar progressBar;

    @Override
    protected void initContentView() {
        setContentView(R.layout.with_draw_tariff_layout);
    }

    @Override
    protected void initView() {
        back = (ImageView) findViewById(R.id.title_back_button);
        listView = (ListView) findViewById(R.id.list_view);
        title = (TextView) findViewById(R.id.titleText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    protected void initData() {
        title.setText(R.string.with_draw_tariff);
        back.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initOperate() {
        back.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        if (myBankDao == null) {
            myBankDao = new MyBankDao(mContext);
        }
        new MyTask().execute(myBankDao);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back_button:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra(IntentFlag.DATA, (Serializable) withDrawTariffAdapter.dataList.get(position));
        setResult(100,intent);
        finish();
    }

    private class MyTask extends AsyncTask<MyBankDao, String, MemberRateVOListData> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected MemberRateVOListData doInBackground(MyBankDao... params) {
            return params[0].getMemberRateVOListData(member.getMember().getId());
        }

        @Override
        protected void onPostExecute(MemberRateVOListData memberRateVOListData) {
            super.onPostExecute(memberRateVOListData);
            progressBar.setVisibility(View.GONE);
            if (memberRateVOListData != null) {
                if (memberRateVOListData.isSuccess()) {
                    showTariffView(memberRateVOListData);
                } else {
                    alert(memberRateVOListData.getMsg().toString());
                }
            } else {
                alert(R.string.net_error);
            }
        }
    }

    private void showTariffView(MemberRateVOListData memberRateVOListData) {
        if (withDrawTariffAdapter == null) {
            withDrawTariffAdapter = new WithDrawTariffAdapter(mContext, memberRateVOListData.getData());
        }
        listView.setAdapter(withDrawTariffAdapter);
    }
}
