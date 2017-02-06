package com.banksoft.XinChengShop.widget;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.entity.PushMessage;
import com.banksoft.XinChengShop.entity.PushOrder;
import com.banksoft.XinChengShop.model.PushData;
import com.banksoft.XinChengShop.model.PushMessageData;
import com.banksoft.XinChengShop.model.PushOrderData;
import com.banksoft.XinChengShop.type.OrderStatus;
import com.banksoft.XinChengShop.type.PushType;
import com.banksoft.XinChengShop.ui.*;
import com.banksoft.XinChengShop.utils.JSONHelper;
import com.google.gson.internal.LinkedTreeMap;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Random;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyJPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";
    private Context mContext;
    private int count;
    private Random random = new Random();
    private int NUM = 1000;


    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            try {
                processCustomMessage(context, bundle);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");


        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {


        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) throws Exception{
        String result = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        PushData pushData = JSONHelper.fromJSONObject(result, JSONHelper.PUSH_DATA);
        if (PushType.order.equals(pushData.getMsgType())) {//订单
            PushOrderData pushOrderData = JSONHelper.fromJSONObject(result, JSONHelper.PUSH_ORDER_DATA);
            PushOrder pushOrder = pushOrderData.getData();
            OrderStatus status = pushOrderData.getData().getOrderStatus();
            if (PushType.member.equals(pushOrder.getType())) {//买家订单
                if (OrderStatus.DISPATCH.equals(status)) {// 已发货
                    Intent intent = new Intent(mContext, OrderListActivity.class);
                    intent.putExtra(IntentFlag.ORDER_ID,pushOrder.getOrderId());
                    intent.putExtra(IntentFlag.ORDER_STATUS,pushOrder.getOrderStatus());
                    showNotification("您有新的订单信息~", "您的订单" + pushOrderData.getData().getOrderId() + "已发货",random.nextInt(NUM), OrderListActivity.class,intent);
                }

            } else if (PushType.shop.equals(pushOrder.getType())) {//卖家订单
                if (OrderStatus.PAY.equals(status)) {// 已发货
                    Intent intent = new Intent(mContext, OrderListActivity.class);
                    intent.putExtra(IntentFlag.ORDER_ID,pushOrder.getOrderId());
                    intent.putExtra(IntentFlag.ORDER_STATUS,pushOrder.getOrderStatus());
                    showNotification("您有新的订单信息~", "订单" + pushOrderData.getData().getOrderId() + "已付款，请及时发货",random.nextInt(NUM), OrderListActivity.class,intent);
                }else if(OrderStatus.SUCCESS.equals(status)){
                    Intent intent = new Intent(mContext, OrderListActivity.class);
                    intent.putExtra(IntentFlag.ORDER_ID,pushOrder.getOrderId());
                    intent.putExtra(IntentFlag.ORDER_STATUS,pushOrder.getOrderStatus());
                    showNotification("您有新的订单信息~", "订单" + pushOrderData.getData().getOrderId() + "已完成",random.nextInt(NUM), OrderListActivity.class,intent);
                }else if(OrderStatus.REPEALING.equals(status)){
                    Intent intent = new Intent(mContext, OrderListActivity.class);
                    intent.putExtra(IntentFlag.ORDER_ID,pushOrder.getOrderId());
                    intent.putExtra(IntentFlag.ORDER_STATUS,pushOrder.getOrderStatus());
                    showNotification("您有新的订单信息~", "订单" + pushOrderData.getData().getOrderId() + "已提交退货，请及时处理",random.nextInt(NUM), OrderListActivity.class,intent);
                }
            } else if (PushType.dispatch.equals(pushOrder.getType())) {//派送订单
                if(OrderStatus.DISPATCH.equals(status)){
                    Intent intent = new Intent(mContext, ExpressBillListActivity.class);
                    intent.putExtra(IntentFlag.TYPE,ExpressBillListActivity.OperaType.NEW_DISPATCH_ORDER);
                    showNotification("新的派送订单信息~", "订单" + pushOrderData.getData().getOrderId(), random.nextInt(NUM),ExpressBillListActivity.class,intent);
                }
            }
        } else {//活动
            //PushMessage pushMessage = (PushMessage) pushData.getData();
            LinkedTreeMap dataMap = (LinkedTreeMap) pushData.getData();
             String content = (String) dataMap.get("content");
             String title = (String) dataMap.get("title");
             String targetId = (String) dataMap.get("targetId");
             String pushType = (String) dataMap.get("type");
            if (PushType.SHOP.name().equals(pushType)) {//推送店铺
                Intent intent = new Intent(mContext, ShopDetailActivity.class);
                intent.putExtra(IntentFlag.SHOP_ID,targetId);
                showNotification(title,content, random.nextInt(NUM),XCMainActivity.class,intent);
            }else if (PushType.PRODUCT.name().equals(pushType)) {//推送产品
                Intent  intent = new Intent(mContext,ShopProductInfoActivity.class);
                intent.putExtra(IntentFlag.PRODUCT_ID,targetId);
                showNotification(title, content, random.nextInt(NUM),ShopProductInfoActivity.class,intent);
            }else if (PushType.MESSAGE.name().equals(pushType)) {//推送消息
                Intent  intent = new Intent(mContext,MessageListActivity.class);
                showNotification(title, content, random.nextInt(NUM),MessageListActivity.class,intent);
            }
        }
    }

    //显示通知栏
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showNotification(String title, String content,int id,Class cls,Intent resultIntent) {
        count++;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mContext)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle(title)
                        .setContentText(content).setSound(Uri.withAppendedPath(
                        MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "3")).setOngoing(false);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        stackBuilder.addParentStack(cls);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(count, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = mBuilder.build();
        mNotificationManager.notify(id,notification);
    }
}
