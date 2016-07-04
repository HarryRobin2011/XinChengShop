package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.AreaListAdapter;
import com.banksoft.XinChengShop.adapter.ExpressAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.ExpressDao;
import com.banksoft.XinChengShop.entity.chinaLocation.County;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by harry_robin on 15/12/4.
 * 选择区域
 */
public class AreaListActivity extends XCBaseActivity implements AdapterView.OnItemClickListener,View.OnClickListener{
    private TextView title;
    private ListView listView;
    private ProgressBar progressBar;
    private int type = 0;
    private String parentId = "86";
    private List currentDataList;
    private AreaListAdapter areaListAdapter;
    private View headView;
    private TextView aroName;
    private ExpressDao expressDao;
    private String code;
    private StringBuffer nameBuffer;

    @Override
    protected void initContentView() {
        setContentView(R.layout.address_select_list_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.title);
        listView = (ListView) findViewById(R.id.list_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        headView = LayoutInflater.from(mContext).inflate(R.layout.location_text_layout,null);
        aroName = (TextView) headView.findViewById(R.id.name);
    }

    @Override
    protected void initData() {
        title.setText(R.string.please_select_area);
        listView.addHeaderView(headView);
        listView.setAdapter(null);
        aroName.setText(areaName);
        listView.setDividerHeight(1);
        nameBuffer = new StringBuffer();
    }

    @Override
    protected void initOperate() {
        if(expressDao == null){
            expressDao = new ExpressDao(mContext);
        }
        new MyThread().execute(expressDao);
        listView.setOnItemClickListener(this);
        aroName.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.name:

                break;
        }
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
            progressBar.setVisibility(View.GONE);
            if (list != null) {
                currentDataList = list;
                if (areaListAdapter == null) {
                    areaListAdapter = new AreaListAdapter(mContext, currentDataList);
                    listView.setAdapter(areaListAdapter);
                }
                areaListAdapter.dataList = currentDataList;
                areaListAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object o = areaListAdapter.getItem(position - 1);
        try {
            Field nameField = o.getClass().getDeclaredField("name");
            nameField.setAccessible(true);
            String name = (String) nameField.get(o);
            nameBuffer.append(name).append(" ");
            if (County.class.getName().equals(o.getClass().getName())) {
                Field codeField = o.getClass().getDeclaredField("code");
                codeField.setAccessible(true);
                code = (String) codeField.get(o);
                Intent intent = new Intent();
                intent.putExtra(IntentFlag.CODE,code);
                intent.putExtra(IntentFlag.AREA_NAME,nameBuffer.toString());
                setResult(Activity.RESULT_OK, intent);
                finish();
            } else {
                Field parentField = o.getClass().getDeclaredField("id");
                parentField.setAccessible(true);
                parentId = (String) parentField.get(o);
                type +=1;
                new MyThread().execute(expressDao);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
