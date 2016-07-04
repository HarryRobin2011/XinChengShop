package com.banksoft.XinChengShop.dao;

import android.content.Context;
import com.banksoft.XinChengShop.config.ControlUrl;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.model.AdvertisementData;
import com.banksoft.XinChengShop.model.ProductTypeData;
import com.banksoft.XinChengShop.model.ShopListData;
import com.banksoft.XinChengShop.model.ShopProductListData;
import com.banksoft.XinChengShop.utils.JSONHelper;

/**
 * Created by harry_robin on 15/11/14.
 */
public class HomeDao extends BaseDao {

    public HomeDao(Context mContext) {
        super(mContext);
    }

    /**
     * 获取手机BANNER
     *
     * @param cacheFlag
     * @return
     */
    public AdvertisementData getAdvertisementData(boolean cacheFlag) {
        String url = ControlUrl.XC_BANNER_URL;
        String params = "no=app-1";
        AdvertisementData data = (AdvertisementData) postHttpRequest(mContext, url, params, JSONHelper.ADVERTISEMENTDATA, cacheFlag);
        return data;
    }

    /**
     * 获取清仓产品
     */
    public ShopProductListData getShopProductListData(boolean cacheFlag, int productType, int size, int index) {
        String url = ControlUrl.XC_SHOP_PRODUCT_LIST_URL;
        ;
        String params = "active=" + productType + "&size=" + size + "&index=" + index;
        ShopProductListData data = (ShopProductListData) postHttpRequest(mContext, url, params, JSONHelper.SHOP_LIST_PRODUCT_DATA, cacheFlag);
        return data;
    }

    /**
     * 获取美食外卖店铺
     * @param lng
     * @param lat
     * @param radius
     * @param index
     * @param shopType
     * @param cacheFlag
     * @return
     */
    public ShopListData getShopListData(String lng,String lat,String radius,int index,String shopType,boolean cacheFlag){
        String url = ControlUrl.XC_POINT_SHOP_LIST_URL;
        String params = "lng="+lng+"&lat="+lat+"&r="+radius+"&index="+index+"&size=20"+"&shopServerType="+shopType;
        ShopListData shopListData = (ShopListData) postHttpRequest(mContext,url,params,JSONHelper.SHOP_LIST_DATA,cacheFlag);
        return shopListData;
    }

    /**
     * 获取清仓产品
     */
    public ShopProductListData getClearanceSaleShopProductListData(boolean cacheFlag, int productType, int size, int index) {
        String url = ControlUrl.XC_SHOP_PRODUCT_LIST_URL;
        ;
        String params = "active=" + productType + "&size=" + size + "&index=" + index;
        ShopProductListData data = (ShopProductListData) postHttpRequest(mContext, url, params, JSONHelper.SHOP_LIST_PRODUCT_DATA, cacheFlag);
        return data;
    }

    /**
     * 获取产品分类
     * @param size
     * @param index
     * @param cacheFlag
     * @return
     */
    public ProductTypeData getProductTypeListData(int size,int index,boolean cacheFlag){
        String url = ControlUrl.XC_SHOP_PRODUCT_TYPE_URL;
        String params = "index="+index+"&size="+size+"&grade=1";
        ProductTypeData productTypeData = (ProductTypeData) postHttpRequest(mContext,url,params,JSONHelper.XC_SHOP_PRODUCT_TYPE_DATA,cacheFlag);
        return productTypeData;
    }



}
