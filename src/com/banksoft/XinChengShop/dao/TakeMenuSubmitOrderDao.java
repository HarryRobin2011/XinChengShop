package com.banksoft.XinChengShop.dao;

import android.content.ContentUris;
import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.entity.SubmitOrder;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.model.MemberAddressVOData;
import com.banksoft.XinChengShop.model.OrderVOListData;
import com.banksoft.XinChengShop.type.ShopType;
import com.banksoft.XinChengShop.utils.JSONHelper;
import com.banksoft.XinChengShop.utils.StringUtil;

/**
 * Created by admin on 2016/7/1.
 * 外卖订单提交
 */
public class TakeMenuSubmitOrderDao extends BaseDao {

    public TakeMenuSubmitOrderDao(Context mContext) {
        super(mContext);
    }

    public OrderVOListData submitOrder(SubmitOrder submitOrder){
        String url = ControlUrl.SUBMIT_ORDER_URL;
        String params = getSubmitOrderParams(submitOrder);
        OrderVOListData data = (OrderVOListData) postHttpRequest(mContext, url, params, JSONHelper.ORDER_VO_LIST_DATA, false);
        return data;
    }

    private String getSubmitOrderParams(SubmitOrder submitOrder) {
        StringBuffer paramsBuffer = new StringBuffer(getListParams(submitOrder.getList(),"list"));
        paramsBuffer.append("&memberId=").append(StringUtil.checkEmpty(submitOrder.getMemberId()));
        paramsBuffer.append("&shopId=").append(StringUtil.checkEmpty(submitOrder.getShopId()));
        paramsBuffer.append("&expressPrice=").append(StringUtil.checkEmpty(submitOrder.getExpressPrice()));
        paramsBuffer.append("&expressType=").append(StringUtil.checkEmpty(submitOrder.getExpressType()));
        paramsBuffer.append("&addressId=").append(StringUtil.checkEmpty(submitOrder.getAddressId()));
        paramsBuffer.append("&remark=").append(StringUtil.checkEmpty(submitOrder.getRemark()));
        paramsBuffer.append("&orderType=").append(StringUtil.checkEmpty(submitOrder.getOrderType()));
        if (ShopType.SHOP_SALE.name().equals(StringUtil.checkEmpty(submitOrder.getShopType()))) {//商城类订单
            //
        } else {//美食外卖，服务类店铺
            paramsBuffer.append("&express=").append(submitOrder.getExpress());
            paramsBuffer.append("&telephone=").append(submitOrder.getTelephone());
            paramsBuffer.append("&orderTime=").append(submitOrder.getOrderTime());
            paramsBuffer.append("&orderTable＝").append(submitOrder.getOrderTable());
        }
        return paramsBuffer.toString();
    }



    public MemberAddressVOData getMemberAddressData(String memberId, boolean cacheFlag) {
        String url = ControlUrl.SHIPPING_LIST_URL;
        String params = "memberId=" + memberId;
        MemberAddressVOData data = (MemberAddressVOData) postHttpRequest(mContext, url, params, JSONHelper.MEMBER_ADDRESS_DATA, cacheFlag);
        return data;
    }
}
