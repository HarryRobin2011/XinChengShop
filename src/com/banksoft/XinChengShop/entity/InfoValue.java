package com.banksoft.XinChengShop.entity;

import java.util.List;

/**
 * Created by Robin on 2016/5/17.
 */
public class InfoValue {
    private String name;
    private String value;
    private List<InfoValue> childList;
    private boolean isLeaf;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<InfoValue> getChildList() {
        return childList;
    }

    public void setChildList(List<InfoValue> childList) {
        this.childList = childList;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }
}
