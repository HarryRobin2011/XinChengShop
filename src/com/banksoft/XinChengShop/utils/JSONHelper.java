package com.banksoft.XinChengShop.utils;


import com.banksoft.XinChengShop.entity.FoundPassword;
import com.banksoft.XinChengShop.model.*;
import com.banksoft.XinChengShop.ui.MemberRateVOListData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-4-24
 * Time: 上午9:54
 * To change this template use File | Settings | File Templates.
 */
public class JSONHelper {
    public final static Gson gson = new Gson();
    public static final Type MEMBER_DATA = new TypeToken<MemberData>(){}.getType();
    public static final Type UPDATE_DATA = new TypeToken<UpdateData>(){}.getType();
    public static final Type IS_FLAG_DATA = new TypeToken<IsFlagData>(){}.getType();
    public static final Type ADVERTISEMENTDATA = new TypeToken<AdvertisementData>(){}.getType();
    public static final Type SHOP_LIST_PRODUCT_DATA = new TypeToken<ShopProductListData>(){}.getType();
    public static final Type PRODUCT_TYPE_DATA = new TypeToken<ProductTypeData>(){}.getType();
    public static final Type SHOP_INFO_PRODUCT_DATA = new TypeToken<ShopProductInfoData>(){}.getType();
    public static final Type SHOP_CART_PRODUCT_DATA = new TypeToken<LinkedHashMap<String,ShopCartProductData>>(){}.getType();
    public static final Type PRODUCT_STANDARD_DATA = new TypeToken<ProductStandardData>(){}.getType();
    public static final Type SHOP_INFO_DATA = new TypeToken<ShopInfoData>(){}.getType();
    public static final Type ORDER_LIST_DATA = new TypeToken<OrderListData>(){}.getType();
    public static final Type SHOP_COLLECT_LIST_DATA = new TypeToken<ShopCollectionVOListData>(){}.getType();
    public static final Type COLLECT_PRODUCT_LIST_DATA = new TypeToken<ProductCollectionVOListData>(){}.getType();
    public static final Type PRODUCT_ASSETS_FRONT_DTO_LIST_DATA = new TypeToken<ProductAssessFrontDTOListData>(){}.getType();
    public static final Type XC_SHOP_SCORE_PRODUCT_LIST_DATAL = new TypeToken<ScoreProductListData>(){}.getType();
    public static final Type MEMBER_ADDRESS_DATA = new TypeToken<MemberAddressVOData>() {}.getType();
    public static final Type ORDER_VO_LIST_DATA = new TypeToken<OrderVOListData>(){}.getType();
    public static final Type SHOP_LIST_DATA = new TypeToken<ShopListData>(){}.getType();
    public static final Type XC_SHOP_LOCAL_TYPE_DATA = new TypeToken<ShopLocalTypeData>(){}.getType();
    public static final Type XC_SHOP_SERVER_TYPE_DATA = new TypeToken<ShopServerTypeData>(){}.getType();
    public static final Type XC_SHOP_TYPE_DATA = new TypeToken<ShopVoTypeData>(){}.getType();
    public static final Type EXPRESS_MODEL_DATA = new TypeToken<ExpressModelData>(){}.getType();
    public static final Type XC_MEMBER_INFO_DATA = new TypeToken<MemberInfoData>(){}.getType();
    public static final Type XC_MEMBER_DATA = new TypeToken<MemberData>() {}.getType();
    public static final Type XC_MEMBER_VO_DATA = new TypeToken<MemberVOData>(){}.getType();
    public static final Type ALIPAY_INFO_DATA = new TypeToken<AlipayInfoData>(){}.getType();
    public static final Type ALIPAY_ORDER_INFO_DATA = new TypeToken<AlipayOrderInfoData>() {}.getType();
    public static final Type SCORE_ORDER_LIST_DATA = new TypeToken<ScoreOrderListData>(){}.getType();
    public static final Type XC_COMMON_LIST_DATA = new TypeToken<ProductListAssessData>(){}.getType();
    public static final Type XC_SHOP_PRODUCT_TYPE_DATA = new TypeToken<ProductTypeData>(){}.getType();
    public static final Type XC_SHOP_PRODUCT_TYPE_PRODUCT_TYPE = new TypeToken<ShopProductTypeBOData>(){}.getType();
    public static final Type XC_SHOP_PRODUCT_PRODUCT_DATA = new TypeToken<ShopProductListData>() {}.getType();
    public static final Type BALANCE_CHANGE_RECORD_DATA = new TypeToken<BalanceChangeRecordData>(){}.getType();
    public static final Type XC_SCORE_LIST_DATA = new TypeToken<ScoreRecordVOListData>(){}.getType();// 会员积分列表
    public static final Type XC_ARTICLE_LIST_DATA = new TypeToken<ArticleListVOListData>() {}.getType();// 通知公告
    public static final Type DISPATCH_MEMBER_DATA = new TypeToken<DispatchMemberData>(){}.getType();// 派单员信息
    public static final Type IMAGE_URL_DATA = new TypeToken<ImageUrlData>(){}.getType();//图片Uri
    public static final Type XC_PRODUCT_ASSESS_FRONT_DTO_LIST_DATA = new TypeToken<ProductAssessFrontDTOListData>(){}.getType();
    public static final Type WEI_XIN_RESPONSE = new TypeToken<PayReqData>(){}.getType();
    public static final Type EXPRESS_SHOP_MODEL_DATA = new TypeToken<ExpressModelBOData>(){}.getType();
    public static final Type INFO_HASH_MAP = new TypeToken<InfoHashMap>(){}.getType();
    public static final Type SHOP_PRODUCT_TYPE_DATA = new TypeToken<ShopProductTypeBOData>(){}.getType();
    public static final Type EXPRESS_COMPANY_CELL_LIST_DATA = new TypeToken<ExpressCompanyCellListData>(){}.getType();
    public static final Type PUSH_DATA = new TypeToken<PushData>(){}.getType();
    public static final Type XC_ORDER_INFO_DATA = new TypeToken<OrderInfoData>() {}.getType();
    public static final Type BANK_LIST_DATA = new TypeToken<BankListData>(){}.getType();
    public static final Type XC_MEMBER_RATE_LIST_DATA = new TypeToken<MemberRateVOListData>() {}.getType();
    public static final Type PUSH_ORDER_DATA = new TypeToken<PushOrderData>(){}.getType();
    public static final Type FOUND_PASSWORD = new TypeToken<FoundPassword>(){}.getType();


    public static String toJSONString(Object o){
       return gson.toJson(o);
    }

    public static <T> T fromJSONObject(String json,Type type){
      return gson.fromJson(json,type);
    }

    public static <T> T fromJSONObject(String json, Class<T> cls){
      return gson.fromJson(json,cls);
    }


}
