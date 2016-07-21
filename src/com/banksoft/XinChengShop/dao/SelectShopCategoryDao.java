package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.ShopLocalTypeData;
import com.banksoft.XinChengShop.model.ShopServerTypeData;
import com.banksoft.XinChengShop.model.ShopVoTypeData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by Harry on 2016-07-20.
 */
public class SelectShopCategoryDao extends BaseDao{

    public SelectShopCategoryDao(Context mContext) {
        super(mContext);
    }

    public ShopServerTypeData getShopServerTypeData(String no){
        String url = ControlUrl.XC_SHOP_SERVER_TYPE_LIST;
        String params = "no="+no;
        ShopServerTypeData data = (ShopServerTypeData) postHttpRequest(mContext,url,params, JSONHelper.XC_SHOP_SERVER_TYPE_DATA,true);
        return data;
    }

    public ShopVoTypeData getShopTypeVOData(String no){
        String url = ControlUrl.XC_SHOP_TYPE_LIST;
        String params = "no="+no;
        ShopVoTypeData data = (ShopVoTypeData) postHttpRequest(mContext,url,params, JSONHelper.XC_SHOP_TYPE_DATA,true);
        return data;
    }

    public ShopLocalTypeData getShopLocalTypeData(String no){
        String url = ControlUrl.XC_SHOP_LOCAL_TYPE_URL;
        String params = "no="+no;
        ShopLocalTypeData data = (ShopLocalTypeData) postHttpRequest(mContext,url,params, JSONHelper.XC_SHOP_LOCAL_TYPE_DATA,true);
        return data;
    }
}
