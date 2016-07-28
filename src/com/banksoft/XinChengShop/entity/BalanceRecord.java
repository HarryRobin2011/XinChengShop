package com.banksoft.XinChengShop.entity;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-9-24
 * Time: 下午1:59
 * To change this template use File | Settings | File Templates.
 * 存放用户余额变化的记录表
 */
public class BalanceRecord {
    private String id;
    private String no;
    private String siteId;
    private String memberId;
    private float money;
    private long createTime;
    private boolean status;    //是否支付成功
    private String payType;         //支付类型
    private String type;        //充值还是提现
    private String agreeStatus;      //提现是否同意
    private String remark;
    private String noAgreeRemark;   //不同意理由
    private String withdrawAccount;    //提现帐号(银行卡号)
    private String memberAccount;   //会员帐号
    private String withdrawName;        //开户人姓名
    private float withdrawRate;     //提现费率
    private float withdrawMoney;        //实际提现金额
    private String withdrawRateName;     //提现限制
    private String withdrawBank;        //所属银行
    private String withdrawSubBank;    //所属分行


    public String getWithdrawRateName() {
        return withdrawRateName;
    }

    public void setWithdrawRateName(String withdrawRateName) {
        this.withdrawRateName = withdrawRateName;
    }

    public float getWithdrawRate() {
        return withdrawRate;
    }

    public void setWithdrawRate(float withdrawRate) {
        this.withdrawRate = withdrawRate;
    }

    public float getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(float withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAgreeStatus() {
        return agreeStatus;
    }

    public void setAgreeStatus(String agreeStatus) {
        this.agreeStatus = agreeStatus;
    }

    public String getNoAgreeRemark() {
        return noAgreeRemark;
    }

    public void setNoAgreeRemark(String noAgreeRemark) {
        this.noAgreeRemark = noAgreeRemark;
    }

    public String getWithdrawAccount() {
        return withdrawAccount;
    }

    public void setWithdrawAccount(String withdrawAccount) {
        this.withdrawAccount = withdrawAccount;
    }

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }

    public String getWithdrawName() {
        return withdrawName;
    }

    public void setWithdrawName(String withdrawName) {
        this.withdrawName = withdrawName;
    }

    public String getWithdrawBank() {
        return withdrawBank;
    }

    public void setWithdrawBank(String withdrawBank) {
        this.withdrawBank = withdrawBank;
    }

    public String getWithdrawSubBank() {
        return withdrawSubBank;
    }

    public void setWithdrawSubBank(String withdrawSubBank) {
        this.withdrawSubBank = withdrawSubBank;
    }
}
