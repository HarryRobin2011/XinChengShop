package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.ProductTypeData;
import com.banksoft.XinChengShop.model.ShopProductTypeBOData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by admin on 2016/7/5.
 */
public class XCShopCategoryDao extends BaseDao{
    public String id;
    public XCShopCategoryDao(Context mContext) {
        super(mContext);
    }
    public ShopProductTypeBOData getProductTypeData(int grade, String no, boolean cacheFlag){
        String url = ControlUrl.XC_PRODUCT_TYPE_URL;
        String params = "no="+no+"&grade="+grade;
        ShopProductTypeBOData data = (ShopProductTypeBOData) postHttpRequest(mContext,url,params, JSONHelper.SHOP_PRODUCT_TYPE_DATA,cacheFlag);
        return data;
    }

}
