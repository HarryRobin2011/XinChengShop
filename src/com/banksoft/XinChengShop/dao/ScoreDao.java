package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.ScoreProductData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by Robin on 2016/8/9.
 */
public class ScoreDao extends BaseDao {
    public ScoreDao(Context mContext) {
        super(mContext);
    }

    /**
     * 获取积分订单详情
     * @param id
     * @return
     */
    public ScoreProductData getScoreProductData(String id) {
        String url = ControlUrl.SCORE_INFO_URL;
        String params = "id=" + id;
        ScoreProductData data = (ScoreProductData) postHttpRequest(mContext, url, params, JSONHelper.SCORE_INFO_DATA, true);
        return data;
    }

    ;
}
