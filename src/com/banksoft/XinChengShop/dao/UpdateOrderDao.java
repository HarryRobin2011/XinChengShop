package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.IsFlagData;

/**
 * Created by Robin on 2016/9/9.
 */
public class UpdateOrderDao extends BaseDao{

    public UpdateOrderDao(Context mContext) {
        super(mContext);
    }

    public IsFlagData UpdatePrice(){
        String url = ControlUrl.XC_SHOP_UPDATE_ORDER_PRICE;
        return null;
    }
}
