package com.banksoft.XinChengShop.entity;

import com.banksoft.XinChengShop.entity.base.BaseEntity;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-3
 * Time: 下午1:20
 * To change this template use File | Settings | File Templates.
 */
public class OrderProductVO extends BaseEntity{
    private String id;      //订单商品id
    private String productId;       //商品的id
    private String productName;     //商品名称
    private String no;  //商品货号
    private float price;        //商品价格
    private int num;            //商品数量
    private float totalMoney;       //商品的总金额
    private String imageFile;       //商品的图片
    private String orderId;         //所属订单id
    private String signProductId;       //软件商品id

    private String inventoryChange;//库存变更模式的值

    private String standardIds;
    private String standardValues;
    private String standardKeys;
    private String standardNames;
    private String standardValNames;

    private String comboProductId;//组合销售活动id

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getComboProductId() {
        return comboProductId;
    }

    public void setComboProductId(String comboProductId) {
        this.comboProductId = comboProductId;
    }

    public String getStandardValNames() {
        return standardValNames;
    }

    public void setStandardValNames(String standardValNames) {
        this.standardValNames = standardValNames;
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

    public String getInventoryChange() {
        return inventoryChange;
    }

    public void setInventoryChange(String inventoryChange) {
        this.inventoryChange = inventoryChange;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getSignProductId() {
        return signProductId;
    }

    public void setSignProductId(String signProductId) {
        this.signProductId = signProductId;
    }
}
