package com.banksoft.XinChengShop.ui.takeout;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.*;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.TakeOutInfoAdapter;
import com.banksoft.XinChengShop.adapter.TakeOutMenuProductAdapter;
import com.banksoft.XinChengShop.adapter.TakeOutSubmitProductAdapter;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.config.MapFlag;
import com.banksoft.XinChengShop.dao.SubmitOrderDao;
import com.banksoft.XinChengShop.dao.TakeMenuSubmitOrderDao;
import com.banksoft.XinChengShop.entity.*;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.model.MemberAddressVOData;
import com.banksoft.XinChengShop.model.OrderVOListData;
import com.banksoft.XinChengShop.model.ShopCartProductData;
import com.banksoft.XinChengShop.type.OrderType;
import com.banksoft.XinChengShop.type.ShopType;
import com.banksoft.XinChengShop.ui.AddressSelectActivity;
import com.banksoft.XinChengShop.ui.PayActivity;
import com.banksoft.XinChengShop.ui.SubmitSaleOrderActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.fragment.TimeSelectFragment;
import com.banksoft.XinChengShop.utils.TimeUtils;
import com.banksoft.XinChengShop.widget.ClearEditText;
import com.banksoft.XinChengShop.widget.MyGridView;
import com.banksoft.XinChengShop.widget.MyProgressDialog;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by harry_robin on 16/3/28.
 */
public class TakeMenuSubmitOrderAcvitity extends XCBaseActivity implements View.OnClickListener,TimeSelectFragment.OnSelectTimeListener{
    private LinearLayout addressLayout;// 地址
    private LinearLayout reservationLayout;//预定人
    private LinearLayout deliveryTimeLayout;

    private TextView time;
    private TextView remark;
    private boolean is_receipt;
    private ToggleButton receipt;
    private TextView shopTitle;
    private MyGridView myGridView;
    private TakeOutSubmitProductAdapter takeOutSubmitProductAdapter;

    private ImageView back;
    private ShopVO shopVO;

    private TakeMenuSubmitOrderDao takeMenuSubmitOrderDao;

    private LinearLayout detailAddressLayout;


    private MemberAddressVO defaultMemberAddressVo;


    private TextView title;


    private TextView addAddress, shippingName, shippingTel, shippingAddress;
    private final int SELECT_ADDRESS_ARNO = 0;// 选择收货地址
    private final int ADDRESS_MANAGER = 1;//收货地址管理

    private LinearLayout detailLayout;
    private ProgressBar mProgressBar;

    private long takeOutTime;

    private HashMap<String, HashMap<String, Object>> cartMap;
    private String shopName;

    private List dataList;

    private String shopId;

    private MyProgressDialog myProgressDialog;


    @Override
    protected void initContentView() {
        setContentView(R.layout.take_out_submit_order_layout);
    }

    @Override
    protected void initView() {
        time = (TextView) findViewById(R.id.time);
        remark = (TextView) findViewById(R.id.remark);
        receipt = (ToggleButton) findViewById(R.id.is_receipt);
        shopTitle = (TextView) findViewById(R.id.shop_name);
        myGridView = (MyGridView) findViewById(R.id.gridView);
        title = (TextView) findViewById(R.id.titleText);
        back = (ImageView) findViewById(R.id.title_back_button);
        addAddress = (TextView) findViewById(R.id.add_address);


        shippingName = (TextView) findViewById(R.id.shipping_name);
        shippingAddress = (TextView) findViewById(R.id.address);
        shippingTel = (TextView) findViewById(R.id.telPhone);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        detailLayout = (LinearLayout) findViewById(R.id.detail_layout);
        detailAddressLayout = (LinearLayout) findViewById(R.id.address_detail_layout);
    }

