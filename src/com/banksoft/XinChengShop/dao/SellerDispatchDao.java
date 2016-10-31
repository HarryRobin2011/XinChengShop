package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by Robin on 2016/9/8.
 */
public class SellerDispatchDao extends BaseDao {

    public SellerDispatchDao(Context mContext) {
        super(mContext);
    }

    /**
     * 卖家发货
     */
    public IsFlagData sendOrder(String orderId,String companyKey, String expressNo,String dispatchPrice, String expressType) {
        String url = ControlUrl.XC_SELLER_ORDER_URL;
        String params = null;
        if(expressType.equals("1")){
             params = "id="+orderId+"&expressCompany="+companyKey+"&expressType="+expressType+"&expressNo="+expressNo;
        }else if(expressType.equals("0")){
            params = "id="+orderId+"&expressType="+expressType;
        }else if(expressType.equals("3")){
            params = "id="+orderId+"&dispatchPrice="+dispatchPrice+"&expressType="+expressType;
        }

        IsFlagData data = (IsFlagData) postHttpRequest(mContext,url,params,JSONHelper.IS_FLAG_DATA,false);
        return data;
    }
}
