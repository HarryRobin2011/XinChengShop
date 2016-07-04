package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.ExpressModelBOData;
import com.banksoft.XinChengShop.model.ExpressModelData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by Robin on 2016/4/13.
 */
public class FreightSelectDao extends BaseDao{
    public FreightSelectDao(Context mContext) {
        super(mContext);
    }
    /**
     * 获取运费模板
     * @param shopId
     * @return
     */
    public ExpressModelBOData getExpressModelBOData(String shopId){
        String url = ControlUrl.SHOP_EXPRESS_MODEL_URL;
        String params = "shopId="+shopId;
        ExpressModelBOData data = (ExpressModelBOData) postHttpRequest(mContext,url,params, JSONHelper.EXPRESS_SHOP_MODEL_DATA,true);
        return data;
    }
}
