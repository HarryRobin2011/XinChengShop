package com.banksoft.XinChengShop.ui;

import android.os.AsyncTask;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.ReturnGoodsDao;
import com.banksoft.XinChengShop.entity.OrderVO;
import com.banksoft.XinChengShop.entity.ReturnMoney;
import com.banksoft.XinChengShop.entity.ReturnProduct;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.type.OrderStatus;
import com.banksoft.XinChengShop.type.ReturnType;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.widget.alertview.AlertView;

import java.math.BigDecimal;

/**
 * Created by Robin on 2016/7/22.
 * 退款退货
 */
public class ReturnGoodsActivity extends XCBaseActivity implements View.OnClickListener{
    private TextView title;
    private TextView back;
    private RadioGroup returnTypeGroup;
    private EditText returnReasonEdit;
    private EditText returnMoneyEdit;
   // private EditText returnDescribe;
    private LinearLayout submitPhoto;
    private LinearLayout imageLayout;
    private Button submitApply;
    private TextView submitPhotoMax;
    private TextView orderMoney;
    private OrderVO orderVO;
    private AlertView mAlertView;
    private ReturnType returnType = ReturnType.RETURN_MONEY;// 默认退款
    private RadioButton returnGoods; // 退货按钮

    private ReturnGoodsDao returnGoodsDao;
    @Override
    protected void initContentView() {
        setContentView(R.layout.return_goods_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.title);
        back = (TextView) findViewById(R.id.title_back_button);
        returnTypeGroup = (RadioGroup) findViewById(R.id.return_group_type);
        returnReasonEdit = (EditText) findViewById(R.id.return_reason);
        returnMoneyEdit = (EditText) findViewById(R.id.return_money);
        //returnDescribe = (EditText) findViewById(R.id.return_describe);
        submitPhoto = (LinearLayout) findViewById(R.id.submit_photo_max_three);
        submitApply = (Button) findViewById(R.id.submit_apply);
        imageLayout = (LinearLayout) findViewById(R.id.image_layout);
        submitPhotoMax = (TextView) findViewById(R.id.submit_photo_max_alert);
        orderMoney = (TextView) findViewById(R.id.order_money);
        returnGoods = (RadioButton) findViewById(R.id.return_the_goods);
    }

    @Override
    protected void initData() {
        orderVO = (OrderVO) getIntent().getSerializableExtra(IntentFlag.ORDER_VO);
       title.setText(R.string.return_goods_title);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        orderMoney.setText("最多"+orderVO.getTotalMoney());

        if(OrderStatus.PAY.name().equals(orderVO.getStatus())){// 已付款未发货
            returnGoods.setVisibility(View.GONE);
        }else if(OrderStatus.DISPATCH.name().equals(orderVO.getStatus())){// 已发货，未收货
            returnGoods.setVisibility(View.VISIBLE);
        }else if(OrderStatus.SUCCESS.name().equals(orderVO.getStatus())){//已经收货
            returnGoods.setVisibility(View.VISIBLE);
        }

        if(returnType.equals(ReturnType.RETURN_MONEY)){
            returnReasonEdit.setHint(R.string.please_select_return_money_reason);
        }else{
            returnReasonEdit.setHint(R.string.please_select_return_good_reason);
        }
    }

    @Override
    protected void initOperate() {
        returnTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
              if(checkedId == R.id.return_money){//退款
                  returnType = ReturnType.RETURN_MONEY;// 退款
              }else{
                  returnType = ReturnType.RETURN_GOODS;// 退货
              }
            }
        });

        submitPhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back_button:
                 finish();
                break;
            case R.id.submit_photo_max_three:
                if(OrderStatus.PAY.name().equals(orderVO.getStatus())){// 已付款未发货


                }else if(OrderStatus.DISPATCH.name().equals(orderVO.getStatus())){// 已发货，未收货


                }else if(OrderStatus.SUCCESS.name().equals(orderVO.getStatus())){//已经收货


                }
                break;
        }
    }

    private ReturnMoney getReturnMoney(){
        String returnReasonStr = returnReasonEdit.getText().toString();
        String returnMoneyStr = returnMoneyEdit.getText().toString();


        BigDecimal returnMoneyBigDecimal = new BigDecimal(returnMoneyStr);
        BigDecimal orderMoneyBigDecimal = new BigDecimal(orderVO.getTotalMoney());
        int result = returnMoneyBigDecimal.compareTo(orderMoneyBigDecimal);
        if(returnMoneyStr.isEmpty()){
            alert(R.string.return_money_no_empty);
            return null;
        }else if(returnReasonStr.isEmpty()){
            alert(R.string.return_reason_no_empty);
            return null;
        }else if(result == 1){
            alert("最多申请退款"+orderVO.getTotalMoney());
            return null;
        }
        ReturnMoney returnMoney = new ReturnMoney();
        returnMoney.setMemberReason(returnReasonStr);
        returnMoney.setOrderNo(orderVO.getNo());
        returnMoney.setMemberMoney(Float.valueOf(returnMoneyStr));
        returnMoney.setMemberAccount(member.getMember().getAccount());
        returnMoney.setBackTime(System.currentTimeMillis());
        returnMoney.setMemberId(member.getMember().getId());
        returnMoney.setOrderTotal(orderVO.getTotalMoney());
        return returnMoney;
    }

    private ReturnProduct getReturnProduct(){
        String returnReasonStr = returnReasonEdit.getText().toString();
        String returnMoney = returnMoneyEdit.getText().toString();
     //   String returnDescribeStr = returnDescribe.getText().toString();
        BigDecimal returnMoneyBigDecimal = new BigDecimal(returnMoney);
        BigDecimal orderMoneyBigDecimal = new BigDecimal(orderVO.getTotalMoney());
        int result = returnMoneyBigDecimal.compareTo(orderMoneyBigDecimal);
        if(returnMoney.isEmpty()){
           alert(R.string.return_money_no_empty);
           return null;
        }else if(returnReasonStr.isEmpty()){
            alert(R.string.return_reason_no_empty);
            return null;
        }else if(result == 1){
            alert("最多申请退款"+orderVO.getTotalMoney());
            return null;
        }
        ReturnProduct returnProduct = new ReturnProduct();
        returnProduct.setMemberId(member.getMember().getId());
        returnProduct.setOrderTotal(orderVO.getTotalMoney());
        returnProduct.setMemberAccount(member.getMember().getAccount());
        returnProduct.setMemberMoney(Float.valueOf(returnMoney));
        returnProduct.setOrderNo(orderVO.getNo());
        returnProduct.setMemberReason(returnReasonStr);
        if(ReturnType.RETURN_MONEY.equals(returnType)){
           returnProduct.setReturnProduct(false);
        }else{
           returnProduct.setReturnProduct(true);
        }
        return returnProduct;

    }

    /**
     * 退款
     */
    private class ReturnMoneyTask extends AsyncTask<ReturnGoodsDao,String,IsFlagData>{

        @Override
        protected IsFlagData doInBackground(ReturnGoodsDao... params) {
            return null;
        }
    }

    private class ReturnGoodsTask extends AsyncTask<ReturnGoodsDao,String,IsFlagData>{

        @Override
        protected IsFlagData doInBackground(ReturnGoodsDao... params) {
            return params[0].;
        }
    }
}
