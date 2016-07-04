package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by Robin on 2016/4/11.
 */
public class CheckCodeDao extends BaseDao {
    public CheckCodeDao(Context mContext) {
        super(mContext);
    }

    public IsFlagData checkCode(String shopId, String checkCode) {
        String url = ControlUrl.XC_SHOP_CHECK_CODE_URL;
        String params = "shopId=" + shopId + "&checkCode=" + checkCode;
        IsFlagData data = (IsFlagData) postHttpRequest(mContext, url, params, JSONHelper.IS_FLAG_DATA, false);
        return data;
    }
}
