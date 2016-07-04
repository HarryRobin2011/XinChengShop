package com.banksoft.XinChengShop.entity;

import java.io.Serializable;

/**
 * Created by Robin on 2015/1/23.
 */
public class Update implements Serializable{
    private int versionCode;
    private String versionNumber;
    private String value;
    private boolean force; //是否强制更新

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }
}
