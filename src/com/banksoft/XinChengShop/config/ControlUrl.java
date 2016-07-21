package com.banksoft.XinChengShop.config;

/**
 * author: Robin
 * createTime: 2015/8/24.
 * desc:
 * version:
 */
public class ControlUrl {
    public static final String BASE_URL = "http://xinchengguangchang.com";
    //public static final String BASE_URL = "http://192.168.3.106";
    public static final String NEW_VERSION_DOWNLOAD_URL = "http://www.xinchengguangchang.com/XcShop.apk";
    public static final String LOGIN_URL = "/app/shopLogin";
    public static final String UPDATE_URL = "http://www.xinchengguangchang.com/updateFile.txt";
    public static final String REGITSTER_URL = "/app/_memberRegister";
    public static final String SEND_CHECK_CODE = "/front/smsUtil_mobileSend";
    ;
    public static final String SUBMIT_IMAGE_URL = "/app/_upload";

    public static final String XC_SHOP_PRODUCT_LIST_URL = "/app/searchProduct_list";// 产品列表


    public static final String XC_BANNER_URL = "/app/_advertisement";// 首页幻灯片
    public static final String XC_PRODUCT_TYPE_URL = "/app/productTypeList_list";//产品分类

    public static final String XC_SHOP_PRODUCT_INFO_URL = "/app/_searchProduct";
    public static final String XC_PRODUCT_STANDARD_URL = "/app/standard_findProductStandard";
    public static final String XC_SHOP_INFO_URL = "/app/shop_find";
    public static final String ORDER_LIST_URL = "/app/memberOrder_list";
    public static final String XC_SHOP_COLLECT_LIST_URL ="/app/shopCollection_list";//我收藏的店铺
    public static final String XC_PRODUCT_COLLECT_LIST_URL= "/app/productCollection_list";//收藏的商品
    public static final String XC_IS_CHECK_PRODUCT_URL = "/app/productCollection_check";//是否收藏了该商品
    public static final String XC_PRODUCT_ASSESS_FRONT_DTO_LIST_URL = "/app/_listProductAssess";// 产品评论列表
    public static final String XC_COLLECT_PRODUCT_URL = "/app/productCollection_save";// 收藏商品
    public static final String XC_COLLECT_SHOP_URL = "/app/shopCollection_save";//收藏店铺
    public static final String XC_SHOP_SCORE_PRODUCT_LIST_URL = "/app/scoreProduct_list";//积分产品列表
    public static final String XC_DELETE_COLLECT_PRODUCT_URL = "/app/productCollection_frontDelete";//详情页取消收藏
    public static final String SHIPPING_LIST_URL = "/app/memberAddress_list";//收货地址列表
    public static final String ADDRESS_SAVE = "/app/memberAddress_save";
    public static final String ADDRESS_DELETE = "/app/memberAddress_delete";
    public static final String ADDRESS_UPDATE = "/app/memberAddress_update";
    public static final String SUBMIT_ORDER_URL = "/app/_createMemberOrder";//创建订单
    public static final String XC_SHOP_LIST_URL = "/app/shop_searchList";// 店铺列表
    public static final String XC_POINT_SHOP_LIST_URL = "/app/shop_list";//根据坐标获取店铺信息

    public static final String XC_SHOP_LOCAL_TYPE_URL = "/app/shopLocalType_list ";//本地服务分类
    public static final String XC_SHOP_TYPE_LIST = "/app/shopType_list";// 本地商城类店铺
    public static final String XC_SHOP_SERVER_TYPE_LIST = "/app/shopServerType_list";// 美食外卖服务


    public static final String EXPRESS_MODEL_FIND_URL = "/app/expressModule_find";// 查询店铺运费模版
    public static final String XC_MEMBER_INFO_URL = "/app/memberInfo";// 会员详情
    public static final String BALANCE_PAY_URL = "/app/_balancePay";//余额支付
    public static final String XC_ALIPAY_FIND_INFO_URL ="/app/payToAlipay_findInfo" ;//

    public static final String XC_ALIPAY_ORDER_URL = "/app/_payToAlipay";// 支付宝订单
    public static final String XC_SCORE_ORDER_LIST_URL = "/app/_scoreOrder";//积分订单列表
    public static final String XC_SHOP_PRODUCT_TYPE_URL = "/app/productTypeList_list";//产品分类列表
    public static final String XC_MEMBER_MONEY_LIST = "/app/memberMoney_list";//会员资金明细
    public static final String XC_WITH_DRAW_LIST_URL = "/app/withdraw_list";//提现列表

