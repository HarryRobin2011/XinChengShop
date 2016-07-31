package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.entity.FoundPassword;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by Harry on 2016-07-31.
 */
public class FoundPasswordDao extends BaseDao{

    public FoundPasswordDao(Context mContext) {
        super(mContext);
    }

    public FoundPassword getUserName(String account){
        String url = ControlUrl.XC_SHOP_LOCAL_TYPE_URL;
        String params = "userName="+account;
        FoundPassword password = (FoundPassword) postHttpRequest(mContext,url,params, JSONHelper.FOUND_PASSWORD,false);
        return password;
    }
}
