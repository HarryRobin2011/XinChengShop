package com.banksoft.XinChengShop.entity;

/**
 * 派单员实名认证
 * Created by Administrator on 15-9-22.
 */
public class DispatchMember {

    private String id; //实名认证ID，同memberId
    private String siteId;
    private String name; //实名
    private String idCard; //身份证号
    private String telephone; //手机号码
    private String imgFile; //头像
    private String cardImg; //身份证照片1
    private String cardImg2; //身份证照片2

    private long createTime; //申请时间
    private boolean status; //状态  true审核通过，false待审核，不可接单

    private String deposit; //保证金额
    private String reason;//申请理由

    private String no;//bianhao

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getImgFile() {
        return imgFile;
    }

    public void setImgFile(String imgFile) {
        this.imgFile = imgFile;
    }

    public String getCardImg() {
        return cardImg;
    }

    public void setCardImg(String cardImg) {
        this.cardImg = cardImg;
    }

    public String getCardImg2() {
        return cardImg2;
    }

    public void setCardImg2(String cardImg2) {
        this.cardImg2 = cardImg2;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
