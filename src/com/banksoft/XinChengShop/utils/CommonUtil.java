package com.banksoft.XinChengShop.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.SharedPreTag;
import com.banksoft.XinChengShop.http.HttpUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-5-10
 * Time: 上午9:23
 * To change this template use File | Settings | File Templates.
 */
public class CommonUtil {
    public static final String TAG = "HarryRobin";

    /**
     * 检测sdcard是否可用
     *
     * @return true为可用，否则为不可用
     */
    public static boolean sdCardIsAvailable() {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED))
            return false;
        return true;
    }

    /**
     * Checks if there is enough Space on SDCard
     *
     * @param updateSize
     *            Size to Check
     * @return True if the Update will fit on SDCard, false if not enough space on SDCard Will also return false, if the SDCard is
     *         not mounted as read/write
     */
    public static boolean enoughSpaceOnSdCard(long updateSize) {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED))
            return false;
        return (updateSize < getRealSizeOnSdcard());
    }

    /**
     * get the space is left over on sdcard
     */
    public static long getRealSizeOnSdcard() {
        File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * Checks if there is enough Space on phone self
     *
     */
    public static boolean enoughSpaceOnPhone(long updateSize) {
        return getRealSizeOnPhone() > updateSize;
    }

    /**
     * get the space is left over on phone self
     */
    public static long getRealSizeOnPhone() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        long realSize = blockSize * availableBlocks;
        return realSize;
    }

    /**
     * 根据手机分辨率从dp转成px
     * @param context
     * @param dpValue
     * @return
     */
    public static  int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static  int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f)-15;
    }

    /**
     * 读取Assets 下 TXT
     */
    public static String readAssets(Context mContext,String name){
        InputStream is = null;
        try {
            is = mContext.getAssets().open(name);
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
            String text = new String(buffer, "UTF-8");
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 拨打电话
     * @param context
     * @param tel
     */
    public static void callPhone(Context context,String tel){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel://"+tel));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     *
     * @param context
     * @param tel
     * @param msg
     */
    public static void sendNote(Context context,String tel,String msg){
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+tel));
        intent.putExtra("sms_body", msg);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 操作软键盘
     * 打开时关闭
     * 关闭时打开
     * @param context
     */
    public static void operationKeyboard(Context context){
        InputMethodManager m=(InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    public static void openQQ(Context context,String s) throws Exception{
        if(s == null || "".equals(s)){
            Toast.makeText(context,context.getText(R.string.qq_empty).toString(),Toast.LENGTH_SHORT).show();
            return;
        }
        String url="mqqwpa://im/chat?chat_type=wpa&uin=123456";
        String params = url.replace("123456",s);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(params));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * EditText 编辑状态
     * @param editText
     * @param isEdit
     */
    public static void setEditTextEditStatus(EditText editText, boolean isEdit) {
        editText.setFocusableInTouchMode(isEdit);
        editText.setFocusable(isEdit);
        editText.requestFocus();
    }

    /**
     * Double 转Int
     * @param string
     * @return
     */
    public static int stringToInt(String string){
        int j = 0;
        String str = string.substring(0, string.indexOf("."));
        int intgeo = Integer.parseInt(str);
        return intgeo;
    }

    public static String filteredNull(String data){
        String response = "";
        if(data.indexOf("null") != -1){
         response =   data.replace("null","");
        }
        return response;
    }

    public static void clearShopCart(Context context){
        SharedPreferences sharedPreferences  = context.getSharedPreferences(SharedPreTag.SHOP_CART_PRODUCT, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }

    public static void setmWebViewSettings(Context context, WebView webView) {
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webSettings.setBlockNetworkImage(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setAllowFileAccess(true);
        if (HttpUtils.isNetworkAvailable(context)) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
    }
}
