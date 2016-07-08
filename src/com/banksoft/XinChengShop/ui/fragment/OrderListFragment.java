package com.banksoft.XinChengShop.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.adapter.OrderLisAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.dao.OrderListDao;
import com.banksoft.XinChengShop.entity.OrderList;
import com.banksoft.XinChengShop.entity.OrderVO;
import com.banksoft.XinChengShop.entity.ReturnProduct;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.type.OrderMaster;
import com.banksoft.XinChengShop.type.OrderStatus;
import com.banksoft.XinChengShop.ui.OrderInfoActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseListFragment;
import com.banksoft.XinChengShop.utils.JSONHelper;
import com.banksoft.XinChengShop.widget.MyProgressDialog;

import java.util.ArrayList;

/**
 * Created by Robin on 2015/2/2.
 */
public class OrderListFragment extends XCBaseListFragment {
    private String type;
    private XCBaseActivity activity;
    private String orderMaster;
    private OrderListDao orderListDao;
    private MyProgressDialog myProgressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = (String) getArguments().get(IntentFlag.ORDER_TYPE);
        orderMaster =  getArguments().getString(IntentFlag.Order_MASTER,"");
        activity = (XCBaseActivity) getActivity();
        myProgressDialog = new MyProgressDialog(mContext);
    }

    @Override
    public void request() {
        url = ControlUrl.ORDER_LIST_URL;
        if(OrderMaster.SELLER.name().equals(orderMaster)){//卖家订单
            params = "status="+type+"&shopId="+activity.member.getShop().getId();
        }else{

            params = "status="+type+"&memberId="+activity.member.getMember().getId();
        }

        jsonType = JSONHelper.ORDER_LIST_DATA;
        bailaAdapter = new OrderLisAdapter(mContext,new ArrayList());
        xListView.setDividerHeight(20);
        setListDao();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Activity.RESULT_FIRST_USER){
          switch (resultCode){
              case Activity.RESULT_OK:  //�����ɹ�
                  request();
                  break;
              case Activity.RESULT_CANCELED: //ȡ�����
                  request();
                  break;
          }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public Fragment getInstance() {
        return null;
    }

    @Override
    public CharSequence getTitle() {
        return null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext,OrderInfoActivity.class);
        intent.putExtra(IntentFlag.ORDER_VO,(OrderVO)bailaAdapter.getItem(position - 1));
        startActivityForResult(intent,Activity.RESULT_FIRST_USER);
    }

    /**
     * 取消订单
     */
    private class CancelTask extends AsyncTask<OrderListDao,String,IsFlagData>{
         String orderId;

        public CancelTask(String orderId) {
            this.orderId = orderId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myProgressDialog.showDialog("");

        }

        @Override
        protected IsFlagData doInBackground(OrderListDao... params) {
            return params[0].cancelOrder(orderId);
        }

        @Override
        protected void onPostExecute(IsFlagData isFlagData) {
            super.onPostExecute(isFlagData);
            if(isFlagData != null){
                if(isFlagData.isSuccess()){
                    alert(R.string.order_cancel_success);
                    onRefresh();
                }else{
                    alert(R.string.net_is_weak);
                }
            }else{
                alert(R.string.net_error);
            }
        }
    }

//    /**
//     * 退款
//     */
//    private class returnUnDispatchMoney extends AsyncTask<OrderListDao,String,IsFlagData>{
//        OrderStatus orderStatus;
//        ReturnProduct returnProduct;
//
//        public returnUnDispatchMoney(OrderStatus orderStatus, ReturnProduct returnProduct) {
//            this.orderStatus = orderStatus;
//            this.returnProduct = returnProduct;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected IsFlagData doInBackground(OrderListDao... params) {
//            return params[0].returnMoney(returnProduct,orderStatus);
//        }
//
//        @Override
//        protected void onPostExecute(IsFlagData isFlagData) {
//            super.onPostExecute(isFlagData);
//        }
//    }
}
