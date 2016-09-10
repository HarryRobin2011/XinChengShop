package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by Robin on 2016/9/9.
 */
public class UpdateOrderDao extends BaseDao {

    public UpdateOrderDao(Context mContext) {
        super(mContext);
    }

    /**
     * 卖家修改订单价格
     *
     * @param total
     * @param expressMoney
     * @param orderId
     * @return
     */
    public IsFlagData UpdatePrice(String orderId, String total, String expressMoney) {
        String url = ControlUrl.XC_SHOP_UPDATE_ORDER_PRICE;
        String params = "&id=" + orderId + "&totalMoney=" + total + "&expressMoney=" + expressMoney;
        IsFlagData data = (IsFlagData) postHttpRequest(mContext, url, params, JSONHelper.IS_FLAG_DATA, false);
        return data;
    }
}
