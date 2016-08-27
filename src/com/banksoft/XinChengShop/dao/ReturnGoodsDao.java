package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.entity.ReturnMoney;
import com.banksoft.XinChengShop.entity.ReturnProduct;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by Robin on 2016/7/22.
 */
public class ReturnGoodsDao extends BaseDao{

    public ReturnGoodsDao(Context mContext) {
        super(mContext);
    }

    /**
     *买家已付款未发货申请退款
     * @return
     */
    public IsFlagData returnMoney(ReturnMoney returnMoney){
        String url = ControlUrl.XC_ORDER_UN_DISPATCH_RETURN_MONEY_URL;
        String params = getParams(returnMoney,"data");
        IsFlagData isFlagData = (IsFlagData) postHttpRequest(mContext,url,params, JSONHelper.IS_FLAG_DATA,false);
        return isFlagData;
    }

    /**
     *已发货未收货，买家申请退款
     * @return
     */
    public IsFlagData returnProductMoney(ReturnProduct returnProduct){
        String url = ControlUrl.XC_ORDER_DISPATCH_RETURN_MONEY_URL;
        String params = getParams(returnProduct,"data");
        IsFlagData isFlagData = (IsFlagData) postHttpRequest(mContext,url,params, JSONHelper.IS_FLAG_DATA,false);
        return isFlagData;
    }

    /**
     *已发货未收货，买家申请退货
     * @return
     */
    public IsFlagData returnProduct(ReturnProduct returnMoney){
        String url = ControlUrl.XC_ORDER_RETURN_GOODS_URL;
        String params = getParams(returnMoney,"data");
        IsFlagData isFlagData = (IsFlagData) postHttpRequest(mContext,url,params, JSONHelper.IS_FLAG_DATA,false);
        return isFlagData;
    }


}
