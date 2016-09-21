package com.jkb.core.contract.dynamicDetail.data;

/**
 * 动态详情：用户数据
 * Created by JustKiddingBaby on 2016/9/17.
 */

public class DynamicDetailUserData {
    private int id;
    private String avatar;
    private String nickName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
