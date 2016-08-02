package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.utils.JSONHelper;
import com.banksoft.XinChengShop.utils.MD5Factory;

/**
 * Created by Harry on 2016-07-31.
 */
public class ResetDao extends BaseDao{
    public ResetDao(Context mContext) {
        super(mContext);
    }

    public BaseData<String> sendCheckCode(String mobilePhone){
        String url = ControlUrl.SEND_CHECK_CODE;
        String params = "mobilePhone="+mobilePhone;
        BaseData<String> baseData = (BaseData<String>) postHttpRequest(mContext,url,params, JSONHelper.IS_FLAG_DATA,false);
        return baseData;
    }

    public IsFlagData resetPassword(String id,String password,String telephone,String code) {
        String url = ControlUrl.XC_UPDATA_LOGIN_PASSWORD;
        String params = "id="+id+"&password="+ MD5Factory.encoding(password)+"&mobileCode="+code+"&telephone="+telephone;
        IsFlagData data = (IsFlagData) postHttpRequest(mContext,url,params,JSONHelper.IS_FLAG_DATA,false);
        return data;
    }
}
