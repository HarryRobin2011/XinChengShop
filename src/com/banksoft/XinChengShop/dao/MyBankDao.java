package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.entity.BalanceRecord;
import com.banksoft.XinChengShop.entity.Bank;
import com.banksoft.XinChengShop.model.BankData;
import com.banksoft.XinChengShop.model.BankListData;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.model.MemberVOData;
import com.banksoft.XinChengShop.ui.MemberRateVOListData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by Robin on 2016/7/26.
 */
public class MyBankDao extends BaseDao {

    public MyBankDao(Context mContext) {
        super(mContext);
    }

    /**
     * 我的银行卡列表
     *
     * @param id
     * @return
     */
    public BankListData getBankListData(String id) {
        String url = ControlUrl.XC_MY_BANK_LIST_URL;
        String params = "memberId=" + id;
        BankListData data = (BankListData) postHttpRequest(mContext, url, params, JSONHelper.BANK_LIST_DATA, false);
        return data;
    }

    /**
     * 保存银行卡
     *
     * @param bank
     * @return
     */
    public IsFlagData saveBankData(Bank bank) {
        String url = ControlUrl.XC_BANK_SAVE_URL;
        String params = getParams(bank, "data");
        IsFlagData data = (IsFlagData) postHttpRequest(mContext, url, params, JSONHelper.IS_FLAG_DATA, false);
        return data;
    }

    /**
     * 删除银行卡信息
     *
     * @param id
     * @return
     */
    public IsFlagData deleteBankData(String id) {
        String url = ControlUrl.XC_BANK_DELETE_URL;
        String params = "id=" + id;
        IsFlagData data = (IsFlagData) postHttpRequest(mContext, url, params, JSONHelper.IS_FLAG_DATA, true);
        return data;
    }

    /**
     * 查询银行卡
     * @param id
     * @return
     */
    public BankData getBankData(String id) {
        String url = ControlUrl.XC_BANNER_FIND_URL;
        String params = "id=" + id;
        BankData data = (BankData) postHttpRequest(mContext, url, params, JSONHelper.IS_FLAG_DATA, true);
        return data;
    }

    /**
     * 更新银行卡
     * @param bank
     * @return
     */
    public IsFlagData updateBankData(Bank bank){
        String url = ControlUrl.XC_BANK_UPDATE_URL;
        String params = getParams(bank,"data");
        IsFlagData data = (IsFlagData) postHttpRequest(mContext,url,params,JSONHelper.IS_FLAG_DATA,true);
        return data;
    }

    /**
     * 申请体现
     * @return
     */
    public MemberVOData applyWithDraw(String memberId, BalanceRecord bankListData, String password){
         String url = ControlUrl.XC_APPLY_WITH_DRAW;
         String params = "password="+password+"&memberId="+memberId+"&data="+getParams(bankListData,"data");
        MemberVOData data = (MemberVOData) postHttpRequest(mContext,url,params,JSONHelper.XC_MEMBER_VO_DATA,false);
        return data;
    }

    /**
     * 提现费率列表
     */
    public MemberRateVOListData getMemberRateVOListData(String id){
        String url = ControlUrl.XC_MEMBER_RATE_LIST;
        String params = "memberId="+id;
        MemberRateVOListData data = (MemberRateVOListData) postHttpRequest(mContext,url,params,JSONHelper.XC_MEMBER_RATE_LIST_DATA,true);
        return data;
    }

    /**
     * 获取会员详情
     * @param memberId
     * @return
     */
    public MemberVOData getMemberVOData(String memberId){
        String url = ControlUrl.XC_MEMBER_INFO_URL;
        String params = "memberId="+memberId;
        MemberVOData data = (MemberVOData) postHttpRequest(mContext,url,params,JSONHelper.XC_MEMBER_VO_DATA,false);
        return data;
    }


}
