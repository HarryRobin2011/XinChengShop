package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.type.ShopType;
import com.banksoft.XinChengShop.utils.JSONHelper;

import java.lang.reflect.Type;

/**
 * Created by harry_robin on 15/12/2.
 */
public class ShopCategoryDao extends BaseDao{

    public ShopCategoryDao(Context mContext) {
        super(mContext);
    }

    public BaseData getCategoryData(ShopType shopType,String no,boolean cacheFlag){
        BaseData data = null;
        String url = null;
        String params = null;
        Type type = null;
        if(shopType.equals(ShopType.SHOP_LOCAL)){
            url = ControlUrl.XC_SHOP_LOCAL_TYPE_URL;
            type = JSONHelper.XC_SHOP_LOCAL_TYPE_DATA;
        }else if(shopType.equals(ShopType.SHOP_SERVER)){
            url = ControlUrl.XC_SHOP_SERVER_TYPE_LIST;
            type = JSONHelper.XC_SHOP_SERVER_TYPE_DATA;
        }else if(shopType.equals(ShopType.SHOP_SALE)){
            url = ControlUrl.XC_SHOP_TYPE_LIST;
            type =JSONHelper.XC_SHOP_TYPE_DATA;
        }
        params = "no="+no;
        data = (BaseData) postHttpRequest(mContext,url,params,type,cacheFlag);
        return data;
    };


}
