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
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
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

    /**
     * ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
     .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
     .diskCacheExtraOptions(480, 800, null)
     .taskExecutor(...)
     .taskExecutorForCachedImages(...)
     .threadPoolSize(3) // default
     .threadPriority(Thread.NORM_PRIORITY - 1) // default
     .tasksProcessingOrder(QueueProcessingType.FIFO) // default
     .denyCacheImageMultipleSizesInMemory()
     .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
     .memoryCacheSize(2 * 1024 * 1024)
     .memoryCacheSizePercentage(13) // default
     .diskCache(new UnlimitedDiscCache(cacheDir)) // default
     .diskCacheSize(50 * 1024 * 1024)
     .diskCacheFileCount(100)
     .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
     .imageDownloader(new BaseImageDownloader(context)) // default
     .imageDecoder(new BaseImageDecoder()) // default
     .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
     .writeDebugLogs()
     .build();
     */
    private void setOptions() {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(this);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.list_thumbnail_loading_ss)
                .showImageForEmptyUri(R.drawable.list_thumbnail_loading_ss)
                .showImageOnFail(R.drawable.list_thumbnail_loading_ss)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
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
        PlatformConfig.setSinaWeibo("4230626533","4cf54bb72a9a04767e3051f8451702b0");
        //新浪微博 appkey appsecret
        PlatformConfig.setQQZone("1105213693", "Nxy0UY02TzUVz1VT");
        // QQ和Qzone appid appkey
    }
}
