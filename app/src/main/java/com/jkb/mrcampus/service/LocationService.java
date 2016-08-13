package com.jkb.mrcampus.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.jkb.mrcampus.helper.map.MyLocationListener;

/**
 * 主要用于定位的服务
 * 方式：不与Activity绑定，直接进行定位
 * Created by JustKiddingBaby on 2016/8/12.
 */
public class LocationService extends Service {

    private static final String TAG = "LocationService";
    private ServiceInformBinder binder = new ServiceInformBinder();

    /**
     * 得到service的一个类，用于交互的时候使用
     */
    public class ServiceInformBinder extends Binder {
        public LocationService getService() {
            return LocationService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    //百度地图相关
    private boolean ableToLocation = true;//是否允许定位

    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = null;

    private static final int TIME_REQUEXTLOCATION = 1 * 1000;//每次定位的间隔时间

    @Override
    public void onCreate() {
        super.onCreate();
        initBaiduLocation();
        startBaiDuLocation();//开始定位
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 初始化百度地图进行定位
     */
    private void initBaiduLocation() {
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
        }
        if (myListener == null) {
            myListener = new MyLocationListener();
            mLocationClient.registerLocationListener(myListener); // 注册监听函数
            initLocation();
        }
    }

    /**
     * 初始化定位相关
     */
    private void initLocation() {
        Log.d(TAG, "initLocation");
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(TIME_REQUEXTLOCATION);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setNeedDeviceDirect(true);//网络定位时候是否需要方向
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(false);// 可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        mLocationClient.setLocOption(option);
    }

    /**
     * 开始百度地图定位
     */
    private void startBaiDuLocation() {
        Log.d(TAG, "startBaiDuLocation");
        if (ableToLocation) {
            // 开始定位
            mLocationClient.start();
        } else {
            stopBaiDuLocation();// 停止百度地图定位
        }
    }

    /**
     * 停止百度地图定位
     */
    private void stopBaiDuLocation() {
        Log.d(TAG, "stopBaiDuLocation");
        // 停止定位
        if (mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
    }
}
