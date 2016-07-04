package com.banksoft.XinChengShop.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Robin on 2015/2/28.
 */
public class ShareUtil {
    /**
     * 分享功能
     *
     * @param activity       上下文
     * @param activityTitle Activity的名字
     * @param msgTitle      消息标题
     * @param msgText       消息内容
     * @param resId       分享图片资源ID
     */
    public static void shareMsg(Activity activity, String activityTitle, String msgTitle, String msgText,
                                int resId) {
       // Context context = activity.getApplicationContext();
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (resId == 0) {
            intent.setType("text/plain"); // 纯文本
        } else {
            Uri uri = Uri.parse("file:///android_asset/two_dimensional_code.png");
            intent.setType("image/png");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(Intent.createChooser(intent, activityTitle));
    }
}
