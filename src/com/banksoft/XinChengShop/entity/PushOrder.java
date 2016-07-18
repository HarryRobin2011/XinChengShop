package com.banksoft.XinChengShop.entity;

import com.banksoft.XinChengShop.entity.base.BaseEntity;
import com.banksoft.XinChengShop.type.OrderStatus;
import com.banksoft.XinChengShop.type.PushOrderType;

/**

 orderId:订单ID，orderStatus:订单状态(orderStatus枚举)，type:推送对象标示 */
public class PushOrder extends BaseEntity {
    private String orderId;
    private OrderStatus orderStatus;
    private PushOrderType type;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PushOrderType getType() {
        return type;
    }

    public void setType(PushOrderType type) {
        this.type = type;
    }
}
