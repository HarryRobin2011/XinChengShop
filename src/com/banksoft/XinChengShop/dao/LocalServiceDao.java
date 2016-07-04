package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.entity.ShopLocalType;
import com.banksoft.XinChengShop.model.ShopLocalTypeData;
import com.banksoft.XinChengShop.model.ShopProductListData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by admin on 2016/6/21.
 */
public class LocalServiceDao extends BaseDao {

    public LocalServiceDao(Context mContext) {
        super(mContext);
    }

    /**
     * 查询店铺分类
     * @return
     */
    public ShopLocalTypeData getShopLocalTypeData() {
        String url = ControlUrl.XC_SHOP_LOCAL_TYPE_URL;
        String params = "";
        ShopLocalTypeData shopLocalTypeData = (ShopLocalTypeData) postHttpRequest(mContext, url, params, JSONHelper.XC_SHOP_LOCAL_TYPE_DATA, true);
        return shopLocalTypeData;
    }

}
