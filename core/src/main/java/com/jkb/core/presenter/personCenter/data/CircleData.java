package com.jkb.core.presenter.personCenter.data;

/**
 * 圈子显示用到的数据
 * Created by JustKiddingBaby on 2016/8/22.
 */

public class CircleData {

    private String circleName;//圈子名称
    private String circleType;//圈子类型
    private String pictureUrl;//图片的地址
    private int dynamics_count;//动态总数.
    private int operation_count;//订阅总数


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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getDynamics_count() {
        return dynamics_count;
    }

    public void setDynamics_count(int dynamics_count) {
        this.dynamics_count = dynamics_count;
    }

    public int getOperation_count() {
        return operation_count;
    }

    public void setOperation_count(int operation_count) {
        this.operation_count = operation_count;
    }
}
