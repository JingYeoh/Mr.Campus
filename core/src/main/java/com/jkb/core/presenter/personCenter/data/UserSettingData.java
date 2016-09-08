package com.jkb.core.presenter.personCenter.data;

import android.graphics.Bitmap;

import com.jkb.model.utils.StringUtils;

/**
 * 用户设置的缓存信息
 * Created by JustKiddingBaby on 2016/8/24.
 */

public class UserSettingData {

    private String name;
    private String nickName;
    private String UID;
    private String sex;
    private String bref_introduction;
    private String phone;
    private String email;
    private String createTime;
    private String headImg;
    private String backGround;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getBackGround() {
        return backGround;
    }

    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }
}
