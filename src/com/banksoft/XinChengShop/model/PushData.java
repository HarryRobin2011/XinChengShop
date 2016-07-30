package com.banksoft.XinChengShop.model;

import com.banksoft.XinChengShop.type.PushType;

/**
 * Created by admin on 2016/7/18.
 */
public class PushData<T>{
    private T data;
    private PushType msgType;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PushType getMsgType() {
        return msgType;
    }

    public void setMsgType(PushType msgType) {
        this.msgType = msgType;
    }
}
