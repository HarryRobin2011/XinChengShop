/**
 * @(#)      2013/11/19 13:18
 *
 * 版权所有 (c) 北京银软网络技术有限公司
 * 北京市海淀区上地国际创业园西区1号
 * 保留所有权利
 */
package com.banksoft.XinChengShop.type;

/**
 * 第三方登录类型
 * @author 王希辉
 * @version 1.0
 */
public enum MergeType {
    QQ("腾讯"),
    WEIXIN("微信"),
    WEIBO("新浪微博");

    private String name;

   MergeType(String name){
       this.name = name;
   }

    public String getName() {
        return name;
    }
}
