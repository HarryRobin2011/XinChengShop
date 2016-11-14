package com.banksoft.XinChengShop.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.ExpressAdapter;
import com.banksoft.XinChengShop.adapter.SubmitSaleOrderAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.SubmitOrderDao;
import com.banksoft.XinChengShop.entity.*;
import com.banksoft.XinChengShop.model.ExpressModelData;
import com.banksoft.XinChengShop.model.MemberAddressVOData;
import com.banksoft.XinChengShop.model.OrderVOListData;
import com.banksoft.XinChengShop.model.ShopCartProductData;
import com.banksoft.XinChengShop.type.OrderType;
import com.banksoft.XinChengShop.type.ShopType;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.utils.PopupWindowUtil;
import com.banksoft.XinChengShop.utils.StringNumber;
import com.banksoft.XinChengShop.widget.ClearEditText;
import com.banksoft.XinChengShop.widget.MyProgressDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by harry_robin on 15/11/17.
 */
public class SubmitSaleOrderActivity extends XCBaseActivity implements View.OnClickListener {
    /**
     * ID
     */
    private HashMap<String, ShopCartProductData> shopCartProductDataHashMap;
    private ListView mListView;
    private TextView price, title;
    private Button confrimBtn;
    private SubmitSaleOrderAdapter submitOrderAdapter;
    private ProgressBar mProgressBar;
    private List<ShopCartProductData> shopCartProductDataList;

    private SubmitOrderDao submitOrderDao;
    private MemberAddressVO defaultMemberAddressVo;

    private LinearLayout detailAddressLayout;
    private TextView addAddress, shippingName, shippingTel, shippingAddress;


    private View headView;

    private String shopType;

    private MyProgressDialog myProgressDialog;

    private boolean isFreeShipping = false;


    private final int SELECT_ADDRESS_ARNO = 0;// 选择收货地址
    private final int ADDRESS_MANAGER = 1;//收货地址管理


    @Override
    protected void initContentView() {
        setContentView(R.layout.submit_order_layout);
    }

    @Override
    protected void initView() {

        mListView = (ListView) findViewById(R.id.list_view);
        price = (TextView) findViewById(R.id.price);
        title = (TextView) findViewById(R.id.titleText);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        confrimBtn = (Button) findViewById(R.id.confirm);

        headView = LayoutInflater.from(mContext).inflate(R.layout.shipping_address_layout, null);
        detailAddressLayout = (LinearLayout) headView.findViewById(R.id.address_detail_layout);
        addAddress = (TextView) headView.findViewById(R.id.add_address);


        shippingName = (TextView) headView.findViewById(R.id.shipping_name);
        shippingAddress = (TextView) headView.findViewById(R.id.address);
        shippingTel = (TextView) headView.findViewById(R.id.telPhone);
    }

    @Override
    protected void initData() {
        shopType = getIntent().getStringExtra(IntentFlag.SHOP_TYPE);
        shopCartProductDataHashMap = (HashMap<String, ShopCartProductData>) getIntent().getSerializableExtra(IntentFlag.SHOP_CART_PRODUCT_DATA);
        title.setText(R.string.confirm_order_title);
    }

