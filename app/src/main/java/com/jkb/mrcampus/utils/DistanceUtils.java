package com.jkb.mrcampus.utils;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.jkb.model.info.LocationInfoSingleton;

import java.text.DecimalFormat;

/**
 * 距离的工具类
 * Created by JustKiddingBaby on 2016/11/14.
 */

public class DistanceUtils {

    private static double mLatitude = LocationInfoSingleton.getInstence().latitude;
    private static double mLongitude = LocationInfoSingleton.getInstence().longitude;

    private static final String TAG = "DistanceUtils";
    static double DEF_PI = 3.14159265359; // PI
    static double DEF_2PI = 6.28318530712; // 2*PI
    static double DEF_PI180 = 0.01745329252; // PI/180.0
    static double DEF_R = 6370693.5; // radius of earth

    // 适用于近距离
    public static double GetShortDistance(double lon1, double lat1,
                                          double lon2, double lat2) {
        double ew1, ns1, ew2, ns2;
        double dx, dy, dew;
        double distance;
        // 角度转换为弧度
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // 经度差
        dew = ew1 - ew2;
        // 若跨东经和西经180 度，进行调整
        if (dew > DEF_PI)
            dew = DEF_2PI - dew;
        else if (dew < -DEF_PI)
            dew = DEF_2PI + dew;
        dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
        dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
        // 勾股定理求斜边长
        distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }

    // 适用于远距离
    public static double GetLongDistance(double lon1, double lat1, double lon2,
                                         double lat2) {
        double ew1, ns1, ew2, ns2;
        double distance;
        // 角度转换为弧度
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // 求大圆劣弧与球心所夹的角(弧度)
        distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1)
                * Math.cos(ns2) * Math.cos(ew1 - ew2);
        // 调整到[-1..1]范围内，避免溢出
        if (distance > 1.0)
            distance = 1.0;
        else if (distance < -1.0)
            distance = -1.0;
        // 求大圆劣弧长度
        distance = DEF_R * Math.acos(distance);
        return distance;
    }

    /**
     * 计算坐标距离
     */
    public static double Calculate(double mLat, double mLon) {
        // 天安门坐标
        /*Log.d(TAG, "-----mLat1=" + mLatitude);
        Log.d(TAG, "-----mLon1=" + mLongitude);
        Log.d(TAG, "-----mLat=" + mLat);
        Log.d(TAG, "-----mLon=" + mLon);*/
        LatLng pt_start = new LatLng(mLatitude, mLongitude);
        LatLng pt_end = new LatLng(mLat, mLon);
        DecimalFormat df = new DecimalFormat("0.0 ");
        String dis = df.format(DistanceUtil.getDistance(pt_start, pt_end));
//        Log.d(TAG, "-----dis=" + dis);
        // 计算p1、p2两点之间的直线距离，单位：米
        return Double.valueOf(dis);
    }

    /**
     * 把距离转换为长度单位的
     */
    public static String changeDistance(double dis) {
        DecimalFormat df = new DecimalFormat("0.0 ");
        if (dis < 1000) {
            return df.format(dis) + "米";
        } else if (dis >= 1000 && dis <= 10000) {
            return df.format(dis / 1000) + "千米";
        } else if (dis >= 1000000) {
            return df.format(dis / 1000000) + "兆米";
        } else {
            return df.format(dis / 1000000) + "兆米";
        }
    }
}
