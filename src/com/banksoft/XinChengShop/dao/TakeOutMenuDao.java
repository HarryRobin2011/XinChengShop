package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.ShopProductListData;
import com.banksoft.XinChengShop.model.ShopProductTypeBOData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by harry_robin on 16/3/21.
 */
public class TakeOutMenuDao extends BaseDao{
    public String categoryID;

    public TakeOutMenuDao(Context mContext) {
        super(mContext);
    }

    public ShopProductTypeBOData getShopProductTypeBOData(String shopId,String level,boolean cacheFlag){
        String url = ControlUrl.XC_SHOP_PRODUCT_TYPE_LIST;
        String params = "shopId="+shopId+"&level="+level;
        ShopProductTypeBOData data = (ShopProductTypeBOData) postHttpRequest(mContext,url,params, JSONHelper
                .XC_SHOP_PRODUCT_TYPE_PRODUCT_TYPE,cacheFlag);
        return data;
    }

    /**
     * 获取店铺分类下产品
     * @param shopID
     * @param typeNo
     * @return
     */
    public ShopProductListData getShopProductListData(String shopID,String typeNo,boolean cacheFlag){
        String url = ControlUrl.XC_SHOP_PRODUCT_URL;
        String params = "shopId="+shopID+"&selfTypeNo="+typeNo+"&index=1&size=20&status=true";
        ShopProductListData shopProductListData = (ShopProductListData) postHttpRequest(mContext,url,params,JSONHelper
                .XC_SHOP_PRODUCT_PRODUCT_DATA,cacheFlag);
        return shopProductListData;
    }
}
