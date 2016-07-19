package com.banksoft.XinChengShop.widget;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;
import com.banksoft.XinChengShop.R;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.model.PushData;
import com.banksoft.XinChengShop.model.PushMessageData;
import com.banksoft.XinChengShop.model.PushOrderData;
import com.banksoft.XinChengShop.type.OrderType;
import com.banksoft.XinChengShop.type.PushType;
import com.banksoft.XinChengShop.ui.OrderListActivity;
import com.banksoft.XinChengShop.utils.JSONHelper;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyJPushReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";
	private Context mContext;

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

        	
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        	Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
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
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
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
		PushData pushData = JSONHelper.fromJSONObject(result,JSONHelper.PUSH_DATA);
		if(PushType.order.equals(pushData.getMessageType())){//订单
			PushOrderData pushOrderData = (PushOrderData) pushData;
			if(PushType.member.equals(((PushOrderData) pushData).getData().getType())){//买家订单

			}else if(PushType.shop.equals(((PushOrderData) pushData).getData().getType())){//卖家订单

			}else if(PushType.dispatch.equals(((PushOrderData) pushData).getData().getType())){//派送订单


			}
		}else{//活动
			PushMessageData pushMessageData = (PushMessageData) pushData;
			if(PushType.SHOP.equals(pushMessageData.getData().getPushType())){//推送店铺

		    }else if (PushType.PRODUCT.equals(pushMessageData.getData().getPushType())){//推送产品

			}
		}
	}

	//显示通知栏
	private void showNotification(){
		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(mContext)
						.setSmallIcon(R.drawable.icon)
						.setContentTitle("My notification")
						.setContentText("Hello World!");
// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, ResultActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(ResultActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
				stackBuilder.getPendingIntent(
						0,
						PendingIntent.FLAG_UPDATE_CURRENT
				);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
				(NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
		mNotificationManager.notify(mId, mBuilder.build());
	}
}
