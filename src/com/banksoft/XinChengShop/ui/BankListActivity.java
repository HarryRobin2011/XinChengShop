package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.MyArrayAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

/**
 * Created by Robin on 2016/11/16.
 * <option value="中国银行" ${data.bankName eq "中国银行"?"selected":""}>中国银行</option>
 * <option value="中国农业银行" ${data.bankName eq "中国农业银行"?"selected":""}>中国农业银行</option>
 * <option value="中国工商银行" ${data.bankName eq "中国工商银行"?"selected":""}>中国工商银行</option>
 * <option value="中国建设银行" ${data.bankName eq "中国建设银行"?"selected":""}>中国建设银行</option>
 * <option value="潍坊银行" ${data.bankName eq "潍坊银行"?"selected":""}>潍坊银行</option>
 * <option value="潍坊农商银行" ${data.bankName eq "潍坊农商银行"?"selected":""}>潍坊农商银行</option>
 * <option value="农村信用社" ${data.bankName eq "农村信用社"?"selected":""}>农村信用社</option>
 * <option value="中国交通银行" ${data.bankName eq "中国交通银行"?"selected":""}>中国交通银行</option>
 */
public class BankListActivity extends XCBaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    private ListView listView;
    private TextView title;
    private ImageView back;
    private String[] bankList = new String[]{"中国银行", "中国农业银行", "中国工商银行", "中国建设银行", "潍坊银行", "潍坊农商银行", "农村信用社", "中国交通银行"};

    @Override
    protected void initContentView() {
        setContentView(R.layout.bank_list_layout);
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.list_view);
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
    }

    @Override
    protected void initData() {
        title.setText(R.string.select_bank_title);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
    }

    @Override
    protected void initOperate() {
        MyArrayAdapter myArrayAdapter = new MyArrayAdapter(mContext,android.R.layout.simple_list_item_1,bankList);
        listView.setAdapter(myArrayAdapter);
        listView.setOnItemClickListener(this);
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
        String item = bankList[position];
        Intent intent = new Intent();
        intent.putExtra(IntentFlag.DATA,item);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
}
