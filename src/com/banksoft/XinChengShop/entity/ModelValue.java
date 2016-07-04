package com.banksoft.XinChengShop.entity;

import java.io.Serializable;

/**
 * Created by Robin on 2014/9/14.
 */
public class ModelValue implements Serializable{
    private String name;
    private String value;

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
}
