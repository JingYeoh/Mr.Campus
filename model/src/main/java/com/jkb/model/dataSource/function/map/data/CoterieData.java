package com.jkb.model.dataSource.function.map.data;

import android.graphics.Bitmap;

/**
 * 圈子的信息类
 * Created by JustKiddingBaby on 2016/7/28.
 */
public class CoterieData {
    //头像
    private Bitmap coterieHeadImg;
    //名称
    private String coterieName;
    //圈子ID
    private String coterieId;
    //签名
    private String sign;
    //坐标
    private String lng, lat;

    public CoterieData(Bitmap coterieHeadImg, String coterieName, String coterieId, String sign, String lng, String lat) {
        this.coterieHeadImg = coterieHeadImg;
        this.coterieName = coterieName;
        this.coterieId = coterieId;
        this.sign = sign;
        this.lng = lng;
        this.lat = lat;
    }

    public Bitmap getCoterieHeadImg() {
        return coterieHeadImg;
    }

    public void setCoterieHeadImg(Bitmap coterieHeadImg) {
        this.coterieHeadImg = coterieHeadImg;
    }

    public String getCoterieName() {
        return coterieName;
    }

    public void setCoterieName(String coterieName) {
        this.coterieName = coterieName;
    }

    public String getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(String coterieId) {
        this.coterieId = coterieId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
