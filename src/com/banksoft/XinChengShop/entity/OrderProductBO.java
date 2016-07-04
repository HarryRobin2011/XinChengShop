package com.banksoft.XinChengShop.entity;

import com.banksoft.XinChengShop.entity.base.BaseEntity;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-31
 * Time: 下午4:52
 * To change this template use File | Settings | File Templates.
 */
public class OrderProductBO extends BaseEntity{
    private String id;
    private String siteId;
    private String shopId;
    private String productId;
    private String productName;
    private float price;
    private int num;
    private String imageFile;
    private String standardIds;
    private String standardValues;
    private String standardKeys;
    private String standardNames;
    private String active;
    private String discountId;

    private String comboProductId;//组合销售活动id

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getComboProductId() {
        return comboProductId;
    }

    public void setComboProductId(String comboProductId) {
        this.comboProductId = comboProductId;
    }

    public String getStandardIds() {
        return standardIds;
    }

    public void setStandardIds(String standardIds) {
        this.standardIds = standardIds;
    }

    public String getStandardValues() {
        return standardValues;
    }

    public void setStandardValues(String standardValues) {
        this.standardValues = standardValues;
    }

    public String getStandardKeys() {
        return standardKeys;
    }

    public void setStandardKeys(String standardKeys) {
        this.standardKeys = standardKeys;
    }

    public String getStandardNames() {
        return standardNames;
    }

    public void setStandardNames(String standardNames) {
        this.standardNames = standardNames;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }
}
