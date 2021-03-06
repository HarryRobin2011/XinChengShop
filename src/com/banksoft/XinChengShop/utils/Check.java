package com.banksoft.XinChengShop.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-8-14
 * Time: 下午3:15
 * To change this template use File | Settings | File Templates.
 */
public class Check{
    /**
     * Check cellphone
     */
    public static boolean isCellphone(String cellphoneNum){
        return cellphoneNum.matches("^(13|14|15|18)\\d{9}$");
    }

    /**
     　　* 验证邮箱地址是否正确
     　　* @param email
     　　* @return
     　　*/
    public static boolean checkEmail(String email)

    {
        try {
            Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
            Matcher m = p.matcher(email);
            return m.matches();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check chinese
     * @param str
     * @return
     */
    public static boolean isFilter(String str){
        if(str.replaceAll("[a-z]*[A-Z]*\\d*-*_*\\s*", "").length()==0){
            return true;
        }else
            return false;
    }

    /**
     * Check Integer
     */
    public static boolean isNumber(String str){
       try {
          Integer.parseInt(str);
          return true;
       }catch (NumberFormatException e){
          return false;
       }
    }

    /**
     * 获取手机号
     */
    public static String getTelPhone(Context mContext){
        TelephonyManager phoneMgr=(TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String telPhone = phoneMgr.getLine1Number();
        return telPhone.replace("+86","");
    }

    /**
     * 匹配中国邮政编码
     * @param postCode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isPostCode(String postCode){
        String reg = "[1-9]\\d{5}";
        return Pattern.matches(reg, postCode);
    }

}
