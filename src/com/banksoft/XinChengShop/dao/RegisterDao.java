package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.entity.MemberBO;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by harry_robin on 15/11/12.
 */
public class RegisterDao extends BaseDao {
    public String mobilePhone;
    public String mobileCode;
    public RegisterDao(Context mContext) {
        super(mContext);
    }

    public IsFlagData register(MemberBO memberBO) {
        String url = ControlUrl.REGITSTER_URL;
        String params = getParams(memberBO,"data")+"&mobileCode="+mobileCode;
        IsFlagData data = (IsFlagData) postHttpRequest(mContext,url,params, JSONHelper.IS_FLAG_DATA,false);
        return data;
    }

    public BaseData<String> sendCheckCode(){
        String url = ControlUrl.SEND_CHECK_CODE;
        String params = "mobilePhone="+mobilePhone;
        BaseData<String> baseData = (BaseData<String>) postHttpRequest(mContext,url,params,JSONHelper.IS_FLAG_DATA,false);
        return baseData;
    }
}
