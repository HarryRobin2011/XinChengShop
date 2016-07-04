package com.banksoft.XinChengShop.entity;

import com.banksoft.XinChengShop.entity.base.BaseEntity;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-18
 * Time: 下午2:22
 * To change this template use File | Settings | File Templates.
 */
public class ExpressPriceVO extends BaseEntity{
    private String type;
    private float price;
    private String name;

    public ExpressPriceVO(String type, float price, String name) {
        this.type = type;
        this.price = price;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
