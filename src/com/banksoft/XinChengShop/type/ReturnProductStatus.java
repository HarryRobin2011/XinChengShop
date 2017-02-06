package com.banksoft.XinChengShop.type;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-31
 * Time: 下午4:41
 * To change this template use File | Settings | File Templates.
 */
public enum ReturnProductStatus {
    APPLY_MONEY,            //申请退款
    APPLY_PRODUCT,           //申请退货
    APPLY_PART_MONEY,       //申请部分退款（不退货）
    SHOP_NOT_AGREE_MONEY,           //申请退款不同意
    SHOP_AGREE_MONEY,           //申请退款同意
    SHOP_NOT_AGREE_PRODUCT,           //申请退货不同意
    SHOP_NOT_PART_MONEY,        //申请部分退款不同意（不退货）
    SHOP_AGREE_PRODUCT,           //申请退货同意
    SHOP_AGREE_PART_MONEY,      //申请部分退款同意（不退货）
    AGREE_NO_DISPATCH,           //申请退货同意等待买家发货
    AGREE_DISPATCH,           //申请退货同意等待卖家收货
    AGREE_OVER           //申请退货同意卖家已收货
}
