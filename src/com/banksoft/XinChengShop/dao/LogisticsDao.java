package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.ExpressCompanyCellListData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by Robin on 2016/9/8.
 */
public class LogisticsDao extends BaseDao{
    public LogisticsDao(Context mContext) {
        super(mContext);
    }

    /**
     * 获取物流公司列表
     * @return
     */
    public ExpressCompanyCellListData getExpressCompanyListData(){
        String url = ControlUrl.XC_EXPRESS_COMPANY_LIST_URL;
        String params = "";
        ExpressCompanyCellListData data = (ExpressCompanyCellListData) postHttpRequest(mContext, url, params, JSONHelper.EXPRESS_COMPANY_CELL_LIST_DATA, false);
        return data;
    }
}
