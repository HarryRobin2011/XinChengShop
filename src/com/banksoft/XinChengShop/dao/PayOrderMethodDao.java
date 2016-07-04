package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.AlipayInfoData;
import com.banksoft.XinChengShop.model.AlipayOrderInfoData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by harry_robin on 16/1/22.
 */
public class PayOrderMethodDao extends BaseDao {

    public PayOrderMethodDao(Context mContext) {
        super(mContext);
    }

    /**
     * 获取支付报设置信息
     *
     * @return
     */
    public AlipayInfoData getAlipayInfoData() {
        String url = ControlUrl.XC_ALIPAY_FIND_INFO_URL;
        String params = "";
        AlipayInfoData infoData = (AlipayInfoData) postHttpRequest(mContext, url, params, JSONHelper.ALIPAY_INFO_DATA, false);
        return infoData;
    }

    /**
     * 获取支付宝订单信息
     *
     * @return
     */
    public AlipayOrderInfoData getAlipayOrderInfoData(String orderIds) {
        String url = ControlUrl.XC_ALIPAY_ORDER_URL;
        String params = "ids="+orderIds;
        AlipayOrderInfoData orderInfoData = (AlipayOrderInfoData) postHttpRequest(mContext, url, params, JSONHelper.ALIPAY_ORDER_INFO_DATA, false);
        return orderInfoData;
    }
}
