package com.banksoft.XinChengShop.entity;

import java.io.Serializable;

/**
 * Created by Robin on 2014/9/14.
 */
public class PriceAndStock implements Serializable {
    private String ids;
    private float price;
    private float stock;
    private boolean success;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getStock() {
        return stock;
    }

    public void setStock(float stock) {
        this.stock = stock;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
