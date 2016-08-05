package com.jkb.model.entering.login;

/**
 * 获取的第三方数据类
 * Created by JustKiddingBaby on 2016/8/3.
 */
public class ThirdPlatformData {

    private String token;
    private String gender;//性别
    private String icon;//头像
    private String userID;//用户id
    private String nickname;//昵称
    private String identity_type;//登录类型

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIdentity_type() {
        return identity_type;
    }

    public void setIdentity_type(String identity_type) {
        this.identity_type = identity_type;
    }
}
