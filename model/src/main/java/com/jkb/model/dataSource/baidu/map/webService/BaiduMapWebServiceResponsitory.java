package com.jkb.model.dataSource.baidu.map.webService;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.entity.baidu.map.webService.GeocoderEntity;
import com.jkb.api.entity.baidu.map.webService.ReverseGeocodingEntity;

/**
 * 百度地图的Web服务的数据仓库类
 * Created by JustKiddingBaby on 2016/8/13.
 */

public class BaiduMapWebServiceResponsitory implements BaiduMapWebServiceDataSource {

    private static BaiduMapWebServiceResponsitory INSTANCE = null;


    public static BaiduMapWebServiceResponsitory getInstance(@NonNull BaiduMapWebServiceDataSource localDataSource,
                                                             @NonNull BaiduMapWebServiceDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new BaiduMapWebServiceResponsitory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    private BaiduMapWebServiceDataSource localDataSource;
    private BaiduMapWebServiceDataSource remoteDataSource;

    private BaiduMapWebServiceResponsitory(@NonNull BaiduMapWebServiceDataSource localDataSource,
                                           @NonNull BaiduMapWebServiceDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void geocoder(String ak, String address, String city, String mcode, ApiCallback<GeocoderEntity> apiCallback) {
        remoteDataSource.geocoder(ak, address, city, mcode, apiCallback);
    }

    @Override
    public void geocoder(String ak, double latitude, double longitude, int pois, String mcode, ApiCallback<ReverseGeocodingEntity> apiCallback) {
        remoteDataSource.geocoder(ak, latitude, longitude, pois, mcode, apiCallback);
    }
}
