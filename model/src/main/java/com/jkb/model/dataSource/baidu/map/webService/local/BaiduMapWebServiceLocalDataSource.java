package com.jkb.model.dataSource.baidu.map.webService.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.entity.baidu.map.webService.GeocoderEntity;
import com.jkb.api.entity.baidu.map.webService.ReverseGeocodingEntity;
import com.jkb.model.dataSource.baidu.map.webService.BaiduMapWebServiceDataSource;

/**
 * 百度地图的Web服务的本地数据来源类
 * Created by JustKiddingBaby on 2016/8/13.
 */

public class BaiduMapWebServiceLocalDataSource implements BaiduMapWebServiceDataSource {

    private static BaiduMapWebServiceLocalDataSource INSTANCE = null;
    private Context applicationContext;

    private BaiduMapWebServiceLocalDataSource(@NonNull Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static BaiduMapWebServiceLocalDataSource getInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new BaiduMapWebServiceLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void geocoder(String ak, String address, String city, String mcode, ApiCallback<GeocoderEntity> apiCallback) {

    }

    @Override
    public void geocoder(String ak, double latitude, double longitude, int pois, String mcode, ApiCallback<ReverseGeocodingEntity> apiCallback) {

    }
}
