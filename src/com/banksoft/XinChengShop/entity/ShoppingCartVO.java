package com.banksoft.XinChengShop.entity;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-30
 * Time: 上午9:42
 * To change this template use File | Settings | File Templates.
 */
public class ShoppingCartVO {
    private String id;
    private String productId;
    private String shopId;
    private String shopNo;
    private String productName;
    private float price;
    private int goodsNum;
    private String imageFile;
    private float total;

    private String cardId;

    private String active; //商品类型 0  1 清仓 2 团购

    private String standardIds;//规格ID
    private String standardValues;//规格名字
    private String standardKeys;//属性ID
    private String standardNames;//属性名字
    private String standardValNames;



    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
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

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getStandardValNames() {
        return standardValNames;
    }

    public void setStandardValNames(String standardValNames) {
        this.standardValNames = standardValNames;
    }
}
