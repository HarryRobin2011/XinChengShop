package com.banksoft.XinChengShop.entity.chinaLocation;

import android.content.ContentValues;
import com.banksoft.XinChengShop.entity.base.BaseEntity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-3-25
 * Time: 下午9:32
 * To change this template use File | Settings | File Templates.
 */
public class City extends BaseEntity{
    private String id;
    private String name;
    private String code;
    private String parentsId;
    private List<County> countyList;

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

    public List<County> getCountyList() {
        return countyList;
    }

    public void setCountyList(List<County> countyList) {
        this.countyList = countyList;
    }

    public ContentValues getContentValues(){
        Field[] fields = this.getClass().getDeclaredFields();
        ContentValues values = new ContentValues();
        for(Field field : fields){
          field.setAccessible(true);
          if(List.class.getSimpleName().equals(field.getType().getSimpleName())){
              continue;
          }
              try {
                  values.put(field.getName(), (String) field.get(this));
              } catch (IllegalAccessException e) {
                  e.printStackTrace();
              }
          }
        return values;
    }
}
