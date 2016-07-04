package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.ShopInfoData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by harry_robin on 16/3/22.
 */
public class TakeOutShopInfoDao extends BaseDao{

    public TakeOutShopInfoDao(Context mContext) {
        super(mContext);
    }

    /**
     * 获取店铺详情
     * @param shopId
     * @param mContext
     * @param cacheFlag
     * @return
     */
    public ShopInfoData getShopInfoData(String shopId,boolean cacheFlag){
        String url = ControlUrl.XC_SHOP_INFO_URL;
        String params = "id="+shopId;
        ShopInfoData shopInfoData = (ShopInfoData) postHttpRequest(mContext,url,params, JSONHelper.SHOP_INFO_DATA,cacheFlag);
        return shopInfoData;
    }
}
