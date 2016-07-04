package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.ProductAssessFrontDTOListData;
import com.banksoft.XinChengShop.model.ProductStandardData;
import com.banksoft.XinChengShop.model.ShopInfoData;
import com.banksoft.XinChengShop.model.ShopProductInfoData;
import com.banksoft.XinChengShop.model.base.BaseData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by harry_robin on 15/11/17.
 */
public class ShopProductInfoDao extends BaseDao {
    public ShopProductInfoDao(Context mContext) {
        super(mContext);
    }

    /**
     * 获取产品详情
     *
     * @param id
     * @param cacheFlag
     * @return
     */
    public ShopProductInfoData getShopProductInfo(String id, boolean cacheFlag) {
        String url = ControlUrl.XC_SHOP_PRODUCT_INFO_URL;
        String params = "id=" + id;
        ShopProductInfoData data = (ShopProductInfoData) postHttpRequest(mContext, url, params, JSONHelper.SHOP_INFO_PRODUCT_DATA, cacheFlag);
        return data;
    }

    /**
     * 获取商品规格
     *
     * @param id
     * @param cacheFlag
     * @return
     */
    public ProductStandardData getProductStandardData(String id, boolean cacheFlag) {
        String url = ControlUrl.XC_PRODUCT_STANDARD_URL;
        String params = "productId=" + id;
        ProductStandardData data = (ProductStandardData) postHttpRequest(mContext, url, params, JSONHelper.PRODUCT_STANDARD_DATA, cacheFlag);
        return data;
    }


    /**
     * 获取店铺详情
     * @param shopID
     * @param cacheFlag
     * @return
     */
    public ShopInfoData getShopInfoData(String shopID,boolean cacheFlag){
        String url = ControlUrl.XC_SHOP_INFO_URL;
        String params = "id="+shopID;
        ShopInfoData data = (ShopInfoData) postHttpRequest(mContext,url,params, JSONHelper.SHOP_INFO_DATA,cacheFlag);
        return data;
    }
    /**
     * 商品是否收藏
     * 会员ID 商品ID
     *
     * @param memberId
     * @param productId
     * @return
     */
    public BaseData isCheckCollect(String memberId, String productId) {
        String url = ControlUrl.XC_IS_CHECK_PRODUCT_URL;
        String params = "memberId=" + memberId + "&productId=" + productId;
        BaseData data = (BaseData) postHttpRequest(mContext, url, params, JSONHelper.IS_FLAG_DATA, false);
        return data;
    }

    /**
     * 获取商品评论
     * @param productId
     * @param cacheFlag
     * @return
     */
    public ProductAssessFrontDTOListData getProductAssessFrontDTOListData(String productId, boolean cacheFlag) {
        String url = ControlUrl.XC_PRODUCT_ASSESS_FRONT_DTO_LIST_URL;
        String params = "productId=" + productId + "&idnex=1&size=1";
        ProductAssessFrontDTOListData data = (ProductAssessFrontDTOListData) postHttpRequest(mContext, url, params, JSONHelper.PRODUCT_ASSETS_FRONT_DTO_LIST_DATA, cacheFlag);
        return data;
    }

    /**
     * 收藏商品
     * @param productId
     * @return
     */
    public BaseData collectProduct(String memberId,String productId) {
        String url = ControlUrl.XC_COLLECT_PRODUCT_URL;
        String params = "memberId="+memberId+"&productId="+productId;
        BaseData data = (BaseData) postHttpRequest(mContext,url,params,JSONHelper.IS_FLAG_DATA,false);
        return data;
    }

    /**
     * 取消收藏
     * @param productId
     * @return
     */
    public BaseData cancelCollectProduct(String memberId,String productId) {
        String url = ControlUrl.XC_DELETE_COLLECT_PRODUCT_URL;
        String params = "memberId="+memberId+"&productId="+productId;
        BaseData data = (BaseData) postHttpRequest(mContext,url,params,JSONHelper.IS_FLAG_DATA,false);
        return data;
    }
}
