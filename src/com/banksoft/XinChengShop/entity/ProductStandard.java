package com.banksoft.XinChengShop.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 颜色尺码
 * Created by Robin on 2014/9/14.
 */
public class ProductStandard implements Serializable {
     private List<Standard> standardsList;
     private List<ModelValue> modelValueList;
     private float maxPrice;
     private float minPrice;
     private List<PriceAndStock> priceStock;

    public List<Standard> getStandardsList() {
        return standardsList;
    }

    public void setStandardsList(List<Standard> standardsList) {
        this.standardsList = standardsList;
    }

    public List<ModelValue> getModelValueList() {
        return modelValueList;
    }

    public void setModelValueList(List<ModelValue> modelValueList) {
        this.modelValueList = modelValueList;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(float minPrice) {
        this.minPrice = minPrice;
    }

    public List<PriceAndStock> getPriceStock() {
        return priceStock;
    }

    public void setPriceStock(List<PriceAndStock> priceStock) {
        this.priceStock = priceStock;
    }
}
