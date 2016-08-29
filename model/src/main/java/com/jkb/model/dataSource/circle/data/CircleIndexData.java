package com.jkb.model.dataSource.circle.data;

import android.graphics.Bitmap;

/**
 * 圈子首页的数据
 * Created by JustKiddingBaby on 2016/8/29.
 */

public class CircleIndexData {

    private String circleName;
    private String circleType;
    private String circleIntroducton;
    private int subsribeCount;//订阅数
    private int operationCount;//动态数
    private Bitmap picture;


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

    public int getOperationCount() {
        return operationCount;
    }

    public void setOperationCount(int operationCount) {
        this.operationCount = operationCount;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }
}
