package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.utils.JSONHelper;
import com.banksoft.XinChengShop.utils.MD5Factory;

/**
 * Created by harry_robin on 15/12/12.
 */
public class CheckPasswordDao extends BaseDao{
    public CheckPasswordDao(Context mContext) {
        super(mContext);
    }

    public BaseData balancePay(String memberId,String orderIds,String password){
        String url = ControlUrl.BALANCE_PAY_URL;
        String params = "memberId="+memberId+"&ids="+orderIds+"&password="+ MD5Factory.encoding(password);
        BaseData data = (BaseData) postHttpRequest(mContext,url,params, JSONHelper.IS_FLAG_DATA,false);
        return data;
    }
}
