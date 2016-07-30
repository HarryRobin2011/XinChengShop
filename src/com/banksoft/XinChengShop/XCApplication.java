package com.banksoft.XinChengShop;

import android.app.Application;
import android.app.Service;
import android.graphics.Bitmap;
import android.os.Vibrator;
import cn.jpush.android.api.JPushInterface;
import com.baidu.mapapi.SDKInitializer;
import com.banksoft.XinChengShop.service.LocationService;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.iflytek.cloud.SpeechUtility;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.umeng.socialize.PlatformConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harry_robin on 15/11/14.
 */
public class XCApplication extends Application {
    public static XCApplication bailaAppLication;
    public static DisplayImageOptions options;        // DisplayImageOptions是用于设置图片显示的类
    private List<XCBaseActivity> activityList = new ArrayList<XCBaseActivity>();
    public LocationService locationService;
    public Vibrator mVibrator;
    @Override
    public void onCreate() {
        super.onCreate();
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());
        //友盟第三方分享组件
        initUmengSetting();
        setOptions();

        //讯飞语音
        SpeechUtility.createUtility(this, "appid=" + getString(R.string.xunfei_app_id));
        JPushInterface.setDebugMode(true);
        //极光推送
        JPushInterface.init(getApplicationContext());


    }

    public static XCApplication getInstance() {
        if(bailaAppLication == null){
            bailaAppLication = new XCApplication();
        }
        return bailaAppLication;
    }

    private void setOptions() {
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_picture_icon)
                .showImageForEmptyUri(R.drawable.default_picture_icon)
                .showImageOnFail(R.drawable.image_error_logo)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(100))// 淡入
                .build();

    }

    public void addActivity(XCBaseActivity activity) {
        activityList.add(activity);
    }

    public void removeActivity(XCBaseActivity activity) {
        activityList.remove(activity);
    }

    public void finish() {
        for (XCBaseActivity activity : activityList) {
            activity.defaultFinish();
        }
    }

    /**
     * AppID：wxe4d53f58f26cd989
     * 9a5eaf0585b6bb69462d2c7e17dbfd7a
     */

    private void initUmengSetting(){
        PlatformConfig.setWeixin("wxe4d53f58f26cd989", "9a5eaf0585b6bb69462d2c7e17dbfd7a");
        //微信 appid appsecret
        PlatformConfig.setSinaWeibo("1105213693","Nxy0UY02TzUVz1VT");
        //新浪微博 appkey appsecret
        PlatformConfig.setQQZone("1105238003", "MDYUtspmILTPqrNf");
        // QQ和Qzone appid appkey
    }
}
