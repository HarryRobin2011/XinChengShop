package com.banksoft.XinChengShop.entity;

import com.banksoft.XinChengShop.entity.base.BaseEntity;

/**
 * Created by Harry on 2016-07-31.
 */
public class FoundPassword extends BaseEntity{
    private String accountId;
    private String mobilePhone;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
