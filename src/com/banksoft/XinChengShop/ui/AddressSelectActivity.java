package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.AddressListAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.AddressMemberDao;
import com.banksoft.XinChengShop.model.MemberAddressVOData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

import java.io.Serializable;

/**
 * Created by harry_robin on 15/12/10.
 */
public class AddressSelectActivity extends XCBaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    private ListView listView;
    private TextView title;
    private AddressMemberDao addressMemberDao;
    private ProgressBar mProgressBar;
    private AddressListAdapter addressListAdapter;
    private boolean cacheFlag = true;
    private Button addressManager;

    @Override
    protected void initContentView() {
        setContentView(R.layout.address_select_list_layout);
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.list_view);
        title = (TextView) findViewById(R.id.title);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        addressManager = (Button) findViewById(R.id.address_manager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Activity.RESULT_FIRST_USER){
            if(resultCode == Activity.RESULT_OK){
                cacheFlag =false;
                new MyThread().execute(addressMemberDao);
            }
        }
    }

    @Override
    protected void initData() {
        listView.setDividerHeight(1);
        title.setText(R.string.address_select);
        addressManager.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initOperate() {
        if (addressMemberDao == null) {
            addressMemberDao = new AddressMemberDao(mContext);
        }
        listView.setOnItemClickListener(this);
        addressManager.setOnClickListener(this);
        new MyThread().execute(addressMemberDao);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_manager:
                Intent intent = new Intent(mContext,AddMemberAddressListActivity.class);
                startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra(IntentFlag.ADDRESS, (Serializable) addressListAdapter.getItem(position));
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private class MyThread extends AsyncTask<AddressMemberDao, String, MemberAddressVOData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected MemberAddressVOData doInBackground(AddressMemberDao... params) {
            return params[0].getMemberAddressVO(member.getMember().getId(),cacheFlag);
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
}
