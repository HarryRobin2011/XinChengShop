/**
 * @(#) AnimationUtil.java 2014/06/14 09:18
 *
 * 版权所有 (c) 北京银软网络技术有限公司
 * 北京市海淀区上地国际创业园西区1号
 * 保留所有权利
 */
package com.banksoft.XinChengShop.utils;

import android.app.Activity;
import com.banksoft.XinChengShop.R;

/**
 * 类的功能，目的，描述等写在此处
 *
 * @author 胡玉波
 * @version 1.0
 */
public class AnimationUtil {
    /**
     * 添加动画效果为 左进，作出
     */
    public static void setAnimationOfLeft(Activity activity) {
        activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 添加动画效果为 右进，右出
     */
    public static  void setAnimationOfRight(Activity activity) {
        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    /**
     * 添加动画效果为 下进 下出
     * @param activity
     */
    public static  void setAnimationOfUnder(Activity activity) {
        activity.overridePendingTransition(R.anim.push_buttom_in, R.anim.push_buttom_out);
    }

    /**
     * 添加动画效果为 上进 上出
     * @param activity
     */
    public static  void setAnimationOfUp(Activity activity) {
        activity.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    /**
     * 淡入
     */
    public static void setAnimationOfIn(Activity activity){
       activity.overridePendingTransition(R.anim.fade_out,R.anim.fade_in);
    }

    /**
     * 淡出
     */
    public static void setAnimationOut(Activity activity){
        activity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
}