package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.adapter.base.BaseMyAdapter;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.entity.OrderAssessBO;
import com.banksoft.XinChengShop.entity.ProductAssessBO;
import com.banksoft.XinChengShop.entity.ReturnProduct;
import com.banksoft.XinChengShop.model.ImageUrlData;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.utils.JSONHelper;
import com.banksoft.XinChengShop.utils.LoadSignImage;

import java.io.File;
import java.util.List;

/**
 * Created by admin on 2016/7/11.
 */
public class PublishCommentDao extends BaseDao{
    private LoadSignImage signImage;

    public PublishCommentDao(Context mContext) {
        super(mContext);
    }

    /**
     * 提交订单评论
     * @param id
     * @param productAssessBOs
     * @param orderAssessBO
     * @return
     */
    public IsFlagData submitComment(String id, List<ProductAssessBO> productAssessBOs, OrderAssessBO orderAssessBO) {
        String url = ControlUrl.XC_ORDER_COMMENT_URL;
        String params = "memberId="+id+"&"+getListParams(productAssessBOs,"productData")+"&orderData="+getParams(orderAssessBO,"orderData")+"";
        IsFlagData isFlagData = (IsFlagData) postHttpRequest(mContext,url,params, JSONHelper.IS_FLAG_DATA,false);
        return isFlagData;
    }

    public ImageUrlData submitImage(File imageFile, String imageType) {
        String url = ControlUrl.SUBMIT_IMAGE_URL;
        String imageUrl = null;
        ImageUrlData imageUrlData = null;
        if (signImage == null) {
            signImage = new LoadSignImage();
        }
        try {
            imageUrl =  signImage.loadImage(url, imageFile, imageType);
            imageUrlData = JSONHelper.fromJSONObject(imageUrl,JSONHelper.IMAGE_URL_DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageUrlData;
    }
}
