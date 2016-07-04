package com.banksoft.XinChengShop.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Robin on 2014/9/14.
 */
public class Standard implements Serializable{
    private String id;
    private List<IdAndValue> list; //要么图片要么文字
    private String name;
    private String type;//image 图片 //text 文字

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<IdAndValue> getList() {
        return list;
    }

    public void setList(List<IdAndValue> list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
