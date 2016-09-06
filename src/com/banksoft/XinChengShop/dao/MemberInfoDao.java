package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.entity.MemberInfo;
import com.banksoft.XinChengShop.model.ImageUrlData;
import com.banksoft.XinChengShop.model.MemberVOData;
import com.banksoft.XinChengShop.utils.JSONHelper;
import com.banksoft.XinChengShop.utils.LoadSignImage;

import java.io.File;

/**
 * Created by Harry on 2016-07-14.
 */
public class MemberInfoDao extends BaseDao{
    private LoadSignImage signImage;
    public MemberInfoDao(Context mContext) {
        super(mContext);
    }

    public MemberVOData updataMemberInfo(MemberInfo memberInfo){
        String url = ControlUrl.XC_UPDATA_MEMBER_UPDATE_URL;
        String params = getParams(memberInfo,"data");
        MemberVOData data = (MemberVOData) postHttpRequest(mContext,url,params, JSONHelper.XC_MEMBER_VO_DATA,false);
        return data;
    }

    /**
     * �ϴ�ͼƬ
     * @param imageFile
     * @param imageType
     * @return
     */
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
