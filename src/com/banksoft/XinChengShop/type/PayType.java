/**
 * @(#) PayType..java     2012/11/08 15:15
 *
 * 版权所有 (c) 北京银软网络技术有限公司
 * 北京市海淀区上地国际创业园西区1号
 * 保留所有权利
 */
package com.banksoft.XinChengShop.type;

/**
 * 类的功能，目的，描述等写在此处
 *
 * @author 刘志斌
 * @version 1.0
 */

public enum PayType {
    ALIPAY("支付宝支付"),
    HUICHAO("网银支付"),
    CHINAPAY("网银在线支付"),
    BALANCE("余额支付"),
    TENPAY("财付通支付"),
    SELFPAY("银软通宝支付"),
    MOBILE_BOFU("手机端宝付支付"),
    MOBILE_ALIPAY("手机端支付宝支付"),
    WEIXIN_PAY("微信支付"),
    CHINA_PAY("银联在线支付"),
    OTHER("现金券支付");

    private String name;

     PayType(String name) {
         this.name = name;
    }

    public String getName() {
        return name;
    }

}
