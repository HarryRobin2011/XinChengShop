package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.entity.ListEntity;
import com.banksoft.XinChengShop.model.base.BaseData;

import java.lang.reflect.Type;

/**
 * Created by Robin on 2015/1/11.
 */
public class ListDao extends BaseDao {
    public String url;
    public String params;
    public Type type;

    public int index;
    public int size;

    public ListDao(Context mContext) {
        super(mContext);
    }

    public BaseData<ListEntity> getBaseListData(boolean cacheFlag){
        params +="&index="+index+"&size="+size;
        BaseData<ListEntity> data = (BaseData<ListEntity>) postHttpRequest(mContext,url,params,type,cacheFlag);
        return data;
    }

}
