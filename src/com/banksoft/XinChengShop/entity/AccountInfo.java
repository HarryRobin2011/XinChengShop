package com.banksoft.XinChengShop.entity;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-10
 * Time: 下午4:52
 * To change this template use File | Settings | File Templates.
 */
public class AccountInfo {
    private String id;
    private String account; //帐号
    private String password;    //密码
    private String siteId;  //所属商城
    private String memberId;    //会员id
    private String payPassword;         //支付密码

    private String mergeAccount;  //第三方账号登陆绑定的唯一标示

    public String getMergeAccount() {
        return mergeAccount;
    }

    public void setMergeAccount(String mergeAccount) {
        this.mergeAccount = mergeAccount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }
}
