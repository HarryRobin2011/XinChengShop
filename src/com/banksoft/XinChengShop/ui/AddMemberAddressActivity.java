package com.banksoft.XinChengShop.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.AddressMemberDao;
import com.banksoft.XinChengShop.entity.MemberAddressVO;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.widget.MyProgressDialog;

/**
 * Created by harry_robin on 15/11/28.
 */
public class AddMemberAddressActivity extends XCBaseActivity implements View.OnClickListener{
    private TextView title;
    private Button titleRightButton;
    private EditText add_address_name;
    private EditText add_address_telPhone;
    private EditText add_address_post_code;
    private EditText add_addres_detailed;   //详细地址
    private LinearLayout address_layout;//
    private Button add_address;//确定添加收货地址
    private TextView add_address_address;//行政区域显示
    private ImageView select_img;//是否默认

    private AddressMemberDao addressDao;

    private boolean isDefault = false;
    private MemberAddressVO memberAddressVO;

    private MyProgressDialog progressDialog;



    private String region;
    private final int REQUEST_CODE_REGION = 1;
    private String province;
    private String city;
    private String county;

    final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    showWarning(msg.obj.toString());
                    break;
                case 1:     //添加收货地址成功
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.add_address_layout);
    }
    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        titleRightButton = (Button) findViewById(R.id.titleRightButton);
        add_address_name = (EditText) findViewById(R.id.add_address_name);
        add_address_telPhone = (EditText) findViewById(R.id.add_address_telNum);
        add_address_post_code = (EditText) findViewById(R.id.add_address_zipCode);
        add_addres_detailed = (EditText) findViewById(R.id.add_address_detail);
        address_layout = (LinearLayout) findViewById(R.id.add_address_area);
        add_address_address = (TextView) findViewById(R.id.add_address_address);
        add_address = (Button) findViewById(R.id.save);
        select_img = (ImageView) findViewById(R.id.select_img);
    }

    protected void initData() {
        title.setText(R.string.address_manage);
        addressDao = new AddressMemberDao(mContext);
        add_address.setOnClickListener(this);
        add_address_address.setText(R.string.please_select_area);
        select_img.setOnClickListener(this);
        address_layout.setOnClickListener(this);

        memberAddressVO = (MemberAddressVO) getIntent().getSerializableExtra("memberAddressVO");
        if (memberAddressVO != null) {
            titleRightButton.setText(R.string.delete);
            titleRightButton.setVisibility(View.VISIBLE);
            titleRightButton.setOnClickListener(this);
            region = memberAddressVO.getNo();
            add_address_name.setText(memberAddressVO.getUserName());
            add_address_telPhone.setText(memberAddressVO.getTelephone());
            add_address_post_code.setText(memberAddressVO.getPostcode());
            add_address_address.setText(memberAddressVO.getProvince() + memberAddressVO.getCity() + memberAddressVO.getCounty());
            add_addres_detailed.setText(memberAddressVO.getAddress());
            isDefault = memberAddressVO.isStatus();
            if (isDefault) {
                select_img.setImageResource(R.drawable.check_select);
            } else {
                select_img.setImageResource(R.drawable.check_normal);
            }
        }
    }

    @Override
    protected void initOperate() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                if (checkEmpty()) {
                    new EditAddressAsyncTask().execute();
                }
                break;
            case R.id.add_address_area:
                Intent intent = new Intent(mContext, AreaListActivity.class);
                startActivityForResult(intent, REQUEST_CODE_REGION);
                break;
            case R.id.select_img:
                if (isDefault) {
                    select_img.setImageResource(R.drawable.check_normal);
                } else {
                    select_img.setImageResource(R.drawable.check_select);
                }
                isDefault = !isDefault;
                break;
            case R.id.titleRightButton://删除收获地址
                new DeleteAsyncTask().execute();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_REGION:
                if (resultCode == RESULT_OK) {
                    region = data.getStringExtra(IntentFlag.CODE);
                    String areaName = data.getStringExtra(IntentFlag.AREA_NAME);
                    province = areaName.split(" ")[0];
                    city = areaName.split(" ")[1];
                    county = areaName.split(" ")[2];
                    add_address_address.setText(areaName);
                }
                break;
        }
    }

    private boolean checkEmpty() {
        if (add_address_name.getText().toString().equals("")) {
            mHandler.sendMessage(mHandler.obtainMessage(0, getText(R.string.add_address_name_no_empty)));
            return false;
        } else if (add_address_post_code.getText().toString().equals("")) {
            mHandler.sendMessage(mHandler.obtainMessage(0, getText(R.string.add_address_post_code_no_empty)));
            return false;
        } else if (add_addres_detailed.getText().toString().equals("")) {
            mHandler.sendMessage(mHandler.obtainMessage(0, getText(R.string.add_address_detailed_no_empty)));
            return false;
        } else if (add_address_telPhone.getText().toString().equals("")) {
            mHandler.sendMessage(mHandler.obtainMessage(0, getText(R.string.add_address_telPhone_no_empty)));
            return false;
        }else if(region == null){
            mHandler.sendMessage(mHandler.obtainMessage(0,getText(R.string.please_select_area)));
            return false;
        }
        return true;
    }

    private MemberAddressVO getAddress() {
        MemberAddressVO address = new MemberAddressVO();
        if (memberAddressVO != null) {
            address.setId(memberAddressVO.getId());
        }
        address.setNo(region);
        address.setTelephone(add_address_telPhone.getText().toString());
        address.setStatus(isDefault);
        address.setAddress(add_addres_detailed.getText().toString());
        address.setPostcode(add_address_post_code.getText().toString());
        address.setUserName(add_address_name.getText().toString());
        address.setMemberId(member.getMember().getId());
        address.setProvince(province);
        address.setCity(city);
        address.setCounty(county);
        return address;
    }



    private class EditAddressAsyncTask extends AsyncTask<AddressMemberDao, String, BaseData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressDialog == null){
               progressDialog = new MyProgressDialog(AddMemberAddressActivity.this);
            }
            progressDialog.showDialog(R.string.please_wait);
        }

        @Override
        protected BaseData doInBackground(AddressMemberDao... params) {
            memberAddressVO = getAddress();
            BaseData commonData;
            if (memberAddressVO.getId() == null) {
                commonData = addressDao.saveMemberAddress(memberAddressVO);
            } else {
                commonData = addressDao.updateMemberAddress(memberAddressVO);
            }
            return commonData;
        }

        @Override
        protected void onPostExecute(BaseData commonData) {
            super.onPostExecute(commonData);
            progressDialog.dismiss();
            if (commonData != null && commonData.isSuccess()) {
                Intent intent = new Intent();
                intent.putExtra(IntentFlag.ADDRESS,memberAddressVO);
                setResult(RESULT_OK,intent);
                finish();
            } else {
               showWarning(commonData.getMsg().toString());
            }
        }
    }

    private class DeleteAsyncTask extends AsyncTask<String, Void, BaseData> {
        @Override
        protected BaseData doInBackground(String... params) {
            if (memberAddressVO == null) {
                return null;
            }
            BaseData data = addressDao.deleteMemberAddress(memberAddressVO.getId());
            return data;
        }

        @Override
        protected void onPostExecute(BaseData commonData) {
            super.onPostExecute(commonData);
            if (commonData != null && commonData.isSuccess()) {
                showWarning(R.string.delete_success);
                setResult(RESULT_OK);
                finish();
            } else {
                showWarning(R.string.delete_fail);
            }
        }
    }
}
