package com.jkb.core.presenter.personCenter.data;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心要显示的用户数据
 * Created by JustKiddingBaby on 2016/8/22.
 */

public class UserData {

    private String headImg;
    private String backGround;//背景图
    private String name;
    private String nickName;
    private String sign;
    private int visitorCount;
    private int attentionUserCount;
    private int fansCount;
    private List<CircleData> circleDatas;


    public UserData() {
        circleDatas = new ArrayList<>();
    }


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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getVisitorCount() {
        return visitorCount;
    }

    public void setVisitorCount(int visitorCount) {
        this.visitorCount = visitorCount;
    }

    public int getAttentionUserCount() {
        return attentionUserCount;
    }

    public void setAttentionUserCount(int attentionUserCount) {
        this.attentionUserCount = attentionUserCount;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public List<CircleData> getCircleDatas() {
        return circleDatas;
    }

    public void setCircleDatas(List<CircleData> circleDatas) {
        this.circleDatas = circleDatas;
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
