package com.banksoft.XinChengShop.type;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-31
 * Time: 下午4:10
 * To change this template use File | Settings | File Templates.
 */
public enum BalanceChangeType {
    RECHARGE("充值"),   //充值
    WITHDRAW("提现申请"),   //提现申请
    WITHDRAW_FAIL("提现失败"),   //提现失败
    WITHDRAW_RATE("提现手续费"),//提现手续费， 平台收入
    BUY("消费"),        //消费，买家购买商品
    EDIT("手动修改"),       //手动修改
    INCOME("收入"),     //收入
    SYSTEM("系统"),     //系统
    RETURN("退款"), //卖家退款
    RATE("佣金扣除"), //卖家佣金扣点，平台收入
    BUY_SMS("短信购买"), //卖家短信购买，平台收入
    BUY_SERVER("服务购买"), //卖家购买服务期限，平台收入
    REBATE("资金返利");      //资金返利
    //平台收入：  WITHDRAW_RATE + RATE + BUY_SMS + BUY_SERVER
    //卖家收入： INCOME - RATE - RETURN

    BalanceChangeType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    private String title;
}
