package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.AdvertisementData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by Robin on 2016/12/29.
 */
public class WelcomeDao extends BaseDao{
    public WelcomeDao(Context mContext) {
        super(mContext);
    }

    /**
     * 获取手机BANNER
     *
     * @param cacheFlag
     * @return
     */
    public AdvertisementData getAdvertisementData(boolean cacheFlag) {
        String url = ControlUrl.XC_BANNER_URL;
        String params = "no=wel-1";
        AdvertisementData data = (AdvertisementData) postHttpRequest(mContext, url, params, JSONHelper.ADVERTISEMENTDATA, cacheFlag);
        return data;
    }
}
