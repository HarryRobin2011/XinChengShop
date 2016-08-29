package com.banksoft.XinChengShop.ui;

import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;

/**
 * Created by Robin on 2016/8/29.
 */
public class SellerDispatchOrderActivity extends XCBaseActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener{
    private TextView title;
    private ImageView back;
    private RadioGroup radioGroup;
    private RadioButton disPatchLogistics,dispatchManual,dispatchMySelf,dispatchPerson;

     private LinearLayout dispatchManualLayout,dispatchSelectLayout,dispatchPersonPriceLayout;

    private EditText dispatchExpressName,dispatchExpressNo,dispatchPrice;
    private Button send;

    private OperationType currentType;
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
        dispatchExpressName = (EditText) findViewById(R.id.express_name);
        dispatchExpressNo = (EditText) findViewById(R.id.express_no);
        dispatchPrice = (EditText) findViewById(R.id.dispatch_persion_price);
        send  = (Button) findViewById(R.id.send);
    }

    @Override
    protected void initData() {
        title.setText(R.string.send);
        back.setVisibility(View.VISIBLE);

    }

    @Override
    protected void initOperate() {
        back.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_button:
                finish();
                break;
            case R.id.send:

                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.dispatch_logistics:
                currentType = OperationType.EXPRESS;
                dispatchPersonPriceLayout.setVisibility(View.GONE);
                dispatchExpressName.setVisibility(View.GONE);
                dispatchSelectLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.dispatch_manual:
                currentType = OperationType.MANUAL;
                dispatchPersonPriceLayout.setVisibility(View.GONE);
                dispatchExpressName.setVisibility(View.VISIBLE);
                dispatchSelectLayout.setVisibility(View.GONE);
                break;
            case R.id.dispatch_my_self:
                currentType = OperationType.MYSELF;
                dispatchPersonPriceLayout.setVisibility(View.GONE);
                dispatchExpressName.setVisibility(View.GONE);
                dispatchSelectLayout.setVisibility(View.GONE);
                break;
            case R.id.dispatch_person:
                currentType = OperationType.PERSON;
                dispatchPersonPriceLayout.setVisibility(View.VISIBLE);
                dispatchExpressName.setVisibility(View.GONE);
                dispatchSelectLayout.setVisibility(View.GONE);
                break;
        }
    }

    private enum OperationType{
        EXPRESS,//物流公司
        MANUAL,//手动填写
        MYSELF,//自己派送
        PERSON;//派送员送货
    }
}
