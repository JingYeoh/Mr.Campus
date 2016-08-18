package com.jkb.core.presenter.usersList.data;

/**
 * 能用到的用户数据
 * Created by JustKiddingBaby on 2016/8/18.
 */

public class UserData {

    private String nickname;//昵称
    private String avatar;//头像地址
    private String sex;//性别
    private String bref_introduction;//签名
    private boolean isAttentioned = false;//是否被关注

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBref_introduction() {
        return bref_introduction;
    }

    public void setBref_introduction(String bref_introduction) {
        this.bref_introduction = bref_introduction;
    }

    public boolean isAttentioned() {
        return isAttentioned;
    }

    public void setAttentioned(boolean attentioned) {
        isAttentioned = attentioned;
    }
}
