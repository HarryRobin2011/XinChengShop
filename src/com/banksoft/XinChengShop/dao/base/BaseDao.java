package com.banksoft.XinChengShop.dao.base;

import android.content.Context;
import android.database.Cursor;
import com.banksoft.XinChengShop.db.DBHelper;
import com.banksoft.XinChengShop.http.HttpUrlConnection;
import com.banksoft.XinChengShop.utils.JSONHelper;
import com.banksoft.XinChengShop.utils.RequestCacheUtil;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-8-30
 * Time: 下午5:08
 * To change this template use File | Settings | File Templates.
 */
public class BaseDao implements HttpRequest {
    public Context mContext;
    public DBHelper dbHelper;

    public BaseDao(Context mContext) {
        this.mContext = mContext;
        dbHelper = DBHelper.getInstance(mContext);
    }


    @Override
    public Object postHttpRequest(Context mContext, String url, String params, Type jsonType, boolean cacheFlag) {
        String response = RequestCacheUtil.getRequestContent(mContext, url, params, cacheFlag);
        Object o = null;
        try {
            o = JSONHelper.fromJSONObject(response, jsonType);
        } catch (JsonParseException e) {
            o = null;
        }
        return o;
    }

    @Override
    public Object postHttpUrl(String url, Type jsonType) {
        try {
            byte[] bytes = HttpUrlConnection.postServer(url);
            String response = new String(bytes, "UTF-8");
            return JSONHelper.fromJSONObject(response, jsonType);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String getParams(Object object, String serviceObjectName) {
        Class<?> aClass = object.getClass();
        Field[] fields = object.getClass().getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        for (Field field : fields) {
            String name = field.getName();
            String c = name.charAt(0) + "";
            c = c.toUpperCase();
            String getter = "get" + c + name.substring(1);
            Method method;
            try {
                method = aClass.getMethod(getter);
            } catch (NoSuchMethodException e) {
                String iser = "is" + c + name.substring(1);
                try {
                    method = aClass.getMethod(iser);
                } catch (NoSuchMethodException e1) {
                    e1.printStackTrace();
                    continue;
                }
            }
            try {
                builder.append(serviceObjectName).append(".").append(field.getName()).append("=").append(method.invoke(object)).append("&");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        System.out.println(builder.toString());
        return builder.toString();
    }

    protected String getCollectionParams(Collection collection, String serviceObjectName) {
        StringBuilder builder = new StringBuilder();
        Object[] array = collection.toArray();
        for (int i = 0; i < collection.size(); i++) {
            Object object = array[i];
            Class<?> aClass = object.getClass();
            Field[] fields = object.getClass().getDeclaredFields();

            for (Field field : fields) {
                String name = field.getName();
                String c = name.charAt(0) + "";
                c = c.toUpperCase();
                String getter = "get" + c + name.substring(1);
                Method method;
                try {
                    method = aClass.getMethod(getter);
                } catch (NoSuchMethodException e) {
                    String iser = "is" + c + name.substring(1);
                    try {
                        method = aClass.getMethod(iser);
                    } catch (NoSuchMethodException e1) {
                        e1.printStackTrace();
                        continue;
                    }
                }
                try {
                    builder.append(serviceObjectName + "[" + i + "]").append(".").append(field.getName()).append("=").append(method.invoke(object)).append("&");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(builder.toString());
        }

        return builder.toString();
    }

    public <T> List getDataList(String tableName, String[] columns, String selectArgs, String[] whereArgs, String orderArgs, Class<T> cls) {
        Cursor cursor = dbHelper.query(tableName, columns, selectArgs, whereArgs, null, null, orderArgs);
        List<T> dataList = new LinkedList<>();
        while (cursor.moveToNext()) {
            try {
                Object o = cls.newInstance();
                Field[] fields = o.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(o, cursor.getString(cursor.getColumnIndex("f_" + field.getName())));
                }
                dataList.add((T) o);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return dataList;
    }

    public String getListParams(List dataList,String key) {
        StringBuffer paramsBuffer = new StringBuffer();
        for (int i = 0; i < dataList.size(); i++) {
            Object obc = dataList.get(i);
            Field[] fields = obc.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    if (field.get(obc) != null) {
                        paramsBuffer.append(key).append("[").append(i).append("]").append(".").append(field.getName()).append("=").
                                append(field.get(obc).toString()).append("&");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return paramsBuffer.toString();
    }

}
