package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.entity.DispatchMember;
import com.banksoft.XinChengShop.model.ImageUrlData;
import com.banksoft.XinChengShop.model.IsFlagData;
import com.banksoft.XinChengShop.utils.JSONHelper;
import com.banksoft.XinChengShop.utils.LoadSignImage;

import java.io.File;

/**
 * Created by Robin on 2016/4/24.
 */
public class ApplyDispatchDao extends BaseDao{
    private LoadSignImage signImage;

    public ApplyDispatchDao(Context mContext) {
        super(mContext);
    }

    public IsFlagData applyDispatch(String id,DispatchMember dispatchMember){
        String url = ControlUrl.APPLY_DISPATCH_URL;
        String params = "id="+id+"&"+getParams(dispatchMember,"data");
        IsFlagData isFlagData = (IsFlagData) postHttpRequest(mContext,url,params, JSONHelper.IS_FLAG_DATA,true);
        return isFlagData;
    }

    /**
     * ÉÏ´«Í¼Æ¬
     * @param imageFile
     * @param imageType
     * @return
     */
    public ImageUrlData submitImage(File imageFile,String imageType) {
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
