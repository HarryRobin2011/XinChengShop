package com.banksoft.XinChengShop.db;


import com.banksoft.XinChengShop.entity.chinaLocation.City;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-3-28
 * Time: 下午8:16
 * To change this template use File | Settings | File Templates.
 */
public class CityColum extends DataBaseColumn{
    private final String TABLE_NAME = "city";
    private final Map<String,String> mColumnMap = new HashMap<String, String>();
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Map<String, String> getColumnMap() {
        Field[] fields = City.class.getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            if(List.class.getSimpleName().equals(field.getType().getSimpleName())){
                continue;
            }
            mColumnMap.put(field.getName(),getFieldType(field.getName()));
        }
        return mColumnMap;
    }

}
