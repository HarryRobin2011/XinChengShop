package com.banksoft.XinChengShop.model;

import com.banksoft.XinChengShop.entity.ExpressPriceVO;
import com.banksoft.XinChengShop.entity.ProductCart;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by harry_robin on 15/11/19.
 */
public class ShopCartProductData implements Serializable {
    private HashMap<String,ProductCart> productVOHashMap;//key 为 产品ID ｜ Standard ID
    private String shopId;
    private String shopType;
    private String shopName;
    private String areaNo;
    private float total;
    private String remark;
    private boolean isFree;// 是否免运费
    private ExpressPriceVO expressPriceVO;

    public HashMap<String, ProductCart> getProductVOHashMap() {
        return productVOHashMap;
    }

    public void setProductVOHashMap(HashMap<String, ProductCart> productVOHashMap) {
        this.productVOHashMap = productVOHashMap;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }

    public ExpressPriceVO getExpressPriceVO() {
        return expressPriceVO;
    }

    public void setExpressPriceVO(ExpressPriceVO expressPriceVO) {
        this.expressPriceVO = expressPriceVO;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setIsFree(boolean isFree) {
        this.isFree = isFree;
    }
}
