package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.XCApplication;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.utils.JSONHelper;
import com.sina.weibo.sdk.api.share.Base;

/**
 * Created by admin on 2016/7/7.
 */
public class ProductManagerDao extends BaseDao{

    public ProductManagerDao(Context mContext) {
        super(mContext);
    }

    /**
     * 产品下架
     * @param ids
     * @return
     */
    public IsFlagData productTakeOff(String ids){
       String url = ControlUrl.XC_PRODUCT_TAKE_OFF_URL;
        String params = "ids="+ids;
        IsFlagData data = (IsFlagData) postHttpRequest(mContext,url,params, JSONHelper.IS_FLAG_DATA,false);
        return data;
    }

    /**
     * 产品上架
     * @param ids
     * @return
     */
    public IsFlagData productTakeOn(String ids){
        String url = ControlUrl.XC_PRODUCT_TAKE_ON_URL;
        String params = "ids="+ids;
        IsFlagData data = (IsFlagData) postHttpRequest(mContext,url,params, JSONHelper.IS_FLAG_DATA,false);
        return data;
    }


}
