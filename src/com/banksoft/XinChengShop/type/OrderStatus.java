package com.banksoft.XinChengShop.type;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-31
 * Time: 下午4:41
 * To change this template use File | Settings | File Templates.
 */
public enum OrderStatus {
    CREATE("待付款"),//订单已创建
    PAY("待发货"),//订单已支付
    DISPATCH("待收获"),//订单已发货
    SUCCESS("已完成"),//订单已完成
	OVER("订单取消或关闭"),//订单已关闭
	REPEALING("订单退货中"),//订单退货中
    REPEAL_OVER("订单退货结束"); //退货结束
    private String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
