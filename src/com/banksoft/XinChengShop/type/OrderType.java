package com.banksoft.XinChengShop.type;

/**
 * Created by harry_robin on 15/12/1.
 * 订单类型，SERVER, //美食外卖、本地服务 COMMON, //普通订单 GROUP,//团购
 */
public enum OrderType {
    SERVER("美食外卖/本地服务"),
    COMMON("商城订单"),
    GROUP("团购订单");
    String name;
    private  OrderType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
