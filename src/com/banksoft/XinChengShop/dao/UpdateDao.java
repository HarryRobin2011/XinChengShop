package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.UpdateData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by Robin on 2015/1/23.
 */
public class UpdateDao extends BaseDao {

    public UpdateDao(Context mContext) {
        super(mContext);
    }

    public UpdateData getUpdateData(){
        UpdateData data = (UpdateData) postHttpUrl(ControlUrl.UPDATE_URL, JSONHelper.UPDATE_DATA);
        return data;
    }

}
