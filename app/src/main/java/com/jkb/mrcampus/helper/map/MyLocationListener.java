package com.jkb.mrcampus.helper.map;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.jkb.model.info.LocationInfoSingleton;
import com.jkb.model.info.SingletonManager;
import com.jkb.model.utils.Config;


/**
 * 进行定位 的监听类
 */
public class MyLocationListener implements BDLocationListener {

    private static final String TAG = "MyLocationListener";
    private StringBuffer sb;
    // 获取当前定位相关的单例
    private LocationInfoSingleton locationSingleton = LocationInfoSingleton
            .getInstence();

    @Override
    public void onReceiveLocation(BDLocation location) {
        sb = new StringBuffer(256);
        sb.append("time : ");
        sb.append(location.getTime());
        sb.append("\nerror code : ");

        sb.append(location.getLocType());
        sb.append("\nlatitude : ");
        sb.append(location.getLatitude());
        sb.append("\nlontitude : ");
        sb.append(location.getLongitude());

        sb.append("\nradius : ");
        sb.append(location.getRadius());

        if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
            sb.append("\nspeed : ");
            sb.append(location.getSpeed());// 单位：公里每小时

            sb.append("\nsatellite : ");
            sb.append(location.getSatelliteNumber());
            sb.append("\nheight : ");
            sb.append(location.getAltitude());// 单位：米
            sb.append("\ndirection : ");
            sb.append(location.getDirection());// 单位度
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());

            sb.append("\ndescribe : ");
            sb.append("gps定位成功");

        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            // 运营商信息
            sb.append("\noperationers : ");
            sb.append(location.getOperators());
            sb.append("\ndescribe : ");
            sb.append("网络定位成功");
        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
            sb.append("\ndescribe : ");
            sb.append("离线定位成功，离线定位结果也是有效的");
        } else if (location.getLocType() == BDLocation.TypeServerError) {
            sb.append("\ndescribe : ");
            sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
            sb.append("\ndescribe : ");
            sb.append("网络不同导致定位失败，请检查网络是否通畅");
        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
            sb.append("\ndescribe : ");
            sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
        }

        // ////////////////////////////////////////////
        locationSingleton.time = location.getTime();
        locationSingleton.latitude = location.getLatitude();
        locationSingleton.longitude = location.getLongitude();
        locationSingleton.addStr = location.getAddrStr();
        locationSingleton.country = location.getCountry();
        locationSingleton.province = location.getProvince();
        locationSingleton.city = location.getCity();
        locationSingleton.districe = location.getDistrict();
        locationSingleton.street = location.getStreet();
        locationSingleton.speed = location.getSpeed();
        locationSingleton.radius = location.getRadius();

//        Log.i(TAG, "Longitude:" + location.getLongitude());
//        Log.i(TAG, "Latitude:" + location.getLatitude());
//        Log.i(TAG, "Add:" + location.getAddrStr());
        // /////////////////////////////////////////////
        // Log.d(TAG, "-----我在进行百度地图定位");
        // 注册到管理者中
        SingletonManager.registerService(Config.SINGLETON_KEY_LOCATION,
                locationSingleton);
    }
}
