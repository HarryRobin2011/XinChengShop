package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by Robin on 2016/8/29.
 */
public class TextDao extends BaseDao{
    public TextDao(Context mContext) {
        super(mContext);
    }

    public IsFlagData textaa(String memberId){
        String url = ControlUrl.DISPATCH_ORDER_LIST;
        String parasm = "memberId="+memberId+"&index=1"+"&size=20";
        IsFlagData isFlagData = (IsFlagData) postHttpRequest(mContext,url,parasm, JSONHelper.IS_FLAG_DATA,false);
        return isFlagData;
    }
}
