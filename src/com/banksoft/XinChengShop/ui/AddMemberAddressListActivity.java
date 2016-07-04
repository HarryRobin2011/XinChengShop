package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.AddressListAdapter;
import com.banksoft.XinChengShop.dao.AddressMemberDao;
import com.banksoft.XinChengShop.model.MemberAddressVOData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

import java.io.Serializable;

/**
 * Created by harry_robin on 15/11/29.
 */
public class AddMemberAddressListActivity extends XCBaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView listView;
    private TextView title;
    private Button rightBtn;
    private AddressMemberDao addressMemberDao;
    private ProgressBar mProgressBar;
    private AddressListAdapter addressListAdapter;
    private boolean cacheFlag = true;
    private boolean isChange = false;

    @Override
    protected void initContentView() {
        setContentView(R.layout.list_layout);
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.list_view);
        title = (TextView) findViewById(R.id.titleText);
        rightBtn = (Button) findViewById(R.id.titleRightButton);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_FIRST_USER) {
            if (resultCode == Activity.RESULT_OK) {
                cacheFlag = false;
                isChange = true;
                new MyThread().execute(addressMemberDao);
            }
        }
    }

    @Override
    protected void initData() {
        rightBtn.setVisibility(View.VISIBLE);
        listView.setDividerHeight(1);
        rightBtn.setText(R.string.add);
        title.setText(R.string.address_list_title);
    }

    @Override
    protected void initOperate() {
        if (addressMemberDao == null) {
            addressMemberDao = new AddressMemberDao(mContext);
        }
        listView.setOnItemClickListener(this);
        new MyThread().execute(addressMemberDao);
        rightBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titleRightButton:
                Intent intent = new Intent(mContext, AddMemberAddressActivity.class);
                startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext, AddMemberAddressActivity.class);
        intent.putExtra("memberAddressVO", (Serializable) addressListAdapter.getItem(position));
        startActivityForResult(intent, Activity.RESULT_FIRST_USER);
    }

    private class MyThread extends AsyncTask<AddressMemberDao, String, MemberAddressVOData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected MemberAddressVOData doInBackground(AddressMemberDao... params) {
            return params[0].getMemberAddressVO(member.getMember().getId(), cacheFlag);
        }

        @Override
        protected void onPostExecute(MemberAddressVOData memberAddressVOData) {
            super.onPostExecute(memberAddressVOData);
            mProgressBar.setVisibility(View.GONE);
            if (memberAddressVOData != null) {
                if (memberAddressVOData.isSuccess()) {
                    if (addressListAdapter == null) {
                        addressListAdapter = new AddressListAdapter(mContext, memberAddressVOData.getData());
                    }
                    addressListAdapter.dataList = memberAddressVOData.getData();
                    listView.setAdapter(addressListAdapter);
                    addressListAdapter.notifyDataSetChanged();
                } else {
                    showWarning(R.string.netWork_warning);
                }
            } else {
                showWarning(R.string.net_error);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(isChange){// 数据有改动，通知上层页面刷新数据
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }
}
