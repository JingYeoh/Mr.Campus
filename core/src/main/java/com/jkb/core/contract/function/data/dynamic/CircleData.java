package com.jkb.core.contract.function.data.dynamic;

/**
 * 动态：圈子
 * Created by JustKiddingBaby on 2016/9/8.
 */

public class CircleData extends DynamicBaseData {

    private int id;//圈子id
    private String name;//圈子名称
    private String type;//圈子类型
    private String picture;//圈子图片
    private String introduction;//圈子介绍
    private double longitude;
    private double latitude;
    private String create_time;//创建时间
    private int dynamics_count;//动态数
    private int operation_count;//订阅书
    private boolean hasSubscribe;//是否关注
    private User user;//圈子作者信息
    private School school;//圈子所在学校信息

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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
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

    public boolean isHasSubscribe() {
        return hasSubscribe;
    }

    public void setHasSubscribe(boolean hasSubscribe) {
        this.hasSubscribe = hasSubscribe;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    /**
     * 圈子作者
     */
    public static class User {
        private int id;//圈子id
        private String circle_owner_nickname;//昵称
        private String circle_owner_avatar;//头像

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCircle_owner_nickname() {
            return circle_owner_nickname;
        }

        public void setCircle_owner_nickname(String circle_owner_nickname) {
            this.circle_owner_nickname = circle_owner_nickname;
        }

        public String getCircle_owner_avatar() {
            return circle_owner_avatar;
        }

        public void setCircle_owner_avatar(String circle_owner_avatar) {
            this.circle_owner_avatar = circle_owner_avatar;
        }
    }

    public static class School {
        private int id;//学校id
        private String sname;//学校名称
        private String badge;//学校销毁

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public String getBadge() {
            return badge;
        }

        public void setBadge(String badge) {
            this.badge = badge;
        }
    }
}
