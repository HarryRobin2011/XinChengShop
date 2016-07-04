package com.banksoft.XinChengShop.dao;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import com.banksoft.XinChengShop.dao.base.BaseDao;
import com.banksoft.XinChengShop.entity.chinaLocation.City;
import com.banksoft.XinChengShop.entity.chinaLocation.County;
import com.banksoft.XinChengShop.entity.chinaLocation.Province;

import java.util.List;

/**
 * Created by harry_robin on 15/12/4.
 */
public class ExpressDao extends BaseDao{
    public ExpressDao(Context mContext) {
        super(mContext);
    }

    /**
     * 查询地理信息
     */
    public <T> List<T> findAreaList(int type, String parentsId) {
        List<T> list = null;
        switch (type) {
            case 0:
                list = (List<T>) dbHelper.selectAll("select * from province where parentsId = '" + parentsId + "'", Province.class, new String[0]);
                break;
            case 1:
                list = (List<T>) dbHelper.selectAll("select * from city where parentsId = '" + parentsId + "'", City.class, new String[0]);
                break;
            case 2:
                list = (List<T>) dbHelper.selectAll("select * from county where parentsId = '" + parentsId + "'", County.class, new String[0]);
                break;
        }
        return list;
    }

    /**
     * 查询单一地域
     * 0:省
     * 1：市
     * 2：县区
     */
    public Object findObject(String code, int type) {
        String sql = null;
        Class<?> params = null;
        Object o;
        switch (type) {
            case 0:
                sql = "select * from province where code = '"+ code+"'";
                params = Province.class;
                break;
            case 1:
                sql = "select * from city where code = '"+ code+"'";
                params = City.class;
                break;
            case 2:
                sql = "select * from county where code = '"+ code+"'";
                params = County.class;
                break;
        }
        try{
            o = dbHelper.selectAll(sql,params,new String[0]).get(0);
        }catch (SQLiteException e){
            return null;
        }
        return o;
    }
}
