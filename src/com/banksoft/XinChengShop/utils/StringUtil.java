package com.banksoft.XinChengShop.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 14-3-13.
 */
public class StringUtil {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 消费券密码
     * @param code
     * @return
     */
    public static String checkCouponsCode(String code){
        if(code.length() == 12) {
            return  code.substring(0,4) + "-" + code.substring(4,8) + "-" +code.substring(8);
        }
        return code;
    }


    //生成订单流水号
    public static String createNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String no = sdf.format(date);
        Random rm = new Random();
        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, 4);
        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);
        // 返回固定的长度的随机数
        return no + fixLenthString.substring(1, 4 + 1);
    }
    /**
     * 根据时间字符串获取long
     */
    public static long formatTime(String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
           return format.parse(time).getTime();
        } catch (ParseException e) {
            return -1l;
        }
    }

    /**
     * 将List 转成 SQL查询字符串， ‘1’，‘2’，‘3’
     * @param ids
     * @return
     */
    public static String convertListToSqlStr(List<String> ids){
        if(ids == null || ids.isEmpty()) return null;
        String sqlIds = "";
        for(String id : ids){
            if(!StringUtil.isNullOrEmpty(id) && !"null".equals(id)) {
                String[] temp = id.split("\\|");
                for(String t : temp) {
                    if(!StringUtil.isNullOrEmpty(t) && !"null".equals(t)) {
                        sqlIds += ",'" + t.replaceAll("\\|", "") + "'";
                    }
                }
            }
        }
        if("".equals(sqlIds)) return null;
        return sqlIds.substring(1);
    }

    public static String checkEmpty(String target){
      if(target == null){
         return "";
      }
      return target;
    }
}
