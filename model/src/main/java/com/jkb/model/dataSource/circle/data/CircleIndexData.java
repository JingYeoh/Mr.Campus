package com.jkb.model.dataSource.circle.data;

/**
 * 圈子首页的数据
 * Created by JustKiddingBaby on 2016/8/29.
 */

public class CircleIndexData {

    private String circleName;
    private String circleType;
    private String circleIntroducton;
    private int subsribeCount;//订阅数
    private int dynamicsCount;//动态数
    private String picture;
    private boolean hasSubscribe;//是否被用戶订阅
    private boolean hasInCommonUse;//是否设置为常用圈子
    private int user_id;//创建圈子的用户id

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public String getCircleType() {
        return circleType;
    }

    public void setCircleType(String circleType) {
        this.circleType = circleType;
    }

    public String getCircleIntroducton() {
        return circleIntroducton;
    }

    public void setCircleIntroducton(String circleIntroducton) {
        this.circleIntroducton = circleIntroducton;
    }

    public int getSubsribeCount() {
        return subsribeCount;
    }

    public void setSubsribeCount(int subsribeCount) {
        this.subsribeCount = subsribeCount;
    }

    public int getDynamicsCount() {
        return dynamicsCount;
    }

    public void setDynamicsCount(int dynamicsCount) {
        this.dynamicsCount = dynamicsCount;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isHasSubscribe() {
        return hasSubscribe;
    }

    public void setHasSubscribe(boolean hasSubscribe) {
        this.hasSubscribe = hasSubscribe;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public boolean isHasInCommonUse() {
        return hasInCommonUse;
    }

    public void setHasInCommonUse(boolean hasInCommonUse) {
        this.hasInCommonUse = hasInCommonUse;
    }
}
