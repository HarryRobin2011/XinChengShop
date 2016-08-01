package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.entity.FoundPassword;
import com.banksoft.XinChengShop.model.FoundPasswordData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by Harry on 2016-07-31.
 */
public class FoundPasswordDao extends BaseDao{

    public FoundPasswordDao(Context mContext) {
        super(mContext);
    }

    public FoundPasswordData getUserName(String account){
        String url = ControlUrl.XC_FOUND_USER_NAME;
        String params = "userName="+account;
        FoundPasswordData password = (FoundPasswordData) postHttpRequest(mContext,url,params, JSONHelper.FOUND_PASSWORD_DATA,false);
        return password;
    }
}
