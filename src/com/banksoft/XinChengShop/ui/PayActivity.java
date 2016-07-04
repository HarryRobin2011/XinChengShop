package com.banksoft.XinChengShop.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.config.PayConfig;
import com.banksoft.XinChengShop.dao.PayDao;
import com.banksoft.XinChengShop.entity.OrderVO;
import com.banksoft.XinChengShop.model.MemberVOData;
import com.banksoft.XinChengShop.model.PayReqData;
import com.banksoft.XinChengShop.model.WeiXinResponse;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.CheckPayPasswordFragment;
import com.banksoft.XinChengShop.utils.StringNumber;
import com.banksoft.XinChengShop.widget.MyProgressDialog;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.modelpay.PayResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.ArrayList;

/**
 * Created by harry_robin on 15/12/3.
 */
public class PayActivity extends XCBaseActivity implements View.OnClickListener, CheckPayPasswordFragment.CheckListener {
    private TextView title;
    private TextView total;
    private TextView balance;
    private Button pay;
    private ArrayList<OrderVO> orderVOLinkedList;
    private ProgressBar progressBar;
    private ImageView back;
    private Button orderCenter;
    private LinearLayout alipayMethodLayout;
    private LinearLayout unPayLayout;
    private String orderIDS;
    private IWXAPI api;
    private PayDao payDao;
    private MyProgressDialog progressDialog;

    @Override
    protected void initContentView() {
        setContentView(R.layout.pay_layout);
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        total = (TextView) findViewById(R.id.total);
        balance = (TextView) findViewById(R.id.balance);
        pay = (Button) findViewById(R.id.order_pay);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        back = (ImageView) findViewById(R.id.title_back_button);
        orderCenter = (Button) findViewById(R.id.titleRightButton);
        alipayMethodLayout = (LinearLayout) findViewById(R.id.pay_method_alipay_layout);
        unPayLayout = (LinearLayout) findViewById(R.id.pay_method_unpay_layout);
    }

    @Override
    protected void initData() {
        orderVOLinkedList = (ArrayList<OrderVO>) getIntent().getSerializableExtra(IntentFlag.ORDER_VO_LIST);
        title.setText(R.string.pay_xincheng_title);
        total.setText(getTotal() + "元");
        back.setVisibility(View.VISIBLE);
    }

    private String getTotal() {
        String total = null;
        StringBuffer orderIDBuffer = new StringBuffer();
        for (OrderVO orderVO : orderVOLinkedList) {
            total = StringNumber.add(total, "" + orderVO.getTotalMoney());
            orderIDBuffer.append(orderVO.getId()).append("|");
        }
        orderIDS = orderIDBuffer.toString();
        return total;
    }

    @Override
    protected void initOperate() {
        back.setOnClickListener(this);
        pay.setOnClickListener(this);
        alipayMethodLayout.setOnClickListener(this);
        unPayLayout.setOnClickListener(this);
        if (payDao == null) {
            payDao = new PayDao(mContext);
        }
        new MyThread().execute(payDao);
    }

    /**
     * 回调是否验证密码通过
     *
     * @param success
     */
    @Override
    public void isSuccess(boolean success) {

    }



    /**
     * 请求会员详情
     */
    private class MyThread extends AsyncTask<PayDao, String, MemberVOData> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected MemberVOData doInBackground(PayDao... params) {
            return params[0].getMemberData(member.getMember().getId());
        }

        @Override
        protected void onPostExecute(MemberVOData memberData) {
            super.onPostExecute(memberData);
            progressBar.setVisibility(View.GONE);
            if (memberData != null) {
                if (memberData.isSuccess()) {
                    balance.setText("您的当前余额：" + memberData.getData().getBalance() + "元");
                } else {
                    showWarning(R.string.netWork_warning);
                }
            } else {
                showWarning(R.string.net_error);
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_pay:
                CheckPayPasswordFragment checkPayPasswordFragment = new CheckPayPasswordFragment();
                Bundle bundle = new Bundle();
                bundle.putString(IntentFlag.MEMBER_ID, member.getMember().getId());
                bundle.putString(IntentFlag.ORDER_IDS, getOrderIds(orderVOLinkedList));
                checkPayPasswordFragment.setArguments(bundle);
                checkPayPasswordFragment.show(getSupportFragmentManager(), "");
                break;

            case R.id.title_back_button:
                finish();
                break;
            case R.id.titleRightButton:
                Intent intent = new Intent(mContext, OrderListActivity.class);
                startActivity(intent);
                break;
            case R.id.pay_method_wx_layout:

                break;
            case R.id.pay_method_alipay_layout:
                Intent aliPayIntent = new Intent(mContext, PayOrderMethodActivity.class);
                aliPayIntent.putExtra(IntentFlag.ORDER_IDS, getOrderIds(orderVOLinkedList));
                aliPayIntent.putExtra(IntentFlag.PAY_METHOD, "支付宝支付");
                startActivity(aliPayIntent);
                break;
            case R.id.pay_method_unpay_layout:// 微信支付
                api = WXAPIFactory.createWXAPI(this, PayConfig.wei_xin_app_id);
                api.registerApp(PayConfig.wei_xin_app_id);

                boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
                if (isPaySupported) {
                    api.registerApp(PayConfig.wei_xin_app_id);
                    if (payDao == null) {
                        payDao = new PayDao(mContext);
                    }
                    new WeiXinPayThread(orderIDS).execute(payDao);
                }
                break;
        }
    }


    private String getOrderIds(ArrayList<OrderVO> orderVOLinkedList) {
        StringBuffer buffer = new StringBuffer();
        for (OrderVO orderVO : orderVOLinkedList) {
            buffer.append(orderVO.getId()).append("|");
        }
        return buffer.toString();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle(R.string.alert).setMessage(R.string.exit_order_pay).setNegativeButton(getText(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(mContext, OrderListActivity.class);
                startActivity(intent);
                finish();
            }
        }).setPositiveButton(getText(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    private class WeiXinPayThread extends AsyncTask<PayDao, String, PayReqData> {
        private String orderIds;

        public WeiXinPayThread( String orderIds) {
            this.orderIds = orderIds;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new MyProgressDialog(PayActivity.this);
            progressDialog.showDialog(R.string.please_wait);
        }

        @Override
        protected PayReqData doInBackground(PayDao... params) {
            return params[0].getWeiXinResponse(orderIds);
        }

        /**
         * 					req.appId			= json.getString("appid");
         req.partnerId		= json.getString("partnerid");
         req.prepayId		= json.getString("prepayid");
         req.nonceStr		= json.getString("noncestr");
         req.timeStamp		= json.getString("timestamp");
         req.packageValue	= json.getString("package");
         req.sign			= json.getString("sign");
         req.extData			= "app data"; // optional
         * @param payReq
         */
        @Override
        protected void onPostExecute(PayReqData payReq) {
            super.onPostExecute(payReq);
            progressDialog.dismiss();
            if (payReq != null) {
               api.sendReq(payReq.getData());
            } else {
               alert(R.string.net_error);
            }
        }
    }





}
