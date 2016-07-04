package com.banksoft.XinChengShop.config;

import java.util.HashMap;

/**
 * Created by Robin on 2015/2/12.
 */
public class ConfigPush {
   public static String PUSH_SP = "PUSH_SP";
   public static String PUSH_NEWS = "PUSH_NEWS";
   public static String PUSH_ANNOUNT = "PUSH_ANNOUNT";
   public static String PUSH_ORDER = "PUSH_ORDER";
   public static HashMap<String,String> tagHashMap;

   public static HashMap<String,String> getTagHashMap(){
       if(tagHashMap == null){
           tagHashMap = new HashMap<String, String>();
       }
       return tagHashMap;
   }
}
