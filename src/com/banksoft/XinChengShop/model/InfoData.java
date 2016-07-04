package com.banksoft.XinChengShop.model;

import com.banksoft.XinChengShop.entity.InfoValue;

import java.util.List;

/**
 * Created by Robin on 2016/5/17.
 */
public class InfoData {
    private int code;
    private List<InfoValue> info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<InfoValue> getInfo() {
        return info;
    }

    public void setInfo(List<InfoValue> info) {
        this.info = info;
    }
}