    @Override
    protected void initOperate() {
        if (ShopType.SHOP_SALE.name().equals(shopType)) {// 普通商品
            mListView.addHeaderView(headView);
            mListView.setAdapter(null);
        }


        if (shopCartProductDataList == null) {
            shopCartProductDataList = new LinkedList<>();
        }
        shopCartProductDataList.clear();
        for (String shopId : shopCartProductDataHashMap.keySet()) {
            shopCartProductDataList.add(shopCartProductDataHashMap.get(shopId));
        }
        if (submitOrderAdapter == null) {
            submitOrderAdapter = new SubmitSaleOrderAdapter(this, shopCartProductDataList);
        }
        mListView.setAdapter(submitOrderAdapter);
        if (submitOrderDao == null) {
            submitOrderDao = new SubmitOrderDao(mContext);
        }
        detailAddressLayout.setOnClickListener(this);
        confrimBtn.setOnClickListener(this);
        setTotal();
        new MyThread().execute(submitOrderDao);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_ADDRESS_ARNO) {//选择收货地址
            MemberAddressVO addressVO = (MemberAddressVO) data.getSerializableExtra(IntentFlag.ADDRESS);

            //重置已选收货地址
            resetExpressModel();

            initDefaultMemberAddressVo(addressVO);
        } else if (requestCode == ADDRESS_MANAGER) {// 收货地址管理
            MemberAddressVO addressVO = (MemberAddressVO) data.getSerializableExtra(IntentFlag.ADDRESS);
            initDefaultMemberAddressVo(addressVO);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_address:
                Intent intent = new Intent(mContext, AddMemberAddressActivity.class);
                startActivityForResult(intent, SELECT_ADDRESS_ARNO);
                break;
            case R.id.address_detail_layout:
                Intent detailIntent = new Intent(mContext, AddressSelectActivity.class);
                startActivityForResult(detailIntent, SELECT_ADDRESS_ARNO);
                break;
            case R.id.confirm:// 确认提交订单
                if (submitOrderDao == null) {
                    submitOrderDao = new SubmitOrderDao(mContext);
                }
                new SubmitThread().execute(submitOrderDao);
                break;
        }
    }


    //请求默认地址
    private class MyThread extends AsyncTask<SubmitOrderDao, String, MemberAddressVOData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected MemberAddressVOData doInBackground(SubmitOrderDao... params) {
            return params[0].getMemberAddressData(member.getMember().getId(), false);
        }

        @Override
        protected void onPostExecute(MemberAddressVOData memberAddressVOData) {
            super.onPostExecute(memberAddressVOData);
            mProgressBar.setVisibility(View.GONE);
            if (memberAddressVOData != null) {
                if (memberAddressVOData.isSuccess()) {
                    setDefaultAddress(memberAddressVOData);
                } else {
                    showWarning(R.string.netWork_warning);
                }
            } else {
                showWarning(R.string.net_error);
            }

        }
    }

    /**
     * 提交订单
     */
    private class SubmitThread extends AsyncTask<SubmitOrderDao, String, OrderVOListData> {
        SubmitOrder submitOrder;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            submitOrder = getSubmitOrder();
            if (submitOrder == null) {
                this.cancel(true);
                return;
            }
            if (myProgressDialog == null) {
                myProgressDialog = new MyProgressDialog(SubmitSaleOrderActivity.this);
            }
            myProgressDialog.showDialog(R.string.please_wait);
        }

        @Override
        protected OrderVOListData doInBackground(SubmitOrderDao... params) {
            return params[0].submitOrderVOListData(submitOrder);
        }

        @Override
        protected void onPostExecute(OrderVOListData orderVOListData) {
            super.onPostExecute(orderVOListData);
            myProgressDialog.dismiss();
            if (orderVOListData != null) {
                if (orderVOListData.isSuccess()) {
                    Intent intent = new Intent(mContext, PayActivity.class);
                    intent.putExtra(IntentFlag.ORDER_VO_LIST, orderVOListData.getData().getOrderList());
                    startActivity(intent);
                    finish();
                } else {
                    showWarning(R.string.netWork_warning);
                }
            } else {
                showWarning(R.string.net_error);
            }
        }
    }


    /**
     * 设置默认收货地址
     *
     * @param memberAddressVOData
     */
    private void setDefaultAddress(MemberAddressVOData memberAddressVOData) {
        if (memberAddressVOData.getData() == null || memberAddressVOData.getData().size() == 0) {
            addAddress.setVisibility(View.VISIBLE);
            detailAddressLayout.setVisibility(View.GONE);
            addAddress.setText(R.string.add_address_title);
            addAddress.setOnClickListener(this);
        } else {
            addAddress.setVisibility(View.GONE);
            detailAddressLayout.setVisibility(View.VISIBLE);
            for (MemberAddressVO memberAddressVO : memberAddressVOData.getData()) {
                if (memberAddressVO.isStatus()) {
                    defaultMemberAddressVo = memberAddressVO;
                }
            }
            if (defaultMemberAddressVo != null) {
                initDefaultMemberAddressVo(defaultMemberAddressVo = memberAddressVOData.getData().get(0));
            }
        }
    }

    /**
     * 显示默认收货地址
     */

    private void initDefaultMemberAddressVo(MemberAddressVO memberAddressVO) {
        addAddress.setVisibility(View.GONE);
        detailAddressLayout.setVisibility(View.VISIBLE);
        this.defaultMemberAddressVo = memberAddressVO;
        shippingName.setText("联系电话：" + memberAddressVO.getUserName());
        shippingAddress.setText("收货地址：" + memberAddressVO.getAddress());
        shippingTel.setText("联系人：" + memberAddressVO.getTelephone());
    }

    /**
     * 获取订单提交对象
     *
     * @return
     */
    private SubmitOrder getSubmitOrder() {
        SubmitOrder submitOrder = new SubmitOrder();
        StringBuffer shopIdBuffer = new StringBuffer();
        StringBuffer shopExpressTypeBuffer = new StringBuffer();
        StringBuffer shopExpressPriceBuffer = new StringBuffer();
        StringBuffer remarkBuffer = new StringBuffer();
        LinkedList<ShoppingCartVO> shoppingCartVOLinkedList = new LinkedList<>();

        if (defaultMemberAddressVo != null) {
            submitOrder.setAddressId(defaultMemberAddressVo.getId());
        } else {
            showWarning(R.string.please_select_address);
            return null;
        }

        for (int position = 0; position < shopCartProductDataList.size(); position++) {
            ShopCartProductData shopCartData = shopCartProductDataList.get(position);
            shopIdBuffer.append(shopCartData.getShopId()).append("|");
            if (shopCartData.getExpressPriceVO() == null && !shopCartData.isFree()) {//未选择运费模版
                showWarning(R.string.please_select_express_model_title);
                return null;
            }

            View cellView = submitOrderAdapter.dataMap.get(position);
            String remarkStr = ((ClearEditText) cellView.findViewById(R.id.leave_message)).getText().toString();
            shopCartData.setRemark(remarkStr);

            if (shopCartData.getExpressPriceVO() != null) {
                shopExpressTypeBuffer.append(shopCartData.getExpressPriceVO().getType()).append("|");

                shopExpressPriceBuffer.append(shopCartData.getExpressPriceVO().getPrice()).append("|");
            } else {
                shopExpressTypeBuffer.append("").append("|");

                shopExpressPriceBuffer.append("").append("|");
            }

            remarkBuffer.append(shopCartData.getRemark()).append("|");


            for (String ids : shopCartData.getProductVOHashMap().keySet()) {
                ProductCart productCart = shopCartData.getProductVOHashMap().get(ids);
                ShoppingCartVO shoppingCartVO = new ShoppingCartVO();
                shoppingCartVO.setShopNo(productCart.getProductVO().getShopNo());
                shoppingCartVO.setStandardIds(productCart.getStandardIDs());
                shoppingCartVO.setStandardValues(productCart.getStandardNames());
                shoppingCartVO.setStandardKeys(productCart.getIdAndValueIDs());
                shoppingCartVO.setStandardNames(productCart.getIdAndValueNames());
                shoppingCartVO.setShopId(shopCartData.getShopId());
                shoppingCartVO.setActive(productCart.getProductVO().getActive());
                shoppingCartVO.setProductId(productCart.getProductVO().getId());
                shoppingCartVO.setProductName(productCart.getProductVO().getName());
                shoppingCartVO.setImageFile(productCart.getProductVO().getIcon());
                shoppingCartVO.setPrice(productCart.getSalePrice());
                shoppingCartVO.setGoodsNum(productCart.getNum());
                shoppingCartVO.setTotal(productCart.getTotal());
                shoppingCartVOLinkedList.add(shoppingCartVO);
            }
        }
        submitOrder.setExpressType(shopExpressTypeBuffer.toString());
        submitOrder.setExpressPrice(shopExpressPriceBuffer.toString());
        submitOrder.setRemark(remarkBuffer.toString());
        submitOrder.setList(shoppingCartVOLinkedList);
        submitOrder.setShopType(shopType);
        submitOrder.setOrderType(OrderType.COMMON.name());
        submitOrder.setMemberId(member.getMember().getId());
        submitOrder.setShopId(shopIdBuffer.toString());

        return submitOrder;
    }

    /**
     * 显示选择运费模版
     */
    public void showExpressModelWindow(final int position) {
        final ShopCartProductData shopCartProductData = (ShopCartProductData) submitOrderAdapter.getItem(position);
        final ExpressAdapter[] expressAdapter = {null};
        View view = LayoutInflater.from(mContext).inflate(R.layout.express_popuwindow_layout, null);
        final ListView listView = (ListView) view.findViewById(R.id.list_view);
        final TextView title = (TextView) view.findViewById(R.id.title);
        final TextView cancel = (TextView) view.findViewById(R.id.cancel);
        final TextView ok = (TextView) view.findViewById(R.id.ok);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        title.setText(R.string.express_title);

        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        List linkedList = new ArrayList();
                        ExpressModel expressModel;
                        expressModel = (ExpressModel) msg.obj;

                        linkedList = expressModel.getList();
                        progressBar.setVisibility(View.GONE);
                        if (linkedList != null && linkedList.size() == 0) {// 免运费
                            linkedList.add(null);
                        }

                        if (expressAdapter[0] == null) {
                            expressAdapter[0] = new ExpressAdapter(mContext, linkedList);
                        }

                        listView.setAdapter(expressAdapter[0]);
                        if (((ExpressModel) msg.obj).getList().size() > 0) {
                            listView.setSelection(0);
                        }
                        break;
                    case 1:
                        progressBar.setVisibility(View.GONE);
                        showWarning(msg.obj.toString());
                        break;
                }
            }
        };


        final PopupWindowUtil popupWindowUtil = new PopupWindowUtil(this, view, findViewById(R.id.price));
        popupWindowUtil.backgroundAlpha(0.5f);
        popupWindowUtil.showPopupWindow();
        progressBar.setVisibility(View.VISIBLE);
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (submitOrderDao == null) {
                    submitOrderDao = new SubmitOrderDao(mContext);
                }
                ExpressModelData expressModelData = submitOrderDao.getExpressModelData((ShopCartProductData) submitOrderAdapter.getItem(position), defaultMemberAddressVo.getNo());
                Message message = Message.obtain();
                if (expressModelData != null) {
                    if (expressModelData.isSuccess()) {
                        message.what = 0;
                        message.obj = expressModelData.getData();
                        mHandler.sendMessage(message);
                    } else {
                        message.what = 1;
                        message.obj = getText(R.string.netWork_warning);
                        mHandler.sendMessage(message);
                    }
                } else {
                    message.what = 1;
                    message.obj = getText(R.string.net_error);
                    mHandler.sendMessage(message);
                }
            }
        }.start();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowUtil.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expressAdapter[0].dataList.get(0) == null) {// 店铺包邮
                    shopCartProductData.setExpressPriceVO(null);
                    shopCartProductData.setIsFree(true);
                    popupWindowUtil.dismiss();
                    submitOrderAdapter.updateIndexCell(shopCartProductData, position, listView);
                    setTotalMoney((LinkedList<ShopCartProductData>) submitOrderAdapter.dataList);
                    return;
                }
                if (listView.getCheckedItemPosition() != -1) {
                    ExpressPriceVO expressPriceVO = (ExpressPriceVO) expressAdapter[0].getItem(listView.getCheckedItemPosition());
                    shopCartProductData.setExpressPriceVO(expressPriceVO);
                    shopCartProductData.setIsFree(false);
                    popupWindowUtil.dismiss();
                    submitOrderAdapter.updateIndexCell(shopCartProductData, position, listView);
                    setTotalMoney((LinkedList<ShopCartProductData>) submitOrderAdapter.dataList);
                    return;
                }
            }
        });
    }

    /**
     * 设置总价
     */
    private void setTotal() {
        String totalMoney = "";
        for (String shopID : shopCartProductDataHashMap.keySet()) {
            ShopCartProductData shopCartProductData = shopCartProductDataHashMap.get(shopID);
            for (String key : shopCartProductData.getProductVOHashMap().keySet()) {
                ProductCart productCart = shopCartProductData.getProductVOHashMap().get(key);
                totalMoney = StringNumber.add(totalMoney, String.valueOf(productCart.getTotal()));
            }
            if (shopCartProductData.getExpressPriceVO() != null) {
                totalMoney = StringNumber.add(totalMoney, "" + shopCartProductData.getExpressPriceVO().getPrice());
            }
        }
        price.setText(totalMoney + "元");
    }

    public void setTotalMoney(LinkedList<ShopCartProductData> dataList) {
        String totalMoney = "0";
        for (ShopCartProductData productData : dataList) {
            for (int position = 0; position < productData.getProductVOHashMap().keySet().size(); position++) {
                ProductCart productCart = productData.getProductVOHashMap().get(productData.getProductVOHashMap().keySet().toArray()[position]);
                if (productCart.isSelect()) {
                    BigDecimal total = new BigDecimal(totalMoney);
                    BigDecimal price = new BigDecimal("" + productCart.getSalePrice());
                    BigDecimal num = new BigDecimal("" + productCart.getNum());
                    BigDecimal singleMoney = price.multiply(num);
                    totalMoney = total.add(singleMoney).toString();
                }
            }
        }
        price.setText(totalMoney + "元");

    }

    /**
     * 选择物流模版
     *
     * @param position
     */
    public void selectExpressModel(int position) {
        if (defaultMemberAddressVo != null) {
            showExpressModelWindow(position);
        } else {
            showWarning(R.string.please_select_address);
        }
    }

    /**
     * 重新选择收货地址后，重置已选模版
     * resetExpressModel
     */
    private void resetExpressModel() {
        for (ShopCartProductData data : shopCartProductDataList) {
            data.setExpressPriceVO(null);
        }
        if (submitOrderAdapter == null) {
            submitOrderAdapter = new SubmitSaleOrderAdapter(this, shopCartProductDataList);
            mListView.setAdapter(submitOrderAdapter);
        }
        submitOrderAdapter.dataList = shopCartProductDataList;
        submitOrderAdapter.notifyDataSetChanged();
    }


}
