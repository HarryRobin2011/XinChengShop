package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.ShopInfoData;
import com.banksoft.XinChengShop.model.ShopProductListData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by harry_robin on 15/11/23.
 */
public class ShopInfoDao extends BaseDao{

    public ShopInfoDao(Context mContext) {
        super(mContext);
    }

    public ShopInfoData getShopInfoData(String shopID,boolean cacheFlag){
        String url = ControlUrl.XC_SHOP_INFO_URL;
        String params = "id="+shopID;
        ShopInfoData data = (ShopInfoData) postHttpRequest(mContext,url,params, JSONHelper.SHOP_INFO_DATA,cacheFlag);
        return data;
    }

    /**
     * 店铺商品
     * @param shopId
     * @param cacheFlag
     * @return
     */
    public ShopProductListData getShopProductListData(String shopId,boolean cacheFlag) {
        String url = ControlUrl.XC_SHOP_PRODUCT_LIST_URL;
        String params = "shopId="+shopId+"&index=1"+"&size=10";
        return (ShopProductListData) postHttpRequest(mContext,url,params,JSONHelper.SHOP_LIST_PRODUCT_DATA,cacheFlag);
    }
}
