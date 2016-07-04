package com.banksoft.XinChengShop.dao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.entity.ProductBO;
import com.banksoft.XinChengShop.model.ImageUrlData;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.utils.JSONHelper;
import com.banksoft.XinChengShop.utils.LoadSignImage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Robin on 2016/4/15.
 */
public class PublishGoodsDao extends BaseDao{
    public LoadSignImage signImage;
    public ProductBO productBO;
    public String shopId;
    public PublishGoodsDao(Context mContext) {
        super(mContext);
    }


    /**
     * 上传图片
     * @param imageFile
     * @param imageType
     * @return
     */
    public ImageUrlData submitImage(File imageFile,String imageType) {
        String url = ControlUrl.SUBMIT_IMAGE_URL;
        ImageUrlData imageData = null;
        String imageUrl = null;
        if (signImage == null) {
            signImage = new LoadSignImage();
        }
        signImage.setId(shopId);
        try {
            imageUrl =  signImage.loadImage(url, imageFile, imageType);
            imageData = JSONHelper.fromJSONObject(imageUrl,JSONHelper.IMAGE_URL_DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageData;
    }

    /**
     * 添加商品
     */
    public IsFlagData submitGoods(ProductBO productBO){
        String url = ControlUrl.SHOP_SUBMIT_GOODS;
        String params = getParams(productBO,"data");
        IsFlagData data = (IsFlagData) postHttpRequest(mContext,url,params, JSONHelper.IS_FLAG_DATA,false);
        return data;
    }


}
