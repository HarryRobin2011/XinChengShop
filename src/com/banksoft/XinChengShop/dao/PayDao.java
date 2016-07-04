package com.banksoft.XinChengShop.dao;

import android.app.AlarmManager;
import android.content.Context;
import android.media.AudioManager;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.MemberVOData;
import com.banksoft.XinChengShop.model.PayReqData;
import com.banksoft.XinChengShop.model.WeiXinResponse;
import com.banksoft.XinChengShop.utils.JSONHelper;
import com.tencent.mm.sdk.modelpay.PayReq;

import java.sql.Time;
import java.util.Timer;

/**
 * Created by harry_robin on 15/12/12.
 */
public class PayDao extends BaseDao{
    private ThreadLocal threadLocal;

    public PayDao(Context mContext) {
        super(mContext);
    }

    public MemberVOData getMemberData(String memberId){
        String url = ControlUrl.XC_MEMBER_INFO_URL;
        String params = "memberId="+memberId;
        MemberVOData data = (MemberVOData) postHttpRequest(mContext,url,params, JSONHelper.XC_MEMBER_VO_DATA,false);
        return data;
    }

    public PayReqData getWeiXinResponse( String orderIds){
        String url = ControlUrl.XC_PAY_TO_WEIXIN_URL;
        String params ="ids="+orderIds;
        PayReqData payReqData = (PayReqData) postHttpRequest(mContext,url,params,JSONHelper.WEI_XIN_RESPONSE,false);
        return payReqData;
    }
}
