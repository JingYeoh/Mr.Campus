package com.jkb.model.dataSource.baidu.map.webService.remote;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.config.Config;
import com.jkb.api.entity.baidu.map.webService.GeocoderEntity;
import com.jkb.api.entity.baidu.map.webService.ReverseGeocodingEntity;
import com.jkb.api.net.baidu.map.webService.GeocodingApi;
import com.jkb.model.dataSource.baidu.map.webService.BaiduMapWebServiceDataSource;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 百度地图的Web服务的远程数据来源类
 * Created by JustKiddingBaby on 2016/8/13.
 */

public class BaiduMapWebServiceRemoteDataSource implements BaiduMapWebServiceDataSource {

    private static BaiduMapWebServiceRemoteDataSource INSTANCE = null;

    public static BaiduMapWebServiceRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BaiduMapWebServiceRemoteDataSource();
        }
        return INSTANCE;
    }


    private BaiduMapWebServiceRemoteDataSource() {
    }

    @Override
    public void geocoder(String ak, String address, String city, String mcode, ApiCallback<GeocoderEntity> apiCallback) {
        //请求登录接口
        ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
        apiFactory.setHttpClient(apiFactory.genericClient());
        apiFactory.initRetrofit(Config.API_HOST_BAIDU_MAP);
        GeocodingApi geocodingAPI = apiFactory.createApi(GeocodingApi.class);
        Call<GeocoderEntity> call;
        call = geocodingAPI.geocoder(Config.API_OUTPUT, ak, address, city, mcode);
        Type type = new TypeToken<GeocoderEntity>() {
        }.getType();
        new ApiEngine<GeocoderEntity>(apiCallback, call, type);
    }

    @Override
    public void geocoder(String ak, double latitude, double longitude, int pois, String mcode, ApiCallback<ReverseGeocodingEntity> apiCallback) {
        String location = latitude + "," + longitude;
        //请求登录接口
        ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
        apiFactory.setHttpClient(apiFactory.genericClient());
        apiFactory.initRetrofit(Config.API_HOST_BAIDU_MAP);
        GeocodingApi geocodingAPI = apiFactory.createApi(GeocodingApi.class);
        Call<ReverseGeocodingEntity> call;
        call = geocodingAPI.geocoder(Config.API_OUTPUT, ak, Config.COORDTYPE_BAIDU, location, pois, mcode);
        Type type = new TypeToken<ReverseGeocodingEntity>() {
        }.getType();
        new ApiEngine<ReverseGeocodingEntity>(apiCallback, call, type);
    }
}
