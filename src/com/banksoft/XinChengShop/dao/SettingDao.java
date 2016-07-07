package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.entity.Member;
import com.banksoft.XinChengShop.entity.MemberVO;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.model.MemberInfoData;
import com.banksoft.XinChengShop.model.MemberVOData;
import com.banksoft.XinChengShop.utils.JSONHelper;
import com.banksoft.XinChengShop.utils.MD5Factory;

import static android.R.attr.id;

/**
 * Created by admin on 2016/7/6.
 */
public class SettingDao extends BaseDao{

    public SettingDao(Context mContext) {
        super(mContext);
    }

    /**
     * 修改登录密码
     * @param memberID
     * @param account
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public MemberVOData updateMemberLoginPassword(String memberID,String account,String oldPassword,String newPassword){
        String url = ControlUrl.UPDATE_MEMBER_PASSWORD_URL;
        String params = "memberId="+memberID+"&account="+account+"&password="+ MD5Factory.encoding(newPassword)+"&oldPassword="+MD5Factory.encoding(oldPassword);
        MemberVOData data = (MemberVOData) postHttpRequest(mContext,url,params, JSONHelper.XC_MEMBER_VO_DATA,false);
        return data;
    }

    /**
     * 修改支付密码
     * @param memberID
     * @param account
     * @param oldPassword
     * @param newPassword
     * @return
     */
    public MemberVOData updateMemberPayPassword(String memberID,String account,String oldPassword,String newPassword){
        String url = ControlUrl.UPDATE_MEMBER_PAY_PASSWORD_URL;
        String params = "memberId="+memberID+"&account="+account+"&password="+ MD5Factory.encoding(newPassword)+"&oldPassword="+MD5Factory.encoding(oldPassword);
        MemberVOData data = (MemberVOData) postHttpRequest(mContext,url,params, JSONHelper.XC_MEMBER_VO_DATA,false);
        return data;
    }

    /**
     * 更新手机号
     * @param id
     * @param checkCode
     * @return
     */
    public IsFlagData updateMemberTelephone(String id, String checkCode) {
        String url = ControlUrl.UPDATE_MEMBER_TELEPHONE_URL;
        String params = "memberId="+id+"&code="+checkCode;
        IsFlagData data = (IsFlagData) postHttpRequest(mContext,url,params, JSONHelper.IS_FLAG_DATA,false);
        return data;
    }

    /**
     * 绑定手机号
     * @param id
     * @param telephoneStr
     * @param checkCode
     * @return
     */
    public IsFlagData bindTelephone(String id, String telephoneStr,String checkCode) {
        String url = ControlUrl.BIND_MEMBER_TELEPHONE_URL;
        String params = "memberId="+id+"&code="+checkCode+"&telephone="+ telephoneStr;
        IsFlagData data = (IsFlagData) postHttpRequest(mContext,url,params, JSONHelper.IS_FLAG_DATA,false);
        return data;

    }

    public MemberInfoData updateMemberInfoEmail(String id,String checkCode,String email){
        String url = ControlUrl.UPDATE_MEMBER_INFO_URL;
        String params = "memberId="+id+"&code="+checkCode+"&email="+ email;
        MemberInfoData data = (MemberInfoData) postHttpRequest(mContext,url,params, JSONHelper.XC_MEMBER_INFO_DATA,false);
        return data;
    }
}
