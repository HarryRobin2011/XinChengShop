package com.banksoft.XinChengShop.entity;

import com.banksoft.XinChengShop.entity.base.BaseEntity;

/**
 * Created by Robin on 2016/7/28.
 */
public class MemberRateVO extends BaseEntity{
    private String id;
    private String setId;
    private float rate;
    private String name;//费率名称
    private String account;   //会员账号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSetId() {
        return setId;
    }

    public void setSetId(String setId) {
        this.setId = setId;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
