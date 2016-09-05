package com.jkb.core.contract.function.index.data.dynamic.entity;

/**
 * 圈子用户的数据实体类
 * Created by JustKiddingBaby on 2016/9/3.
 */

public class DynamicUserData {

    private int id;
    private String nickName;
    private String avatar;

    public DynamicUserData(int id, String nickName, String avatar) {
        this.id = id;
        this.nickName = nickName;
        this.avatar = avatar;
    }

    public DynamicUserData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
