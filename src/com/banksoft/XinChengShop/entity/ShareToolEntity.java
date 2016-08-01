package com.banksoft.XinChengShop.entity;

import java.io.Serializable;

/**
 * Created by Robin on 2016/7/19.
 */
public class ShareToolEntity implements Serializable {
    private String name;
    private int resId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
