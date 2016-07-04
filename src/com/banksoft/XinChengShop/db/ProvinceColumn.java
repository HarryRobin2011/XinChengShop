package com.banksoft.XinChengShop.db;


import com.banksoft.XinChengShop.entity.chinaLocation.Province;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-3-28
 * Time: 下午8:15
 * To change this template use File | Settings | File Templates.
 */
public class ProvinceColumn extends DataBaseColumn {
    private final static String TABLE_NAME = "province";
    private final Map<String, String> mColumnMap = new HashMap<String, String>();

    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Map<String, String> getColumnMap() {
        Field[] fields = Province.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (List.class.getSimpleName().equals(field.getType().getSimpleName())) {
                continue;
            }
            mColumnMap.put(field.getName(), getFieldType(field.getName()));
        }
        return mColumnMap;
    }
}
