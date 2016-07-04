package com.banksoft.XinChengShop.type;

/**
 * Created by harry_robin on 15/11/20.
 */
public enum OfSizeType {

    STANDARD("颜色"),
    MODELVALUE("尺码");
    String name;

     OfSizeType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
