package com.banksoft.XinChengShop.model;

import com.banksoft.XinChengShop.type.PushType;

/**
 * Created by admin on 2016/7/18.
 */
public class PushData<T>{
    private T data;
    private PushType messageType;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PushType getMessageType() {
        return messageType;
    }

    public void setMessageType(PushType messageType) {
        this.messageType = messageType;
    }
}
