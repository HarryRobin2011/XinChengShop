package com.banksoft.XinChengShop.ui;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.PayConfig;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.AliPayDao;
import com.banksoft.XinChengShop.dao.PayOrderMethodDao;
import com.banksoft.XinChengShop.entity.AlipayInfo;
import com.banksoft.XinChengShop.entity.AlipayOrderInfo;
import com.banksoft.XinChengShop.model.AlipayInfoData;
import com.banksoft.XinChengShop.model.AlipayOrderInfoData;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.utils.alipay.Config;
import com.banksoft.XinChengShop.utils.alipay.PayResult;

import java.util.HashMap;

/**
 * Created by harry_robin on 16/1/22.
 */
public class PayOrderMethodActivity extends XCBaseActivity implements View.OnClickListener{
    private PayOrderMethodDao payOrderMethodDao;
    private TextView methodName,payOrderNO,orderMoney,titleText;
    private String payMethod,orderIDS;
    private AlipayOrderInfo alipayOrderInfo;
    private AlipayInfo alipayInfo;
    private Button pay;
    private ProgressBar progressBar;
    private AliPayDao aliPayDao;
    private LinearLayout content;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Config.SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(mContext, "支付成功",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(mContext, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(mContext, "支付失败",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case Config.SDK_CHECK_FLAG: {
                    Toast.makeText(mContext, "检查结果为：" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        };
    };
    @Override
    protected void initContentView() {
        setContentView(R.layout.pay_method_activity);
    }

    @Override
    protected void initView() {
        methodName = (TextView) findViewById(R.id.pay_method);
        payOrderNO = (TextView) findViewById(R.id.order_no);
        orderMoney = (TextView) findViewById(R.id.order_money);
        titleText = (TextView) findViewById(R.id.titleText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        pay = (Button) findViewById(R.id.pay);
        content = (LinearLayout) findViewById(R.id.content);
    }

    @Override
    protected void initData() {
      titleText.setText(R.string.pay_title);
      payMethod = getIntent().getStringExtra(IntentFlag.PAY_METHOD);
      orderIDS = getIntent().getStringExtra(IntentFlag.ORDER_IDS);
    }

    @Override
    protected void initOperate() {
        pay.setOnClickListener(this);
         if(payOrderMethodDao == null){
            payOrderMethodDao = new PayOrderMethodDao(mContext);
         }
        new MyTask().execute(payOrderMethodDao);
    }

    private void setPayView(){
         methodName.setText(payMethod);
         payOrderNO.setText(alipayOrderInfo.getOrderNo());
         orderMoney.setText(alipayOrderInfo.getMoney());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pay:
                if(aliPayDao == null){
                    aliPayDao = new AliPayDao(mContext);
                }
                aliPayDao.activity = this;
                aliPayDao.mHandler = mHandler;
                aliPayDao.RSA_PRIVATE = PayConfig.ali_wap_rsa;
                aliPayDao.aliPayMethod(alipayInfo,alipayOrderInfo);
                break;
        }
    }

    private class MyTask extends AsyncTask<PayOrderMethodDao,String,HashMap<String,Object>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected HashMap<String, Object> doInBackground(PayOrderMethodDao... params) {
            HashMap<String,Object> dataMap = new HashMap<>();
            AlipayInfoData infoData = params[0].getAlipayInfoData();
            AlipayOrderInfoData orderInfoData = params[0].getAlipayOrderInfoData(orderIDS);

            dataMap.put(IntentFlag.DATA,infoData);
            dataMap.put(IntentFlag.DATA_LIST,orderInfoData);
            return dataMap;
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> stringObjectHashMap) {
            super.onPostExecute(stringObjectHashMap);
            progressBar.setVisibility(View.GONE);
            content.setVisibility(View.VISIBLE);
            AlipayInfoData infoData = (AlipayInfoData) stringObjectHashMap.get(IntentFlag.DATA);
            AlipayOrderInfoData orderInfoData = (AlipayOrderInfoData) stringObjectHashMap.get(IntentFlag.DATA_LIST);

            if(infoData != null && orderInfoData != null){
                if(infoData.isSuccess() && orderInfoData.isSuccess()){
                   // 显示支付内容
                    alipayInfo = infoData.getData();
                    alipayOrderInfo = orderInfoData.getData();
                    setPayView();

                }else{
                   showWarning(R.string.netWork_warning);

                }

            }else{

               showWarning(R.string.net_is_weak);
            }
        }
    }




}
