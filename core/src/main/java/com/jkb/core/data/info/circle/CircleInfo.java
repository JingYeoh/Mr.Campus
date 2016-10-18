package com.jkb.core.data.info.circle;

import com.jkb.core.data.info.user.UserInfo;

/**
 * 圈子的地图标记信息
 * Created by JustKiddingBaby on 2016/10/4.
 */

public class CircleInfo {

    private int circleId;
    private String pictureUrl;
    private String circleName;
    private int operatorCount;
    private int dynamicsCount;
    private double longitude;
    private double latitude;
    private String circleType;
    private String introduction;

    private UserInfo user;//作者信息


    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public int getOperatorCount() {
        return operatorCount;
    }

    public void setOperatorCount(int operatorCount) {
        this.operatorCount = operatorCount;
    }

    public int getDynamicsCount() {
        return dynamicsCount;
    }

    public void setDynamicsCount(int dynamicsCount) {
        this.dynamicsCount = dynamicsCount;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getCircleType() {
        return circleType;
    }

    public void setCircleType(String circleType) {
        this.circleType = circleType;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}
