package com.jkb.core.contract.menu.data;

/**
 * 好友数据
 * Created by JustKiddingBaby on 2016/9/6.
 */

public class FriendsData {
    private String headImgUrl;//头像地址
    private String nickName;//昵称
    private String sex;//性别
    private String bref;//简介
    private int id;//用户id

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBref() {
        return bref;
    }

    public void setBref(String bref) {
        this.bref = bref;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
