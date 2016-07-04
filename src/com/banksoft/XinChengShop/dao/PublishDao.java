package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.entity.ProductBO;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.model.ShopProductTypeBOData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by Robin on 2016/4/12.
 */
public class PublishDao extends BaseDao{
    public PublishDao(Context mContext) {
        super(mContext);
    }


    /**
     * 发布产品
     */

    public IsFlagData submitGoods(ProductBO productBO){
        String url = ControlUrl.SHOP_SUBMIT_GOODS;
        String params = getParams(productBO,"data");
        IsFlagData isFlagData = (IsFlagData) postHttpRequest(mContext,url,params,JSONHelper.IS_FLAG_DATA,false);
        return isFlagData;
    }


}
