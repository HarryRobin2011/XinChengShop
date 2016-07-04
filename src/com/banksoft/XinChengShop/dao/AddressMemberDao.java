package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.entity.MemberAddressVO;
import com.banksoft.XinChengShop.model.MemberAddressVOData;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by harry_robin on 15/11/30.
 */
public class AddressMemberDao extends BaseDao {

    public AddressMemberDao(Context mContext) {
        super(mContext);
    }

    public MemberAddressVOData getMemberAddressVO(String memberId, boolean cacheFlag) {
        String url = ControlUrl.SHIPPING_LIST_URL;
        String params = "memberId=" + memberId;
        MemberAddressVOData data = (MemberAddressVOData) postHttpRequest(mContext, url, params, JSONHelper.MEMBER_ADDRESS_DATA, cacheFlag);
        return data;
    }

    /**
     * 添加收货地址
     * {success:false, code:1}
     * 1、地址不能为空，2、姓名空、3 电话或手机必填一个， 4 邮编空，5 地址数量最多5个
     *
     * @param memberAddress
     * @return
     */
    public BaseData saveMemberAddress(MemberAddressVO memberAddress) {
        String url = ControlUrl.ADDRESS_SAVE;
        String params = getParams(memberAddress, "data");
        BaseData baseData = (BaseData) postHttpRequest(mContext, url, params, JSONHelper.IS_FLAG_DATA, false);
        return baseData;
    }

    public BaseData deleteMemberAddress(String id) {
        String url = ControlUrl.ADDRESS_DELETE;
        String params = "id=" + id;
        BaseData BaseData = (BaseData) postHttpRequest(mContext, url, params, JSONHelper.IS_FLAG_DATA, false);
        return BaseData;
    }

    /**
     * 更新收货地址
     * {success:false, code:1}
     * 1、地址不能为空，2、姓名空、3 电话或手机必填一个， 4 邮编空，5 地址数量最多5个
     *
     * @param memberAddress
     * @return
     */
    public BaseData updateMemberAddress(MemberAddressVO memberAddress) {
        String url = ControlUrl.ADDRESS_UPDATE;
        String params = getParams(memberAddress, "data");
        BaseData baseData = (BaseData) postHttpRequest(mContext, url, params, JSONHelper.IS_FLAG_DATA, false);
        return baseData;
    }


}
