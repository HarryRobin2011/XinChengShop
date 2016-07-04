package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.DispatchMemberData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by Robin on 2016/4/23.
 */
public class DispatchDao extends BaseDao{

    public DispatchDao(Context mContext) {
        super(mContext);
    }

    public DispatchMemberData getDispatchMemberData(String id,boolean cacheFlag){
        String url = ControlUrl.DISPATCH_MEMBER_URL;
        String params = "id="+id;
        DispatchMemberData dispatchMemberData = (DispatchMemberData) postHttpRequest(mContext,url,params, JSONHelper.DISPATCH_MEMBER_DATA,false);
        return dispatchMemberData;
    }
}
