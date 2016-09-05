package com.jkb.core.contract.function.index.data.dynamic.entity;

/**
 * 动态圈子数据
 * Created by JustKiddingBaby on 2016/9/3.
 */

public class DynamicCircleData {

    private int id;
    private String name;
    private String type;
    private String pictureUrl;

    public DynamicCircleData(int id, String name, String type, String pictureUrl) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.pictureUrl = pictureUrl;
    }

    public DynamicCircleData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
