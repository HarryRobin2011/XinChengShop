package com.banksoft.XinChengShop.db;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-8-31
 * Time: 下午4:30
 * To change this template use File | Settings | File Templates.
 */
public class CacheColumn extends DataBaseColumn{
    public  static final String TABLE_NAME = "cache";
    public  static final String F_ID = "f_id";
    public  static final String F_URL = "f_url";
    public  static final String F_UPDATE_TIME = "f_update_time";
    public  static final String F_JSON = "f_json";
    private Map<String,String> columnMap;


    public Map<String,String> getColumnMap(){
        if(columnMap == null){
            columnMap = new HashMap<String, String>();
        }
        columnMap.put(F_ID,"integer primary key autoincrement");
        columnMap.put(F_URL,"text");
        columnMap.put(F_UPDATE_TIME,"integer");
        columnMap.put(F_JSON,"text");
        return columnMap;
    }

    public String getTableName(){
        return TABLE_NAME;
    }
}
