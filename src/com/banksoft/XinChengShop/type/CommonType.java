package com.banksoft.XinChengShop.type;

/**
 * Created by harry_robin on 16/1/24.
 */
public enum CommonType {
    replySelf("给我的评论"),//
    assess("我的评论");//

    private String name;

    CommonType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
