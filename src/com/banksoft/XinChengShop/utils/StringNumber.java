/**
 * @(#) StringMath.java     2012/09/11 15:24
 *
 * 版权所有 (c) 北京银软网络技术有限公司
 * 北京市海淀区上地国际创业园西区1号
 * 保留所有权利
 */
package com.banksoft.XinChengShop.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * 类的功能，目的，描述等写在此处
 *
 * @author 张奎
 * @version 1.0
 */
public final class StringNumber {
    private BigDecimal decimal;
    private static MathContext MATH_CONTEXT = new MathContext(20, RoundingMode.HALF_UP);



    public StringNumber(String number) {
        decimal = new BigDecimal(number);
    }

    public static boolean isNullOrEmpty(String str){
        if(str == null || "".equals(str)) return true;
        return false;
    }

    /**
     * 加
     * @param number
     * @return
     */
    public StringNumber add(String number) {
        decimal = decimal.add(new BigDecimal(number));
        return this;
    }
    /**
     *减
     * @param number
     * @return
     */
    public StringNumber sub(String number) {
        decimal = decimal.subtract(new BigDecimal(number));
        return this;
    }
    /**
     *乘
     * @param number
     * @return
     */
    public StringNumber mul(String number) {
        decimal = decimal.multiply(new BigDecimal(number), MATH_CONTEXT);
        return this;
    }
    /**
     *除
     * @param number
     * @return
     */
    public StringNumber div(String number) {
        decimal = decimal.divide(new BigDecimal(number), MATH_CONTEXT);
        return this;
    }

    public static String add(String number1, String number2) {
        number1 = StringUtil.isNullOrEmpty(number1)?"0":number1;
        number2 = StringUtil.isNullOrEmpty(number2)?"0":number2;
        BigDecimal decimal = new BigDecimal(number1);
        decimal = decimal.add(new BigDecimal(number2));
        return decimal.toString();
    }

    public static String sub(String number1, String number2) {
        number1 = StringUtil.isNullOrEmpty(number1)?"0":number1;
        number2 = StringUtil.isNullOrEmpty(number2)?"0":number2;
        BigDecimal decimal = new BigDecimal(number1);
        decimal = decimal.subtract(new BigDecimal(number2));
        return decimal.toString();
    }

    public static String mul(String number1, String number2) {

        number1 = StringUtil.isNullOrEmpty(number1)?"0":number1;
        number2 = StringUtil.isNullOrEmpty(number2)?"0":number2;
        BigDecimal decimal = new BigDecimal(number1);
        decimal = decimal.multiply(new BigDecimal(number2), MATH_CONTEXT);
        return decimal.toString();
    }

    public static String div(String number1, String number2) {
        number1 = StringUtil.isNullOrEmpty(number1)?"0":number1;
        number2 = StringUtil.isNullOrEmpty(number2)?"0":number2;
        BigDecimal decimal = new BigDecimal(number1);
        decimal = decimal.divide(new BigDecimal(number2), MATH_CONTEXT);
        return decimal.toString();
    }

    /**
     * 绝对值
     * @param number
     * @return
     */
    public static String abs(String number) {
        if (number == null || number.trim().isEmpty()) return "0";
        return new BigDecimal(number).abs(MATH_CONTEXT).toString();
    }

    /**
     * 比较
     * @param number1
     * @param number2
     * @return
     */
    public static int compareTo(String number1, String number2) {
        number1 = StringUtil.isNullOrEmpty(number1)?"0":number1;
        number2 = StringUtil.isNullOrEmpty(number2)?"0":number2;
        return new BigDecimal(number1).compareTo(new BigDecimal(number2));
    }

    /**
     * 保留两位小数， 四舍五入
     *
     * @param number
     * @return
     */
    public static String formatPrecisionOfTwo(String number) {
        return new BigDecimal(number).setScale(2, RoundingMode.HALF_UP).toString();
    }

    /**
     * 保留一位小数， 四舍五入
     * @param number
     * @return
     */
    public static String formatPrecisionOfOne(String number) {
        return new BigDecimal(number).setScale(1, RoundingMode.HALF_UP).toString();
    }

    /**
     * 去除小数位， 四舍五入
     *
     * @param number
     * @return
     */
    public static String formatPrecisionOfZero(String number) {
        return new BigDecimal(number).setScale(0, RoundingMode.HALF_UP).toString();
    }

    /**
     * 小数点后直接抹除
     *
     * @param number
     * @return
     */
    public static String formatIgnoreOfZero(String number) {
        return new BigDecimal(number).setScale(0, RoundingMode.DOWN).toString();
    }

    public static String negate(String number) {
        return new BigDecimal(number).negate().toString();
    }


    //验证正数数字
    public static boolean checkNumber(String number) {
        if (number == null || number.trim().isEmpty()) {
            return false;
        }
        try {
            double d = Double.parseDouble(number);
            if (d < 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public String toString() {
        return decimal.toString();
    }

    /**
     * String 类型的金额转为Long型，并只保存原有金额的小数点后两位有效数字（四舍五入），保存时无小数
     *
     * @param money
     * @return
     */
    public static long stringToLongOfMoney(String money) {
        return Long.parseLong(formatPrecisionOfZero(mul(money, "100")));
    }

    public static String longToStringOfMoney(long money) {
        return div(String.valueOf(money), "100");
    }
}
