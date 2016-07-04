package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.ProductTypeData;
import com.banksoft.XinChengShop.model.ShopProductTypeBOData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by Robin on 2016/4/12.
 */
public class SelectGoodsCategoryDao extends BaseDao{

    public SelectGoodsCategoryDao(Context mContext) {
        super(mContext);
    }

    /**
     * 获取商品分类
     * @param level
     * @return
     */
    public ProductTypeData getProductTypeBOData ( int level,String no){
        String url = ControlUrl.XC_PRODUCT_TYPE_URL;
        String params = "&grade="+level+"&no="+no;
        ProductTypeData data = (ProductTypeData) postHttpRequest(mContext,url,params, JSONHelper.XC_SHOP_PRODUCT_TYPE_DATA,true);
        return data;
    }
}
