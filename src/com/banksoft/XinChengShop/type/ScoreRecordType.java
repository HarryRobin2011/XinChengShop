package com.banksoft.XinChengShop.type;

/**
 * Created with IntelliJ IDEA.
 * User: 刘志斌
 * Date: 13-10-23
 * Time: 下午4:30
 * To change this template use File | Settings | File Templates.
 * 积分变更记录类型
 */
public enum ScoreRecordType {
    LOGIN("登陆奖励"),//登录获得积分
    REGISTER("注册奖励"),//注册获得积分
    MESSAGE("评论奖励"),//评论获得积分
    BUY("购买奖励"),//购买获得积分
    ADMIN_ADD("管理员奖励"),//管理员正向调整积分
    ADMIN_DEL("管理员扣除"),//管理员负向调整积分
    GIFT("积分换购");//积分换购消耗

    String title;

    ScoreRecordType(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
