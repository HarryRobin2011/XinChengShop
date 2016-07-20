package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.base.BaseData;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/7/20.
 */
public class SelectDialogDao extends BaseDao{
    public SelectDialogDao(Context mContext) {
        super(mContext);
    }
    public BaseData getSelectData(String url, String params, Type type){
        BaseData data = (BaseData) postHttpRequest(mContext,url,params,type,true);
        return data;
    }
}
