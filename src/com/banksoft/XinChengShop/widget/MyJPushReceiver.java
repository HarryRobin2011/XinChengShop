package com.banksoft.XinChengShop.widget;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

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
            processCustomMessage(context, bundle);

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
    private void processCustomMessage(Context context, Bundle bundle) {
        String result = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        PushData pushData = JSONHelper.fromJSONObject(result, JSONHelper.PUSH_DATA);
        if (PushType.order.equals(pushData.getMessageType())) {//订单
            PushOrderData pushOrderData = (PushOrderData) pushData;
            PushOrder pushOrder = pushOrderData.getData();
            OrderStatus status = pushOrderData.getData().getOrderStatus();
            if (PushType.member.equals(((PushOrderData) pushData).getData().getType())) {//买家订单
                if (OrderStatus.DISPATCH.equals(status)) {// 已发货
                    Intent intent = new Intent(mContext, ShopProductInfoActivity.class);
                    intent.putExtra(IntentFlag.ORDER_ID,pushOrder.getOrderId());
                    intent.putExtra(IntentFlag.ORDER_STATUS,pushOrder.getOrderStatus());
                    showNotification("您有新的订单信息~", "您的订单" + pushOrderData.getData().getOrderId() + "已发货", OrderListActivity.class,intent);
                }

            } else if (PushType.shop.equals(((PushOrderData) pushData).getData().getType())) {//卖家订单
                if (OrderStatus.PAY.equals(status)) {// 已发货
                    Intent intent = new Intent(mContext, ShopProductInfoActivity.class);
                    intent.putExtra(IntentFlag.ORDER_ID,pushOrder.getOrderId());
                    intent.putExtra(IntentFlag.ORDER_STATUS,pushOrder.getOrderStatus());
                    showNotification("您有新的订单信息~", "订单" + pushOrderData.getData().getOrderId() + "已付款，请及时发货", OrderListActivity.class,intent);
                }else if(OrderStatus.SUCCESS.equals(status)){
                    Intent intent = new Intent(mContext, ShopProductInfoActivity.class);
                    intent.putExtra(IntentFlag.ORDER_ID,pushOrder.getOrderId());
                    intent.putExtra(IntentFlag.ORDER_STATUS,pushOrder.getOrderStatus());
                    showNotification("您有新的订单信息~", "订单" + pushOrderData.getData().getOrderId() + "已完成", OrderListActivity.class,intent);
                }else if(OrderStatus.REPEALING.equals(status)){
                    Intent intent = new Intent(mContext, ShopProductInfoActivity.class);
                    intent.putExtra(IntentFlag.ORDER_ID,pushOrder.getOrderId());
                    intent.putExtra(IntentFlag.ORDER_STATUS,pushOrder.getOrderStatus());
                    showNotification("您有新的订单信息~", "订单" + pushOrderData.getData().getOrderId() + "已提交退货，请及时处理", OrderListActivity.class,intent);
                }
            } else if (PushType.dispatch.equals(((PushOrderData) pushData).getData().getType())) {//派送订单
                if(OrderStatus.DISPATCH.equals(status)){
                    Intent intent = new Intent(mContext, ShopProductInfoActivity.class);
                    intent.putExtra(IntentFlag.ORDER_ID,pushOrder.getOrderId());
                    intent.putExtra(IntentFlag.ORDER_STATUS,pushOrder.getOrderStatus());
                    showNotification("新的派送订单信息~", "订单" + pushOrderData.getData().getOrderId(), OrderListActivity.class,intent);
                }

            }
        } else {//活动
            PushMessageData pushMessageData = (PushMessageData) pushData;
            PushMessage pushMessage = pushMessageData.getData();
            if (PushType.SHOP.equals(pushMessageData.getData().getPushType())) {//推送店铺
                Intent intent = new Intent(mContext, ShopProductInfoActivity.class);
                intent.putExtra(IntentFlag.SHOP_ID,pushMessage.getTargetId());
                showNotification(pushMessage.getTitle(), pushMessage.getContent(), ShopDetailActivity.class,intent);
            }else if (PushType.PRODUCT.equals(pushMessageData.getData().getPushType())) {//推送产品
                Intent  intent = new Intent(mContext,ShopInfoActivity.class);
                intent.putExtra(IntentFlag.PRODUCT_ID,pushMessage.getTargetId());
                showNotification(pushMessage.getTitle(), pushMessage.getContent(), ShopProductInfoActivity.class,intent);
            }else if (PushType.MESSAGE.equals(pushMessageData.getData().getPushType())) {//推送产品
                Intent  intent = new Intent(mContext,MessageListActivity.class);
                showNotification(pushMessage.getTitle(), pushMessage.getContent(), MessageListActivity.class,intent);
            }
        }
    }

    //显示通知栏
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showNotification(String title, String content, Class cls,Intent resultIntent) {
        count++;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mContext)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle(title)
                        .setContentText(content);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        stackBuilder.addParentStack(cls);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(count, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(android.R.id.text1, mBuilder.build());
    }
}
