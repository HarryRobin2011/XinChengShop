package com.banksoft.XinChengShop.entity;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-31
 * Time: 下午2:46
 * To change this template use File | Settings | File Templates.
 *  保存客户余额变更记录
 */
public class BalanceChangeRecord {
    private String id;
    private String siteId;
    private String memberId;
    private long createTime;
    private String type; // 充值，提现，消费，手动修改，系统，收入
    private String balanceType;//冻结金额，可用金额
    private float balance;          //变革余额
    private String remark;
    private String memberAccount;           //会员帐号
    private String adminAccount;            //管理员帐号
    private boolean addStatus;   //是否是余额增多
    private float changeBalance;

    public float getChangeBalance() {
        return changeBalance;
    }

    public void setChangeBalance(float changeBalance) {
        this.changeBalance = changeBalance;
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(String memberAccount) {
        this.memberAccount = memberAccount;
    }

    public String getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(String adminAccount) {
        this.adminAccount = adminAccount;
    }

    public boolean isAddStatus() {
        return addStatus;
    }

    public void setAddStatus(boolean addStatus) {
        this.addStatus = addStatus;
    }
}
