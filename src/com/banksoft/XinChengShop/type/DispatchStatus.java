package com.banksoft.XinChengShop.type;

/**
 * Created by Robin on 2016/9/7.
 */
public enum  DispatchStatus {
    CREATE("未派送"), //允许抢单
    READY("已被抢单"), //已被抢单
    OVER("派送完成"),//派送完成
    DISPATCH("派送中"); //派送中
    String name;
    DispatchStatus(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
