package com.jkb.model.dataSource.baidu.map.webService;

import com.jkb.api.ApiCallback;
import com.jkb.api.entity.baidu.map.webService.GeocoderEntity;
import com.jkb.api.entity.baidu.map.webService.ReverseGeocodingEntity;

/**
 * 百度地图的Web服务API数据来源类
 * Created by JustKiddingBaby on 2016/8/13.
 */

public interface BaiduMapWebServiceDataSource {
    /**
     * 地址解析
     */
    void geocoder(String ak, String address, String city, String mcode, ApiCallback<GeocoderEntity> apiCallback);

    /**
     * 地址逆向解析
     */
    void geocoder(String ak, double latitude, double longitude, int pois, String mcode, ApiCallback<ReverseGeocodingEntity> apiCallback);
}
