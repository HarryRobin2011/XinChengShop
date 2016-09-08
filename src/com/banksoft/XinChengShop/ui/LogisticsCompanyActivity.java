package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.LogisticsCompanyListAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.LogisticsDao;
import com.banksoft.XinChengShop.model.ExpressCompanyCellListData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

import java.io.Serializable;

/**
 * Created by Robin on 2016/9/8.
 */
public class LogisticsCompanyActivity extends XCBaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ImageView back;
    private TextView title;
    private LogisticsDao logisticsDao;
    private ListView listview;
    private ProgressBar progressBar;
    private LogisticsCompanyListAdapter logisticsCompanyListAdapter;

    @Override
    protected void initContentView() {
        setContentView(R.layout.logistics_company_layout);
    }

    @Override
    protected void initView() {
        back = (ImageView) findViewById(R.id.title_back_button);
        title = (TextView) findViewById(R.id.titleText);
        listview = (ListView) findViewById(R.id.list_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    protected void initData() {
        back.setVisibility(View.VISIBLE);
        title.setText(R.string.please_select_logistic_company);
    }

    @Override
    protected void initOperate() {
        back.setOnClickListener(this);
        listview.setOnItemClickListener(this);
        if (logisticsDao == null) {
            logisticsDao = new LogisticsDao(mContext);
        }
        new MyTask().execute(logisticsDao);
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
        intent.putExtra(IntentFlag.DATA, (Serializable) logisticsCompanyListAdapter.dataList.get(position));
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private class MyTask extends AsyncTask<LogisticsDao, String, ExpressCompanyCellListData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ExpressCompanyCellListData doInBackground(LogisticsDao... params) {
            return params[0].getExpressCompanyListData();
        }

        @Override
        protected void onPostExecute(ExpressCompanyCellListData expressCompanyCellListData) {
            super.onPostExecute(expressCompanyCellListData);
            progressBar.setVisibility(View.GONE);
            if (expressCompanyCellListData != null) {
                if (expressCompanyCellListData.isSuccess()) {
                    if (logisticsCompanyListAdapter == null) {
                        logisticsCompanyListAdapter = new LogisticsCompanyListAdapter(mContext, expressCompanyCellListData.getData());
                    }
                    listview.setAdapter(logisticsCompanyListAdapter);
                } else {
                    alert(R.string.net_is_weak);
                }
            } else {
                alert(R.string.net_error);
            }
        }
    }
}
