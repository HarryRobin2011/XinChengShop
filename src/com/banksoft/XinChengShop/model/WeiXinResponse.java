package com.banksoft.XinChengShop.model;

import com.banksoft.XinChengShop.entity.base.BaseEntity;

/**
 * Created by Robin on 2016/4/25.
 * "appid":"wxb4ba3c02aa476ea1",
 * "noncestr":"ce74575bc710f11ccef74a1a03035a11",
 * "package":"Sign=WXPay",
 * "partnerid":"10000100",
 * "prepayid":"wx201604251108083a8bab0a750890824003",
 * "timestamp":"1461553688",
 * "sign":"562316BDB2AE8F483F1C85BFD95B9CA4"
 */
public class WeiXinResponse extends BaseEntity {
    private String appid;
    private String noncestr;
    private String packageValue;
    private String partnerid;
    private String prepayid;
    private String timestamp;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }
}
