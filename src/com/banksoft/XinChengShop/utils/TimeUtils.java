package com.banksoft.XinChengShop.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-6-11
 * Time: 下午2:40
 * To change this template use File | Settings | File Templates.
 */
public class TimeUtils {

    public static enum TimeType{
      Day,
      HOUR,
      MINUTE,
      SECOND
    }

    public static String timeAgo(String timeStr)
    {
        Date date = null;
        try
        {
            SimpleDateFormat format =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
            date = format.parse(timeStr);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return "";
        }


        long timeStamp = date.getTime();

        Date currentTime = new Date();
        long currentTimeStamp = currentTime.getTime();
        long seconds = (currentTimeStamp - timeStamp)/1000;

        long minutes = Math.abs(seconds / 60);
        long hours = Math.abs(minutes / 60);
        long days = Math.abs(hours / 24);



        if ( seconds <= 15 )
        {
            return "刚刚";
        }
        else if ( seconds < 60 )
        {
            return seconds+"秒前";
        }
        else if ( seconds < 120 )
        {
            return"1分钟前";
        }
        else if ( minutes < 60 )
        {
            return minutes+"分钟前";
        }
        else if ( minutes < 120 )
        {
            return "1小时前";
        }
        else if ( hours < 24 )
        {
            return hours +"小时前";
        }
        else if ( hours < 24 * 2 )
        {
            return "1天前";
        }
        else if ( days < 5 )
        {
            return days+"天前" ;
        }
        else
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
            String dateString = formatter.format(date);
            return dateString;
        }

    }

    public static String timeLeft(long timeStamp)
    {
//        Date date = null;
//        try
//        {
//            SimpleDateFormat format =   new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss Z" );
//            date = format.parse(timeStr);
//        }
//        catch (ParseException e)
//        {
//            e.printStackTrace();
//            return "";
//        }
//
//
//        long timeStamp = date.getTime();

        Date currentTime = new Date();
        long currentTimeStamp = currentTime.getTime();

        long total_seconds = (timeStamp - currentTimeStamp)/1000;

        if (total_seconds <= 0)
        {
            return  "";
        }

        long days = Math.abs(total_seconds / (24 * 60 * 60));

        long hours = Math.abs((total_seconds - days * 24 * 60 * 60) / (60 * 60));
        long minutes = Math.abs((total_seconds - days * 24 * 60 * 60 - hours * 60 * 60) / 60);
        long seconds = Math.abs((total_seconds - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60));
        String leftTime;
        if (days > 0)
        {
            leftTime = days+"天" + hours + "小时" + minutes +"分" +seconds+"秒";
        }
        else if (hours > 0)
        {
            leftTime = hours + "小时" + minutes +"分" +seconds+"秒";
        }
        else if (minutes > 0)
        {
            leftTime = minutes +"分" +seconds+"秒";
        }
        else if (seconds > 0)
        {
            leftTime = seconds+"秒";
        }
        else
        {
            leftTime = "0秒";
        }

        return leftTime;
    }

    public static String timeRuningTime(long beginTime,long endTime){
        if(endTime < beginTime){
          return "已过期";
        }
        long total_seconds = (endTime - beginTime)/1000;
        long days = Math.abs(total_seconds / (24 * 60 * 60));

        long hours = Math.abs((total_seconds - (days * 24 * 60 * 60)) / (60 * 60));
        long minutes = Math.abs((total_seconds - days * 24 * 60 * 60 - hours * 60 * 60) / 60);
        long seconds = Math.abs((total_seconds - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60));
        String leftTime;
        if (days > 0)
        {
            leftTime = days+"天" + hours + "小时" + minutes +"分" +seconds+"秒";
        }
        else if (hours > 0)
        {
            leftTime = hours + "小时" + minutes +"分" +seconds+"秒";
        }
        else if (minutes > 0)
        {
            leftTime = minutes +"分" +seconds+"秒";
        }
        else if (seconds > 0)
        {
            leftTime = seconds+"秒";
        }
        else
        {
            leftTime = "0秒";
        }

        return leftTime;
    }

    public static String getCurrentTimeStr(Enum timeType){
        String timeStype = null;
        if(TimeType.Day.equals(timeType)){
            timeStype = "yyyy-MM-dd ";
        }else if(TimeType.HOUR.equals(timeType)){
            timeStype = "yyyy-MM-dd HH";
        }else if(TimeType.MINUTE.equals(timeType)){
            timeStype = "yyyy-MM-dd HH:mm";
        }else if(timeType.equals(timeType)){
            timeStype = "yyyy-MM-dd HH:mm:ss";
        }
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(timeStype);
        return dateFormat.format(date);
    }

    public static long getDateTime(String time,TimeType timeType){
        String timeStype = null;
        Long timeLong = 0l;
        if(TimeType.Day.equals(timeType)){
            timeStype = "yyyy-MM-dd";
        }else if(TimeType.HOUR.equals(timeType)){
            timeStype = "yyyy-MM-dd HH";
        }else if(TimeType.MINUTE.equals(timeType)){
            timeStype = "yyyy-MM-dd HH:mm";
        }else if(timeType.equals(timeType)){
            timeStype = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(timeStype);
        try {
            timeLong = dateFormat.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeLong;
    }

    public static String getTimeStr(long time, TimeType timeType){
        String timeStype = null;
        if(TimeType.Day.equals(timeType)){
            timeStype = "yyyy-MM-dd ";
        }else if(TimeType.HOUR.equals(timeType)){
            timeStype = "yyyy-MM-dd HH";
        }else if(TimeType.MINUTE.equals(timeType)){
            timeStype = "yyyy-MM-dd HH:mm";
        }else if(timeType.equals(timeType)){
            timeStype = "yyyy-MM-dd HH:mm:ss";
        }
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat(timeStype);
        return dateFormat.format(date);
    }
}
