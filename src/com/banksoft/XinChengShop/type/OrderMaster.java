package com.banksoft.XinChengShop.type;

/**
 * Created by harry_robin on 16/1/25.
 */
public enum  OrderMaster {

    SELLER("商家订单"),
    SALE("我的订单");
    String name;

    OrderMaster(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
