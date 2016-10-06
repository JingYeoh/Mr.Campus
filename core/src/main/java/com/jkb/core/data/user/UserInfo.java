package com.jkb.core.data.user;

/**
 * 用户的信息类
 * Created by JustKiddingBaby on 2016/10/4.
 */

public class UserInfo {
    private int id;
    private String avatar;
    private String nickName;
    private String UID;
    private String sex;
    private String name;
    private String bref_introduction;
    private String background;
    private int count_of_fan;
    private int count_of_visitor;
    private boolean has_attention;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBref_introduction() {
        return bref_introduction;
    }

    public void setBref_introduction(String bref_introduction) {
        this.bref_introduction = bref_introduction;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getCount_of_fan() {
        return count_of_fan;
    }

    public void setCount_of_fan(int count_of_fan) {
        this.count_of_fan = count_of_fan;
    }

    public int getCount_of_visitor() {
        return count_of_visitor;
    }

    public void setCount_of_visitor(int count_of_visitor) {
        this.count_of_visitor = count_of_visitor;
    }

    public boolean isHas_attention() {
        return has_attention;
    }

    public void setHas_attention(boolean has_attention) {
        this.has_attention = has_attention;
    }
}
