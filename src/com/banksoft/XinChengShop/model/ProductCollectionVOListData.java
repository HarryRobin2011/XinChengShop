/**
 * @(#) ProductCollectionVO..java     2012/11/08 15:15
 *
 * 版权所有 (c) 北京银软网络技术有限公司
 * 北京市海淀区上地国际创业园西区1号
 * 保留所有权利
 */
package com.banksoft.XinChengShop.model;

import com.banksoft.XinChengShop.entity.ListEntity;
import com.banksoft.XinChengShop.entity.ProductCollectionVO;
import com.banksoft.XinChengShop.model.base.BaseData;

/**
 * 类的功能，目的，描述等写在此处
 *
 * @author 刘志斌
 * @version 1.0
 */

public class ProductCollectionVOListData extends BaseData<ListEntity<ProductCollectionVO>>{
    private String id;
    private String productName;//商品名称
    private String productImage;//商品图片
    private String productPrice;//收藏时商品价格
    private String productId;//商品ie
    private String shopId;//店铺id
    private String shopNo;//店铺编号
    private String shopName;//店铺名称
    private String shopAccount;//店铺账号
    private String shopQQ;//店铺QQ
    private String shopAli;//店铺旺旺
    private long createTime;//收藏时间
    private long collections;//收藏商品的人气数
    private long sales;//收藏商品的销量
    private long assesses;//收藏商品的评价数

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAccount() {
        return shopAccount;
    }

    public void setShopAccount(String shopAccount) {
        this.shopAccount = shopAccount;
    }

    public String getShopQQ() {
        return shopQQ;
    }

    public void setShopQQ(String shopQQ) {
        this.shopQQ = shopQQ;
    }

    public String getShopAli() {
        return shopAli;
    }

    public void setShopAli(String shopAli) {
        this.shopAli = shopAli;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getCollections() {
        return collections;
    }

    public void setCollections(long collections) {
        this.collections = collections;
    }

    public long getSales() {
        return sales;
    }

    public void setSales(long sales) {
        this.sales = sales;
    }

    public long getAssesses() {
        return assesses;
    }

    public void setAssesses(long assesses) {
        this.assesses = assesses;
    }
}
