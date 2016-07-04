/**
 * @(#)      2013/11/19 13:18
 *
 * 版权所有 (c) 北京银软网络技术有限公司
 * 北京市海淀区上地国际创业园西区1号
 * 保留所有权利
 */
package com.banksoft.XinChengShop.entity;

import java.io.Serializable;

/**
 * 时间设置
 * @author 王希辉
 * @version 1.0
 */
public class TimeSetting implements Serializable{

    private String id;
    private String shopId;  //店铺id
    private int startHour;  //开始时间 小时 0-23
    private int startMinute; //开始分钟  0-59

    private int endHour;  //结束时间 小时  0-23
    private int endMinute;  //结束分钟 0-59

    private boolean status;  //状态  开启、关闭

    private long createTime; //添加时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
