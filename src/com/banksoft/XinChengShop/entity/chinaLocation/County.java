package com.banksoft.XinChengShop.entity.chinaLocation;

import android.content.ContentValues;
import com.banksoft.XinChengShop.entity.base.BaseEntity;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-3-25
 * Time: 下午9:33
 * To change this template use File | Settings | File Templates.
 */
public class County extends BaseEntity {
    private String id;
    private String name;
    private String code;
    private String parentsId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentsId() {
        return parentsId;
    }

    public void setParentsId(String parentsId) {
        this.parentsId = parentsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ContentValues getContentVales(){
        ContentValues values = new ContentValues();
        Field[] fields = this.getClass().getDeclaredFields();
        for(Field filed : fields){
          filed.setAccessible(true);
            try {
                values.put(filed.getName(), (String) filed.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return values;
    }
}
