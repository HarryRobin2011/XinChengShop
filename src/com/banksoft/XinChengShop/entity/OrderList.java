package com.banksoft.XinChengShop.entity;

import com.banksoft.XinChengShop.entity.base.BaseEntity;

import java.util.LinkedList;

/**
 * Created by harry_robin on 15/12/1.
 */
public class OrderList extends BaseEntity {
    private LinkedList<OrderVO> orderList;

    public LinkedList<OrderVO> getOrderList() {
        return orderList;
    }

    public void setOrderList(LinkedList<OrderVO> orderList) {
        this.orderList = orderList;
    }
}
