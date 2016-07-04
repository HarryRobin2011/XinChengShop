package com.banksoft.XinChengShop.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Environment;
import com.banksoft.XinChengShop.config.CacheConfig;
import com.banksoft.XinChengShop.db.CacheColumn;
import com.banksoft.XinChengShop.db.DBHelper;
import com.banksoft.XinChengShop.http.HttpUrlConnection;
import org.apache.http.util.EncodingUtils;

import java.io.*;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-5-9
 * Time: 下午4:36
 * To change this template use File | Settings | File Templates.
 */
public class RequestCacheUtil {
    private static final String TAG = "RequestCacheUtil";

    private static LinkedHashMap<String, SoftReference<String>> RequestCache = new LinkedHashMap<String, SoftReference<String>>(
            20);

    // [start] 公有方法


    public static String getRequestContent(Context context, String RequestUrl,String params, boolean UseCache) {

        DBHelper dbHelper = DBHelper.getInstance(context);
        String md5 = MD5Factory.encoding(RequestUrl+"?"+params);
        // 缓存目录
        if (!CommonUtil.sdCardIsAvailable())/* true 为可用 */ {
            String cachePath = context.getCacheDir().getAbsolutePath() + "/" + md5; // data里的缓存
            return getCacheRequest(context, RequestUrl,cachePath,params,dbHelper, UseCache);
        } else {
            String imagePath = getExternalCacheDir(context) + File.separator + md5; // sd卡
            return getCacheRequest(context, RequestUrl, imagePath,params,dbHelper, UseCache);
        }
    }

    // [end]

    // [start] 私有方法

    /**
     * 获得程序在sd开上的cahce目录
     *
     * @param context The context to use
     * @return The external cache dir
     */
    @SuppressLint("NewApi")
    private static String getExternalCacheDir(Context context) {
        // android 2.2 以后才支持的特性
        if (hasExternalCacheDir()) {
            return context.getExternalCacheDir().getPath() + File.separator
                    + "request";
        }

        // Before Froyo we need to construct the external cache dir ourselves
        // 2.2以前我们需要自己构造
        final String cacheDir = "/Android/data/" + context.getPackageName()
                + "/cache/request/";
        return Environment.getExternalStorageDirectory().getPath() + cacheDir;
    }

    private static boolean hasExternalCacheDir() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    private static String getCacheRequest(Context context, String requestUrl,
                                          String requestPath,
                                          String params,
                                          DBHelper dbHelper, boolean useCache) {
        String result;
        if (useCache) {
            result = getStringFromSoftReference(requestUrl+"?"+params);
            if (!result.equals(null) && !result.equals("")) {
                return result;
            }
            result = getStringFromLocal(requestPath,requestUrl+"?"+params,dbHelper);
            if (!result.equals(null) && !result.equals("")) {
                putStringForSoftReference(requestUrl+"?"+params, result);
                return result;
            }
        }
        result = getStringFromWeb(context,requestPath, requestUrl,params,dbHelper);
        return result;

    }


    private static void putStringForSoftReference(String requestUrl,
                                                  String result) {
        SoftReference<String> referece = new SoftReference<String>(result);
        RequestCache.put(requestUrl, referece);
    }

    private static String getStringFromWeb(Context context, String requestPath,
                                           String requestUrl, String params,
                                           DBHelper dbHelper) {
        String result = "";
        try {
            byte[] data = HttpUrlConnection.postFromWebByHttpUrlConnection(requestUrl, params.getBytes("UTF-8"));
            result = new String(data,"UTF-8");
            if (result.equals(null) && result.equals("")) {
                return result;
            }
            // 更新数据库
            Cursor cursor = getStringFromDB(requestUrl+"?"+params, dbHelper);
            updateDB(cursor, requestUrl+"?"+params,dbHelper);
            saveFileByRequestPath(requestPath, result);
            putStringForSoftReference(requestUrl+"?"+params, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void saveFileByRequestPath(String requestPath, String result) {
        deleteFileFromLocal(requestPath);
        saveFileForLocal(requestPath, result);
    }

    private static void saveFileForLocal(String requestPath, String result) {
        File file = new File(requestPath);
        if (!file.exists()) {
            try {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                file.createNewFile();
                FileOutputStream fout = new FileOutputStream(file);
                byte[] buffer = result.getBytes();
                fout.write(buffer);
                fout.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void updateDB(Cursor cursor, String requestUrl, DBHelper dbHelper) {
        if (cursor.moveToFirst()) {
            // 更新
            int id = cursor.getInt(cursor
                    .getColumnIndex(CacheColumn.F_ID));
            long timestamp = System.currentTimeMillis();
            String SQL = "update " + CacheColumn.TABLE_NAME + " set "
                    + CacheColumn.F_UPDATE_TIME + "=" + timestamp
                    + " where " + CacheColumn.F_ID + "=" + id;
            dbHelper.execSQL(SQL);
        } else {
            // 添加
            String SQL = "insert into " + CacheColumn.TABLE_NAME + "("
                    + CacheColumn.F_URL + ","
                    + CacheColumn.F_UPDATE_TIME + ") values('" + requestUrl
                    + "','" + System.currentTimeMillis() + "')";
            dbHelper.execSQL(SQL);
        }
    }

    private static String getStringFromSoftReference(String requestUrl) {
        if (RequestCache.containsKey(requestUrl)) {
            SoftReference<String> reference = RequestCache.get(requestUrl);
            String result = (String) reference.get();
            if (result != null && !result.equals("")) {
                return result;
            }
        }
        return "";
    }

    private static String getStringFromLocal(String requestPath,
                                             String requestUrlParams, DBHelper dbHelper) {
        String result = "";
        Cursor cursor = getStringFromDB(requestUrlParams, dbHelper);
        if (cursor.moveToFirst()) {
            Long timestamp = cursor.getLong(cursor
                    .getColumnIndex(CacheColumn.F_UPDATE_TIME));
            long span = CacheConfig.CONTENT_TIME;
            long nowTime = System.currentTimeMillis();
            if ((nowTime - timestamp) > span * 60 * 1000) {
                // 过期
                deleteFileFromLocal(requestPath);
            } else {
                // 没过期
                result = getFileFromLocal(requestPath);
            }
        }
        return result;
    }

    /**
     * 从db中查找数据
     *
     * @param requestUrl
     * @param dbHelper
     * @return
     */
    private static Cursor getStringFromDB(String requestUrl, DBHelper dbHelper) {
        String SQL = "select * from " + CacheColumn.TABLE_NAME
                + " where " + CacheColumn.F_URL + "='" + requestUrl + "'";
        Cursor cursor = dbHelper.rawQuery(SQL, new String[]{});
        return cursor;
    }

    private static String getFileFromLocal(String requestPath) {
        // TODO Auto-generated method stub
        File file = new File(requestPath);
        String result = "";
        if (file.exists()) {
            FileInputStream fileIn;
            try {
                fileIn = new FileInputStream(file);

                int length = fileIn.available();
                byte[] buffer = new byte[length];
                fileIn.read(buffer);
                result = EncodingUtils.getString(buffer, "UTF-8");
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return result;
        }
        return "";
    }

    private static void deleteFileFromLocal(String requestPath) {
        // TODO Auto-generated method stub
        File file = new File(requestPath);
        if (file.exists()) {
            file.delete();
        }
    }
}
