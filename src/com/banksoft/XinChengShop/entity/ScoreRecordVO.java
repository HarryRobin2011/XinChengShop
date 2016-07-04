package com.banksoft.XinChengShop.entity;

/**
 * Created with IntelliJ IDEA.
 * User: lzw
 * Date: 14-2-22
 * Time: 上午11:35
 * To change this template use File | Settings | File Templates.
 */
public class ScoreRecordVO {
    private String id;
    private String siteId;
    private String memberId;      //会员id
    private String memberType;      //会员类型（SHOP,MEMBER）
    private String type;        //操作类型（LOGIN,REGISTER,MESSAGE,BUY,ADMIN_ADD, ADMIN_DEL, GIFT）
    private String changeType;         //变更类型（ADD, DELETE）
    private long createTime;        //记录时间
    private String remark;          //备注
    private String operator;        //操作人，运营者，如果系统自动生成的，则不填
    private int score;
    private String name;//会员昵称

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
