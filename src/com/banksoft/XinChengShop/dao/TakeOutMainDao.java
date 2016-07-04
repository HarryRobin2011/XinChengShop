package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.ShopServerTypeData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by harry_robin on 16/3/17.
 */
public class TakeOutMainDao extends BaseDao {
    public TakeOutMainDao(Context mContext) {
        super(mContext);
    }

    /**
     * 美食外卖店铺分类
     * @param cacheFlag
     * @return
     */
    public ShopServerTypeData getShopServerType(boolean cacheFlag){
        String url = ControlUrl.XC_SHOP_SERVER_TYPE_LIST;
        String params = "";
        ShopServerTypeData shopServerTypeData = (ShopServerTypeData) postHttpRequest(mContext,url,params, JSONHelper
                .XC_SHOP_SERVER_TYPE_DATA,cacheFlag);
        return shopServerTypeData;
    }

    /**
     * 本地服务店铺分类
     * @param cacheFlag
     * @return
     */
    public ShopServerTypeData getShopLocalServerType(boolean cacheFlag){
        String url = ControlUrl.XC_SHOP_LOCAL_TYPE_URL;
        String params = "";
        ShopServerTypeData shopServerTypeData = (ShopServerTypeData) postHttpRequest(mContext,url,params, JSONHelper
                .XC_SHOP_LOCAL_TYPE_DATA,cacheFlag);
        return shopServerTypeData;
    }
}
