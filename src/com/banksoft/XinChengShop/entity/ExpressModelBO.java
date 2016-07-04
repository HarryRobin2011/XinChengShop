package com.banksoft.XinChengShop.entity;

import com.banksoft.XinChengShop.entity.base.BaseEntity;

import java.util.List;

/**
 * Created by Robin on 2016/5/5.
 */
public class ExpressModelBO extends BaseEntity {
    private String id;
    private String shopId;
    private String name;
    private List<ExpressTypeModelBO> list;
    private String lastEditTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExpressTypeModelBO> getList() {
        return list;
    }

    public void setList(List<ExpressTypeModelBO> list) {
        this.list = list;
    }

    public String getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(String lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}
