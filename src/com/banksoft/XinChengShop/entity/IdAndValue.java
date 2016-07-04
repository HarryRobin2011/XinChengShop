package com.banksoft.XinChengShop.entity;

import java.io.Serializable;

/**
 * Created by Robin on 2014/9/14.
 */
public class IdAndValue implements Serializable {
    private String id;
    private String value;
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
