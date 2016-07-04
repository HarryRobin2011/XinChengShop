package com.banksoft.XinChengShop.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.ExpressAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.ExpressDao;
import com.banksoft.XinChengShop.entity.chinaLocation.County;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseFragment;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by harry_robin on 15/12/4.
 */
public class ExpressFragment extends XCBaseFragment{
    public ListView listView;
    private ProgressBar progressBar;
    public ExpressAdapter expressAdapter;
    private ExpressDao expressDao;
    private List dataList;
    private String code;
    private int type = 0;
    private String parentId = "86";
    private StringBuffer nameBuffer;
    private XCBaseActivity activity;
    public Class currentCls;
    @Override
    public View initContentView() {
        activity = (XCBaseActivity) getActivity();
        return LayoutInflater.from(mContext).inflate(R.layout.list_layout,null);
    }

    @Override
    public void initView(View view) {
        listView = (ListView) view.findViewById(R.id.list_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }

    @Override
    public void initData() {
        parentId = getArguments().getString(IntentFlag.PARENT_ID,"86");
        currentCls = (Class) getArguments().get(IntentFlag.CLASS);
    }

    @Override
    public void initOperation() {
        if(expressDao == null){
            expressDao = new ExpressDao(mContext);
        }
        new MyThread().execute(expressDao);
    }

    @Override
    public Fragment getInstance() {
        return null;
    }

    @Override
    public CharSequence getTitle() {
        return null;
    }



    private class MyThread extends AsyncTask<ExpressDao, String, List> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List doInBackground(ExpressDao... params) {
            return params[0].findAreaList(type,parentId);
        }

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            if (list != null) {
                dataList = list;
                if (expressAdapter == null) {
                    expressAdapter = new ExpressAdapter(mContext, dataList);
                    listView.setAdapter(expressAdapter);
                }
                expressAdapter.dataList = dataList;
                expressAdapter.notifyDataSetChanged();
            }
        }
    }
}
