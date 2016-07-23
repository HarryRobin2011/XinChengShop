package com.banksoft.XinChengShop.entity;

/**
 * Created with IntelliJ IDEA.
 * User: 刘志斌
 * Date: 13-11-20
 * Time: 上午10:57
 * 退款申请
 */
public class ReturnMoney {
    private String id;
    private String siteId;
    private String orderId;         //退款订单
    private String orderNo;         //要退款的单号
    private String status;          //退款状态（申请中，同意，不同意）
    private String returnType;            //退款原因
    private String no;
    private float orderTotal;       //订单总金额

    private String memberId;    //申请人
    private String memberAccount;      //申请人帐号
    private float memberMoney;         //申请退款金额
    private String memberReason;          //申请原因
    private long createTime;        //申请时间

    private String shopId;          //被申请人
    private String shopAccount;         //被申请人帐号
    private String shopName;
    private String shopReason;      //不同意申请原因
    private long backTime;          //被申请人处理时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }

    public float getMemberMoney() {
        return memberMoney;
    }

    public void setMemberMoney(float memberMoney) {
        this.memberMoney = memberMoney;
    }

    public String getMemberReason() {
        return memberReason;
    }

    public void setMemberReason(String memberReason) {
        this.memberReason = memberReason;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopAccount() {
        return shopAccount;
    }

    public void setShopAccount(String shopAccount) {
        this.shopAccount = shopAccount;
    }

    public String getShopReason() {
        return shopReason;
    }

    public void setShopReason(String shopReason) {
        this.shopReason = shopReason;
    }

    public long getBackTime() {
        return backTime;
    }

    public void setBackTime(long backTime) {
        this.backTime = backTime;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public float getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(float orderTotal) {
        this.orderTotal = orderTotal;
    }
}
