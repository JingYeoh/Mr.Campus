package com.jkb.api.net.baidu.map.webService;

import com.jkb.api.config.Config;
import com.jkb.api.entity.baidu.map.webService.GeocoderEntity;
import com.jkb.api.entity.baidu.map.webService.ReverseGeocodingEntity;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 百度地图的GeocodingAPI接口
 * 经纬度到地址或者地址到经纬度的坐标转换的接口
 * Created by JustKiddingBaby on 2016/8/13.
 */

public interface GeocodingApi {

    /**
     * 通过坐标得到请求地址
     *
     * @param output    输出格式为json或者xml
     * @param ak        用户申请注册的key，自v2开始参数修改为“ak”，之前版本参数为“key”
     * @param coordtype 坐标的类型，目前支持的坐标类型包括：
     *                  bd09ll（百度经纬度坐标）、
     *                  bd09mc（百度米制坐标）、
     *                  gcj02ll（国测局经纬度坐标）、
     *                  wgs84ll（ GPS经纬度）
     * @param location  根据经纬度坐标获取地址
     * @param pois      是否显示指定位置周边的poi，0为不显示，1为显示。当值为1时，显示周边100米内的poi。
     * @param mcode     应用的sha1+签名
     * @return Call
     */
    @POST(Config.URL_GEOCODING)
    Call<ReverseGeocodingEntity> geocoder(
            @Query(Config.KEY_OUTPUT) String output,
            @Query(Config.KEY_AK) String ak,
            @Query(Config.KEY_COORDTYPE) String coordtype,
            @Query(Config.KEY_LOCATION) String location,
            @Query(Config.KEY_POIS) int pois,
            @Query(Config.KEY_MCODE) String mcode
    );

    /**
     * 通过请求地址得到坐标等参数
     *
     * @param output  输出格式为json或者xml
     * @param ak      用户申请注册的key，自v2开始参数修改为“ak”，之前版本参数为“key”
     * @param address 根据指定地址进行坐标的反定向解析，最多支持100个字节输入
     * @param city    地址所在的城市名。
     * @param mcode   应用的sha1+签名
     * @return Call
     */
    @POST(Config.URL_GEOCODING)
    Call<GeocoderEntity> geocoder(
            @Query(Config.KEY_OUTPUT) String output,
            @Query(Config.KEY_AK) String ak,
            @Query(Config.KEY_ADDRESS) String address,
            @Query(Config.KEY_CITY) String city,
            @Query(Config.KEY_MCODE) String mcode
    );
}
