package com.banksoft.XinChengShop.entity;

import com.banksoft.XinChengShop.entity.base.BaseEntity;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-28
 * Time: 下午3:03
 * To change this template use File | Settings | File Templates.
 */
public class MemberBO extends BaseEntity{
    private String id;
    private String siteId;
    private String parentId; //介绍人MemberId
    private String name;   //会员昵称
    private String account;   //会员账号
    private String password;
    private boolean status;   //是否开启
    private int score;
    private float balance;
    private long createTime;
    private long lastLogin;
    private int loginNum;       //登陆次数
    private boolean buyStatus; //允许购买
    private boolean stayStatus;//允许留言
    private String qq;
    private String ali;
    private String email;
    private String trueName;
    private String telephone; //lianxidianhua
    private String sex;
    private String imageFile;
	private String lastIp;
    private String payPassword;
    private String mergeAccount;  //第三方登录标示

    private String qqId; //绑定的qq
    private String weixinId; //绑定的微信Id
    private String weiboId; //绑定的微博id

    public String getWeixinId() {
        return weixinId;
    }

    public void setWeixinId(String weixinId) {
        this.weixinId = weixinId;
    }

    public String getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(String weiboId) {
        this.weiboId = weiboId;
    }

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId;
    }

    public String getMergeAccount() {
        return mergeAccount;
    }

    public void setMergeAccount(String mergeAccount) {
        this.mergeAccount = mergeAccount;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public int getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(int loginNum) {
        this.loginNum = loginNum;
    }

    public boolean isBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(boolean buyStatus) {
        this.buyStatus = buyStatus;
    }

    public boolean isStayStatus() {
        return stayStatus;
    }

    public void setStayStatus(boolean stayStatus) {
        this.stayStatus = stayStatus;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getAli() {
        return ali;
    }

    public void setAli(String ali) {
        this.ali = ali;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }
}
