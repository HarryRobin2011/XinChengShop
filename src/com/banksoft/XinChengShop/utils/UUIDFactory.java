/**
 * @(#) UUIDFactory.java 2014/06/16 16:50
 *
 * 版权所有 (c) 北京银软网络技术有限公司
 * 北京市海淀区上地国际创业园西区1号
 * 保留所有权利
 */
package com.banksoft.XinChengShop.utils;

import java.util.UUID;

/**
 * 类的功能，目的，描述等写在此处
 *
 * @author 胡玉波
 * @version 1.0
 */
public class UUIDFactory {

    public static String random() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}