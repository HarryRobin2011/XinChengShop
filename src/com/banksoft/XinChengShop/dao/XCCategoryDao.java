package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.ProductTypeData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by harry_robin on 15/11/16.
 */
public class XCCategoryDao extends BaseDao{

    public String id;

    public XCCategoryDao(Context mContext) {
        super(mContext);
    }

    public ProductTypeData getProductTypeData(int grade,String no,boolean cacheFlag){
        String url = ControlUrl.XC_PRODUCT_TYPE_URL;
        String params = "no="+no+"&grade="+grade;
        ProductTypeData data = (ProductTypeData) postHttpRequest(mContext,url,params, JSONHelper.PRODUCT_TYPE_DATA,cacheFlag);
        return data;
    }
}
