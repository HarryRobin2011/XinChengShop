package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.entity.OrderAssessBO;
import com.banksoft.XinChengShop.entity.ReturnProduct;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.utils.JSONHelper;

import java.util.List;

/**
 * Created by admin on 2016/7/11.
 */
public class PublishCommentDao extends BaseDao{

    public PublishCommentDao(Context mContext) {
        super(mContext);
    }

    /**
     * 提交订单评论
     * @param id
     * @param returnProducts
     * @param orderAssessBO
     * @return
     */
    public IsFlagData submitComment(String id, List<ReturnProduct> returnProducts, OrderAssessBO orderAssessBO) {
        String url = ControlUrl.XC_ORDER_COMMENT_URL;
        String params = "memberId="+id+"&productData="+getListParams(returnProducts,"list")+"&productAssessBO="+getParams(orderAssessBO,"data");
        IsFlagData isFlagData = (IsFlagData) postHttpRequest(mContext,url,params, JSONHelper.IS_FLAG_DATA,false);
        return isFlagData;
    }
}
