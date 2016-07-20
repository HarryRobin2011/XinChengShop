package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.OrderInfoData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by admin on 2016/7/20.
 */
public class OrderInfoDao extends BaseDao{
    public OrderInfoDao(Context mContext) {
        super(mContext);
    }

    public OrderInfoData getOrderInfoData(String orderId){
        String url = ControlUrl.ORDER_LIST_URL;
        String params = "orderId="+orderId;
        OrderInfoData data = (OrderInfoData) postHttpRequest(mContext,url,params, JSONHelper.XC_ORDER_INFO_DATA,true);
        return data;
    }
}
