package com.jkb.model.info;

/**
 * 当前定位的信息类
 * 设计模式：单例
 * Created by JustKiddingBaby on 2016/8/12.
 */

public class LocationInfoSingleton {

    public String time = "";// 获取位置的时间
    public double latitude = -9999;// 精度
    public double longitude = -9999;// 纬度
    public String city = "";// 城市
    public String street = "";// 街道
    public float radius = 0;// 半径
    public float speed = 0;// 速度
    public String addStr = "";// 地址
    public String province = "";// 省份
    public String districe = "";// 区/县
    public String country = "";// 国家

    // 设为单例模式
    private LocationInfoSingleton() {
    }

    public static LocationInfoSingleton getInstence() {
        return SingletonHolder.sInstence;
    }

    /**
     * 静态内部类
     */
    private static class SingletonHolder {
        private static final LocationInfoSingleton sInstence = new LocationInfoSingleton();
    }
}
