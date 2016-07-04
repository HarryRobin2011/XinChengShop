package com.banksoft.XinChengShop.utils;

import android.content.Context;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.banksoft.XinChengShop.config.IntentFlag;
import com.banksoft.XinChengShop.ui.base.XCBaseActivity;
import com.banksoft.XinChengShop.ui.base.XCBaseFragment;

/**
 * Created by harry_robin on 15/9/12.
 */
public class LocationUtils {
    private Context mContext;
    public LocationClient mLocationClient;
    public MyLocationListener mMyLocationListener;
    private String tempcoor="gcj02";
    private XCBaseActivity activity;
    private XCBaseFragment baseFragment;
    private LocationListener locationListener;

    public LocationUtils(LocationListener locationListener){
        baseFragment = (XCBaseFragment) locationListener;
        activity = (XCBaseActivity) baseFragment.getActivity();
        this.mContext = activity.getApplicationContext();
        this.locationListener = locationListener;
    }

    public void startLocation(){
        mLocationClient = new LocationClient(mContext);
        initLocation();
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        mLocationClient.start();
    }

    public class MyLocationListener implements BDLocationListener {


        @Override
        public void onReceiveLocation(BDLocation location) {
            if(location != null){
                String areaID = activity.preferences.getString(IntentFlag.AREA_ID,"");
                if(!areaID.equals(location.getCityCode())){
                    activity.preferences.edit().putString(IntentFlag.AREA_NAME,location.getProvince()+location.getCity()).commit();
                    activity.preferences.edit().putString(IntentFlag.AREA_ID,location.getCityCode()).commit();
                }
            }
            locationListener.setLocation(location);
            mLocationClient.stop();

        }


    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType(tempcoor);//可选，默认gcj02，设置返回的定位结果坐标系，
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(false);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    public interface LocationListener{
        public void setLocation(BDLocation location);
    }
}
