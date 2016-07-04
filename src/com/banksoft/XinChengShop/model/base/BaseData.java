package com.banksoft.XinChengShop.model.base;

import java.io.Serializable;

/**
 * Created by Robin on 2015/1/11.
 */
public class BaseData<T> implements Serializable {
    public boolean success;
    public T data;
    public Object msg;
    public int code;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
