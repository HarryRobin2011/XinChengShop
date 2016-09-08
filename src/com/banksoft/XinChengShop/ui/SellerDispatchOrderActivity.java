package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.SellerDispatchDao;
import com.banksoft.XinChengShop.entity.ExpressCompanyCell;
import com.banksoft.XinChengShop.entity.OrderVO;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

/**
 * Created by Robin on 2016/8/29.
 * 订单发货
 */
public class SellerDispatchOrderActivity extends XCBaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private TextView title;
    private ImageView back;
    private RadioGroup radioGroup;
    private RadioButton disPatchLogistics, dispatchManual, dispatchMySelf, dispatchPerson;

    private LinearLayout dispatchManualLayout, dispatchSelectLayout, dispatchPersonPriceLayout;

    private EditText dispatchExpressNo, dispatchPrice;
    private TextView dispatchExpressName;
    private Button send;

    private OperationType currentType;

    private ExpressCompanyCell expressCompanyCell;

    private SellerDispatchDao sellerDispatchDao;
    private ProgressDialog mProgressDialog;
    private OrderVO currentOrderVO;

    @Override
    protected void initContentView() {
        setContentView(R.layout.seller_dispatch_order_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        radioGroup = (RadioGroup) findViewById(R.id.express_group);
        dispatchManualLayout = (LinearLayout) findViewById(R.id.dispatch_manual_layout);
        dispatchSelectLayout = (LinearLayout) findViewById(R.id.dispatch_select_layout);
        dispatchPersonPriceLayout = (LinearLayout) findViewById(R.id.dispatch_person_layout);
        dispatchExpressName = (TextView) findViewById(R.id.express_name);
        dispatchExpressNo = (EditText) findViewById(R.id.express_no);
        dispatchPrice = (EditText) findViewById(R.id.dispatch_persion_price);
        send = (Button) findViewById(R.id.send);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_FIRST_USER:
                if (data != null) {
                    expressCompanyCell = (ExpressCompanyCell) data.getSerializableExtra(IntentFlag.DATA);
                    dispatchExpressName.setText(expressCompanyCell.getName());
                }
                break;
        }
    }

    @Override
    protected void initData() {
        currentOrderVO = (OrderVO) getIntent().getSerializableExtra(IntentFlag.ORDER_VO);
        title.setText(R.string.send);
        back.setVisibility(View.VISIBLE);

    }

    @Override
    protected void initOperate() {
        back.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
        dispatchSelectLayout.setOnClickListener(this);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back_button:
                finish();
                break;
            case R.id.send:
                String key = null;
                String type = null;
                String no = dispatchExpressNo.getText().toString();
                String dispatchPriceStr = dispatchPrice.getText().toString();
                ;
                if (OperationType.EXPRESS.equals(currentType)) {//物流公司配送
                    type = "" + 1;
                    if (expressCompanyCell == null) {
                        alert(R.string.please_select_logistic_company);
                        return;
                    } else {
                        key = expressCompanyCell.getKey();
                    }

                    if ("".equals(no)) {
                        alert(R.string.express_no_not_empty);
                        return;
                    }
                } else if (OperationType.MANUAL.equals(currentType)) {
                    type = "" + 1;
                    key = "" + -1;
                    if ("".equals(no)) {
                        alert(R.string.express_no_not_empty);
                        return;
                    }
                } else if (OperationType.MYSELF.equals(currentType)) {//
                    key = "" + -1;
                    type = "" + 0;
                } else if (OperationType.PERSON.equals(currentType)) {
                    key = "" + -1;
                    type = "" + 3;
                    if("".equals(dispatchPriceStr)){
                        alert(R.string.commission_no_empty);
                    }
                }
                if (sellerDispatchDao == null) {
                    sellerDispatchDao = new SellerDispatchDao(mContext);
                }
                new MyTask(key, no, type,dispatchPriceStr).execute(sellerDispatchDao);
                break;
//            物流公司
//            Logistics company
            case R.id.dispatch_select_layout:
                Intent intent = new Intent(mContext, LogisticsCompanyActivity.class);
                startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.dispatch_logistics:
                currentType = OperationType.EXPRESS;
                dispatchPersonPriceLayout.setVisibility(View.GONE);
                dispatchExpressNo.setVisibility(View.VISIBLE);
                dispatchSelectLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.dispatch_manual:
                currentType = OperationType.MANUAL;
                dispatchPersonPriceLayout.setVisibility(View.GONE);
                dispatchExpressNo.setVisibility(View.VISIBLE);
                dispatchSelectLayout.setVisibility(View.GONE);
                break;
            case R.id.dispatch_my_self:
                currentType = OperationType.MYSELF;
                dispatchPersonPriceLayout.setVisibility(View.GONE);
                dispatchExpressNo.setVisibility(View.GONE);
                dispatchSelectLayout.setVisibility(View.GONE);
                break;
            case R.id.dispatch_person:
                currentType = OperationType.PERSON;
                dispatchPersonPriceLayout.setVisibility(View.VISIBLE);
                dispatchExpressNo.setVisibility(View.GONE);
                dispatchSelectLayout.setVisibility(View.GONE);
                break;
        }
    }

    private enum OperationType {
        EXPRESS,//物流公司
        MANUAL,//手动填写
        MYSELF,//自己派送
        PERSON;//派送员送货
    }

    private class MyTask extends AsyncTask<SellerDispatchDao, String, IsFlagData> {
        private String key, no, type,dispatchPrice;

        public MyTask(String key, String no, String type,String dispatchPrice) {
            this.key = key;
            this.no = no;
            this.type = type;
            this.dispatchPrice = dispatchPrice;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(mProgressDialog == null){
                mProgressDialog = ProgressDialog.show(SellerDispatchOrderActivity.this,getText(R.string.alert),getText(R.string.please_wait));
            }
        }

        @Override
        protected IsFlagData doInBackground(SellerDispatchDao... params) {
            return params[0].sendOrder(currentOrderVO.getId(),key, no,type,dispatchPrice);
        }

        @Override
        protected void onPostExecute(IsFlagData isFlagData) {
            super.onPostExecute(isFlagData);
            mProgressDialog.dismiss();
            if (isFlagData != null) {
                if (isFlagData.isSuccess()) {
                    alert(R.string.send_success);
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    alert(isFlagData.getMsg().toString());
                }
            } else {
                alert(R.string.net_error);
            }
        }
    }
}
