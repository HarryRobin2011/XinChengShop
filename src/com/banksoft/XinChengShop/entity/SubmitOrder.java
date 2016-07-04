package com.banksoft.XinChengShop.entity;

import com.banksoft.XinChengShop.entity.base.BaseEntity;

import java.util.List;

/**
 * Created by harry_robin on 15/12/1.
 * List: shoppingCartVO列表，
 memberId:下单的会员id
 shopId: 商品店铺id，多个用“|”分割
 expressPrice: 运费 ，多个用“|”分割
 addressId: 收货地址id
 expressType: 运费类型，多个用“|”分割

 Remark: 备注，多个用“|”分割
 Express:是否送货上门1送货上门 0 到店消费
 Telephone:到店消费时，需要填写手机号
 orderTime:long美食外卖、本地服务店铺类订单预定时间；具体到分钟
 orderTable:美食外卖、本地服务店铺类订单填写，桌位信息
 orderType:订单类型，SERVER, //美食外卖、本地服务 COMMON, //普通订单 GROUP,//团购
 */
public class SubmitOrder extends BaseEntity {
    private String memberId;
    private String shopId;
    private String expressPrice;
    private String addressId;
    private String expressType;
    private String remark;
    private String express;//是否送货上门
    private String telephone;
    private String orderTime;
    private String orderTable;
    private String shopType;
    private String orderType;

    private List<ShoppingCartVO> list;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getExpressPrice() {
        return expressPrice;
    }

    public void setExpressPrice(String expressPrice) {
        this.expressPrice = expressPrice;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getExpressType() {
        return expressType;
    }

    public void setExpressType(String expressType) {
        this.expressType = expressType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderTable() {
        return orderTable;
    }

    public void setOrderTable(String orderTable) {
        this.orderTable = orderTable;
    }

    public List<ShoppingCartVO> getList() {
        return list;
    }

    public void setList(List<ShoppingCartVO> list) {
        this.list = list;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
