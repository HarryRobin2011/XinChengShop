package com.banksoft.XinChengShop.entity;


import com.banksoft.XinChengShop.entity.base.BaseEntity;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-3
 * Time: 上午8:18
 * To change this template use File | Settings | File Templates.
 */
public class MemberAddress extends BaseEntity{
    private String id;
    private String memberId;
    private String no;
    private String address;
    private String telephone;
    private String mobile;
    private String postcode;
    private String userName;
    private boolean status;
    private long createTime; //创建时间

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
