package com.jkb.model.dataSource.function.map.data;

import android.graphics.Bitmap;

/**
 * UI中能用到的用户信息
 * Created by JustKiddingBaby on 2016/7/28.
 */
public class UserData {

    //头像
    private Bitmap headImg;
    //用户名
    private String userName;
    //用户的ID
    private String userId;
    //性别
    private String sex;
    //签名
    private String sign;
    //坐标点
    private String lng, lat;


    public UserData(Bitmap headImg, String userName, String userId, String sex, String sign, String lng, String lat) {
        this.headImg = headImg;
        this.userName = userName;
        this.userId = userId;
        this.sex = sex;
        this.sign = sign;
        this.lng = lng;
        this.lat = lat;
    }

    public Bitmap getHeadImg() {
        return headImg;
    }

    public void setHeadImg(Bitmap headImg) {
        this.headImg = headImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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
