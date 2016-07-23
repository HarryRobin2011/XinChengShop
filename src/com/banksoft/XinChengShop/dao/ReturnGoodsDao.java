package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.IsFlagData;

/**
 * Created by Robin on 2016/7/22.
 */
public class ReturnGoodsDao extends BaseDao{

    public ReturnGoodsDao(Context mContext) {
        super(mContext);
    }

    public IsFlagData returnMoney(String orderId){
        String url = ControlUrl.XC_ORDER_UN_DISPATCH_RETURN_MONEY_URL;
        String params = "";
        return null;
    }


}