    @Override
    protected void initData() {
        cartMap = (HashMap<String, HashMap<String, Object>>) getIntent().getSerializableExtra(IntentFlag.DATA_MAP);
        title.setText(R.string.please_select_address);
        back.setVisibility(View.VISIBLE);
        if(dataList == null){
            dataList = new LinkedList();
        }
        dataList.clear();
        for(String key:cartMap.keySet()) {
            dataList.add(cartMap.get(key));
            shopName = ((ShopProductListVO) cartMap.get(key).get(MapFlag.DATA_0)).getShopName();
            shopId = ((ShopProductListVO) cartMap.get(key).get(MapFlag.DATA_0)).getShopId();
        }
        shopTitle.setText(shopName);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_ADDRESS_ARNO) {//选择收货地址
            MemberAddressVO addressVO = (MemberAddressVO) data.getSerializableExtra(IntentFlag.ADDRESS);

            initDefaultMemberAddressVo(addressVO);
        } else if (requestCode == ADDRESS_MANAGER) {// 收货地址管理
            MemberAddressVO addressVO = (MemberAddressVO) data.getSerializableExtra(IntentFlag.ADDRESS);
            initDefaultMemberAddressVo(addressVO);
        }
    }

    @Override
    protected void initOperate() {
        if(takeMenuSubmitOrderDao == null ){
            takeMenuSubmitOrderDao = new TakeMenuSubmitOrderDao(mContext);
        }
        new MyThread().execute(takeMenuSubmitOrderDao);
        if(takeOutSubmitProductAdapter == null){
            takeOutSubmitProductAdapter = new TakeOutSubmitProductAdapter(mContext,dataList);
        }
        detailAddressLayout.setOnClickListener(this);
        addAddress.setOnClickListener(this);
        time.setOnClickListener(this);
        myGridView.setAdapter(takeOutSubmitProductAdapter);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.submit_order:

               break;
           case R.id.title_back_button:
               finish();
               break;
           case R.id.address_detail_layout://选择收获地址
               Intent selectIntent = new Intent(mContext, AddressSelectActivity.class);
               startActivityForResult(selectIntent,ADDRESS_MANAGER);
               break;
           case R.id.add_address_address:
               Intent intent = new Intent(mContext, AddressSelectActivity.class);
               startActivityForResult(intent,SELECT_ADDRESS_ARNO);
               break;
           case R.id.time:
               TimeSelectFragment timeSelectFragment = new TimeSelectFragment();
               timeSelectFragment.show(getSupportFragmentManager(),timeSelectFragment.getClass().getSimpleName());
               break;
       }
    }

    @Override
    public void OnSelectTime(String currentTime) {
        this.takeOutTime = TimeUtils.getDateTime(currentTime, TimeUtils.TimeType.MINUTE);
        time.setText(currentTime);
    }

    //请求默认地址
    private class MyThread extends AsyncTask<TakeMenuSubmitOrderDao, String, MemberAddressVOData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected MemberAddressVOData doInBackground(TakeMenuSubmitOrderDao... params) {
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
     * 提交订单
     */
    private class MyTask extends AsyncTask<TakeMenuSubmitOrderDao,String,OrderVOListData>{
        SubmitOrder submitOrder;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            submitOrder = getSubmitOrder();
            if(submitOrder == null){
                this.cancel(true);
                return;
            }
            if (myProgressDialog == null) {
                myProgressDialog = new MyProgressDialog(TakeMenuSubmitOrderAcvitity.this);
            }
            myProgressDialog.showDialog(R.string.please_wait);
        }

        @Override
        protected OrderVOListData doInBackground(TakeMenuSubmitOrderDao... params) {
            return params[0].submitOrder(submitOrder);
        }

        @Override
        protected void onPostExecute(OrderVOListData orderVOListData) {
            super.onPostExecute(orderVOListData);
            myProgressDialog.dismiss();
            if (orderVOListData != null) {
                if (orderVOListData.isSuccess()) {
                    Intent intent = new Intent(mContext,PayActivity.class);
                    intent.putExtra(IntentFlag.ORDER_VO_LIST, orderVOListData.getData().getOrderList());
                    startActivity(intent);
                } else {
                    showWarning(R.string.netWork_warning);
                }
            } else {
                showWarning(R.string.net_error);
            }
        }
    }


    /**
     * 获取订单提交对象
     *
     * @return
     */
    private SubmitOrder getSubmitOrder() {
        SubmitOrder submitOrder = new SubmitOrder();
        LinkedList<ShoppingCartVO> shoppingCartVOLinkedList = new LinkedList<>();

        if (defaultMemberAddressVo != null) {
            submitOrder.setAddressId(defaultMemberAddressVo.getId());
        } else {
            showWarning(R.string.please_select_address);
            return null;
        }

        for (int position = 0; position < cartMap.size();position++) {
             ShopProductListVO shopProductListVO = (ShopProductListVO) cartMap.get(cartMap.keySet().toArray()[position]).get(MapFlag.DATA_0);
             int num = (int) cartMap.get(cartMap.keySet().toArray()[position]).get(MapFlag.NUM);
                ShoppingCartVO shoppingCartVO = new ShoppingCartVO();
                shoppingCartVO.setShopNo(shopProductListVO.getShopNO());
                shoppingCartVO.setShopId(shoppingCartVO.getShopId());
                shoppingCartVO.setActive(shoppingCartVO.getActive());
                shoppingCartVO.setProductId(shoppingCartVO.getId());
                shoppingCartVO.setProductName(shopProductListVO.getName());
                shoppingCartVO.setImageFile(shopProductListVO.getIcon());
                shoppingCartVO.setPrice(Float.valueOf(shopProductListVO.getPrice()));
            shoppingCartVO.setGoodsNum(num);
                BigDecimal totalDecimal = new BigDecimal(shopProductListVO.getPrice());
                Float totalFloat = totalDecimal.multiply(new BigDecimal(num)).floatValue();
                shoppingCartVO.setTotal(totalFloat);
                shoppingCartVOLinkedList.add(shoppingCartVO);
        }
        submitOrder.setRemark(remark.toString());
        submitOrder.setList(shoppingCartVOLinkedList);
        submitOrder.setShopType(ShopType.SHOP_SERVER.name());
        submitOrder.setOrderType(OrderType.SERVER.name());
        submitOrder.setMemberId(member.getMember().getId());
        submitOrder.setShopId(shopId);

        return submitOrder;
    }
}
