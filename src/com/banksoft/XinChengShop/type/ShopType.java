package com.banksoft.XinChengShop.type;

/**
 * Created by harry_robin on 15/11/25.
 */
public enum  ShopType {
    SHOP_SERVER("美食外卖"),//订单已创建
    SHOP_LOCAL("本地服务"),//订单已支付
    SHOP_SALE("商城");
    private String name;

    ShopType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