    public static final String  XC_SCORE_LIST_URL = "/app/memberScore";// 会员积分
    public static final String XC_SHOP_GOODS_LIST_URL = "/app/_shopProduct";//店铺商品列表
    public static final String XC_SHOP_GOODS_TYPE_URL = "/app/shopProductType_list";//店铺商品分类
    public static final String XC_SHOP_CHECK_CODE_URL = "/app/shopOrder_checkCode";//到店消费验证
    public static final String SHOP_SUBMIT_GOODS = "/app/shopProduct_save";//商品发布
    public static final String XC_ARTICLE_LIST_URL = "/app/_article";//通知公告
    public static final String DISPATCH_MEMBER_URL = "/app/dispatchMemberApply_find";//获取派单员信息
    public static final String APPLY_DISPATCH_URL = "/app/_dispatchMemberApply";//申请派单员
    public static final String XC_PAY_TO_WEIXIN_URL = "/app/_payToWeixin";//微信流水号
    public static final String UPDATE_MEMBER_INFO_URL = "/app/memberInfo_updateEmail";//修改电子邮箱
    public static final String XC_UPDATA_MEMBER_INFO_URL = "/app/memberInfo_edit";//修改会员资料

    public static String XC_COMMON_LIST_URL = "/app/memberAssess_list";// 评论列表

    public static final String XC_SHOP_PRODUCT_TYPE_LIST = "/app/shopProductType_list";// 店铺商品分类

    public static final String XC_SHOP_PRODUCT_URL = "/app/_shopProduct";//店铺商品列表

    public static final String THIRD_LOGIN = "/app/_thirdLogin";// 第三方登录

    public static final String THIRD_LOGIN_LOGIN = "/app/thirdLogin_login";//第三方账号关联

    public static final String SHOP_INFO_MESSAGE_FIND = "/app/shopApply_find";// 申请开店时 查询店铺信息


    public static final String SHOP_INFO_MESSAGE_OPEN_SHOP = "/app/_shopApply";// 会员申请开店



    public static final String APPLY_LEAFLET_URL = "/app/_dispatchMemberApply";//申请派单员

    public static final String DISPATCH_ORDER_LIST = "app/dispatchOrder_list";// 派单员抢单列表

    public static final String EXPRESS_BILL_ORDER_LIST_URL=  "/app/dispatchOrder_dispatchList";// 派单员订单列表

    public static final String DISPATCH_ORDER_URL = "app/dispatchOrder_dispatchOrder";//派单员抢单

    public static final String SHOP_EXPRESS_MODEL_URL = "/app/_shopExpress";//店铺运费模板

    public static final String UPDATE_MEMBER_PASSWORD_URL = "/app/memberInfo_updatePassword";// 修改登录密码

    public static final String UPDATE_MEMBER_PAY_PASSWORD_URL = "/app/memberInfo_updatePayPassword";//更新支付密码

    public static final String UPDATE_MEMBER_TELEPHONE_URL = "/app/memberInfo_step1";//修改手机号

    public static final String BIND_MEMBER_TELEPHONE_URL = "/app/memberInfo_step2";//绑定手机号

    public static final String XC_PRODUCT_TAKE_OFF_URL = "/app/shopProduct_outStock";//产品下架

    public static final String XC_PRODUCT_TAKE_ON_URL = "/app/shopProduct_inStock";//产品上架


    public static final String XC_ORDER_CANCEL_URL = "/app/memberOrder_cancelOrder";//取消订单

    public static final String XC_ORDER_CONFIRM_GOODS_URL = "/app/memberOrder_overOrder";//确认收货

    public static final String XC_ORDER_COMMENT_URL = "/app/memberOrder_assess";//订单评论

    public static final String XC_ORDER_UN_DISPATCH_RETURN_MONEY_URL = "/app/returnMoney_save";//已付款未发货退款

    public static final String XC_ORDER_DISPATCH_RETURN_MONEY_URL = "/app/returnProduct_saveMoney";//已发货退款

    public static final String XC_ORDER_RETURN_GOODS_URL = "/app/returnProduct_saveProduct";//已发货未收货申请退货

    public static final String XC_ORDER_BUYER_DISPATCH_URL = "/app/memberOrder_returnDispatch";//买家退货发货

    public static final String XC_EXPRESS_COMPANY_LIST_URL = "/app/_expressCompany";// 获取物流公司列表

    public static final String XC_ORDER_INFO_URL = "/app/memberOrder_find";//订单详情
}
