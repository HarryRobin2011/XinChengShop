package com.banksoft.XinChengShop.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.OrderInfoDao;
import com.banksoft.XinChengShop.entity.OrderProductVO;
import com.banksoft.XinChengShop.entity.OrderVO;
import com.banksoft.XinChengShop.model.OrderInfoData;
import com.banksoft.XinChengShop.type.OrderStatus;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.utils.CommonUtil;
import com.banksoft.XinChengShop.utils.TimeUtils;
import com.banksoft.XinChengShop.widget.MyProgressDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.math.BigDecimal;

/**
 * Created by Robin on 2016/9/7.
 */
public class SellerOrderInfoActivity extends XCBaseActivity implements View.OnClickListener {
    private TextView title;
    private ImageView back;
    private TextView orderNo;//订单编号

    private LinearLayout contentLayout;
    private LinearLayout toolLayout;

    private LinearLayout addressLayout, addressDetailLayout;
    private TextView shippingTelPhone, shipName, shipAddress;

    private TextView shopName;
    private LinearLayout productContentLayout;
    private Button orderUpdata;
    private TextView payMethod;
    private TextView productTotalMoney;
    private TextView expressMoney;
    private TextView disbursements;
    private TextView createOrderTime;
    private OrderInfoDao orderInfoDao;

    private MyProgressDialog progressDialog;
    private String orderId;
    private OrderStatus orderStatus;

    private ImageLoader mImageLoader;
    private OrderVO orderVO;

    @Override
    protected void initContentView() {
        setContentView(R.layout.seller_order_info_layout);

    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        orderNo = (TextView) findViewById(R.id.order_no);
        contentLayout = (LinearLayout) findViewById(R.id.content_layout);
        toolLayout = (LinearLayout) findViewById(R.id.layout_tool);
        addressLayout = (LinearLayout) findViewById(R.id.address_layout);
        addressDetailLayout = (LinearLayout) findViewById(R.id.address_detail_layout);
        shippingTelPhone = (TextView) findViewById(R.id.telPhone);
        shipName = (TextView) findViewById(R.id.shipping_name);
        shipAddress = (TextView) findViewById(R.id.address);
        shopName = (TextView) findViewById(R.id.shop_name);
        productContentLayout = (LinearLayout) findViewById(R.id.content);
        orderUpdata = (Button) findViewById(R.id.order_updata);

        payMethod = (TextView) findViewById(R.id.pay_method);
        productTotalMoney = (TextView) findViewById(R.id.product_total_money);
        expressMoney = (TextView) findViewById(R.id.express_money);
        disbursements = (TextView) findViewById(R.id.disbursements);
        createOrderTime = (TextView) findViewById(R.id.order_create_time);
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));

    }

    @Override
    protected void initData() {
        orderId = getIntent().getStringExtra(IntentFlag.ORDER_ID);
        orderStatus = (OrderStatus) getIntent().getSerializableExtra(IntentFlag.ORDER_STATUS);
        title.setText(orderStatus.getName());
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        if(!orderStatus.equals(OrderStatus.CREATE)){
            orderUpdata.setVisibility(View.GONE);
        }else{
            orderUpdata.setVisibility(View.VISIBLE);
            orderUpdata.setText(R.string.updata_order_price);
        }
        addressLayout.setVisibility(View.VISIBLE);
        if (orderInfoDao == null) {
            orderInfoDao = new OrderInfoDao(mContext);
        }
        new MyTask().execute(orderInfoDao);
    }

    @Override
    protected void initOperate() {
        orderUpdata.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back_button:
                finish();
                break;
            case R.id.order_updata:
                Intent intent = new Intent(mContext, UpdateOrderPriceActivity.class);
                intent.putExtra(IntentFlag.ORDER_VO,orderVO);
                startActivityForResult(intent, Activity.RESULT_FIRST_USER);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_FIRST_USER) {
            if (data != null) {
                float orderTotal = data.getFloatExtra(IntentFlag.ORDER_MONEY, 0F);
                float expressMoney = data.getFloatExtra(IntentFlag.EXPRESS_MONEY, 0F);
                orderVO.setTotalMoney(orderTotal);
                orderVO.setExpressMoney(expressMoney);
                showOrderInfoView(orderVO);
            }
        }
    }

    private class MyTask extends AsyncTask<OrderInfoDao, String, OrderInfoData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = new MyProgressDialog(SellerOrderInfoActivity.this);
            }
            progressDialog.showDialog(R.string.please_wait);
        }

        @Override
        protected void onPostExecute(OrderInfoData orderInfoData) {
            super.onPostExecute(orderInfoData);
            progressDialog.dismiss();
            if (orderInfoData != null) {
                if (orderInfoData.isSuccess()) {
                    orderVO = orderInfoData.getData();
                    showOrderInfoView(orderInfoData.getData());
                } else {
                    alert(orderInfoData.getMsg().toString());
                }
            } else {
                alert(R.string.net_error);
            }
        }

        @Override
        protected OrderInfoData doInBackground(OrderInfoDao... params) {
            return params[0].getOrderInfoData(orderId);
        }
    }

    private void showOrderInfoView(OrderVO orderVO) {
        BigDecimal bigDecimal = new BigDecimal(orderVO.getTotalMoney());
        bigDecimal.add(new BigDecimal(orderVO.getExpressMoney()));

        contentLayout.setVisibility(View.VISIBLE);
        orderNo.setText("订单编号：" + orderVO.getNo());
        shippingTelPhone.setText(orderVO.getTelephone());
        shipName.setText(orderVO.getUserName());
        shipAddress.setText(orderVO.getAddress());
        shopName.setText(orderVO.getShopName());
        payMethod.setText(orderVO.getPayType());
        productTotalMoney.setText("￥" + String.valueOf(orderVO.getTotalMoney()));
        disbursements.setText("￥" + bigDecimal.floatValue());
        createOrderTime.setText("下单时间：" + TimeUtils.getTimeStr(orderVO.getCreateTime(), TimeUtils.TimeType.MINUTE));
        expressMoney.setText(String.valueOf(orderVO.getExpressMoney()));
        addressDetailLayout.setVisibility(View.VISIBLE);

        for (OrderProductVO productVO : orderVO.getList()) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.product_list_item_layout, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            TextView name = (TextView) view.findViewById(R.id.name);
            //TextView num = (TextView) view.findViewById(R.id.describe);
            TextView discountPrice = (TextView) view.findViewById(R.id.discount_price);
            TextView realPrice = (TextView) view.findViewById(R.id.real_price);
            TextView saleNum = (TextView) view.findViewById(R.id.sale_num);
            String imageUrl = "";
            if (!"".equals(productVO.getImageFile()) && productVO.getImageFile() != null) {
                imageUrl = ControlUrl.BASE_URL + productVO.getImageFile().split("\\|")[0];
            }

            mImageLoader.displayImage(imageUrl, imageView, XCApplication.options);

            name.setText(productVO.getProductName());
            // num.setText(productVO.getNum()+"");
            discountPrice.setText(productVO.getPrice() + "元");
            realPrice.setVisibility(View.GONE);
            saleNum.setVisibility(View.GONE);
            productContentLayout.addView(view);
        }

    }
}
