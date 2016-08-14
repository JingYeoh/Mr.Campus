package com.jkb.api.entity.circle;

import java.util.List;

/**
 * 创建圈子的数据实体类
 * Created by JustKiddingBaby on 2016/8/14.
 */

public class CircleCreateEntity {


    /**
     * user_id : 1
     * school_id : 1
     * name : 学习圈
     * picture : http://image.lyfsmile.cn/circle/image5329_1470120181.jpg
     * introduction : 学习的圈子
     * longitude : 123.45
     * latitude : 123.45
     * created_at : 2016-08-02 14:43:01
     * id : 1
     */

    private CircleInfoBean circleInfo;
    /**
     * circleInfo : {"user_id":1,"school_id":1,"name":"学习圈","picture":"http://image.lyfsmile.cn/circle/image5329_1470120181.jpg","introduction":"学习的圈子","longitude":123.45,"latitude":123.45,"created_at":"2016-08-02 14:43:01","id":1}
     * user_id : ["The user id field is required."]
     * name : ["The name field is required."]
     * introduction : ["The introduction field is required."]
     * longitude : ["The longitude field is required."]
     * latitude : ["The latitude field is required."]
     */

    private List<String> user_id;
    private List<String> name;
    private List<String> introduction;
    private List<String> longitude;
    private List<String> latitude;

    public CircleInfoBean getCircleInfo() {
        return circleInfo;
    }

    public void setCircleInfo(CircleInfoBean circleInfo) {
        this.circleInfo = circleInfo;
    }

    public List<String> getUser_id() {
        return user_id;
    }

    public void setUser_id(List<String> user_id) {
        this.user_id = user_id;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getIntroduction() {
        return introduction;
    }

    public void setIntroduction(List<String> introduction) {
        this.introduction = introduction;
    }

    public List<String> getLongitude() {
        return longitude;
    }

    public void setLongitude(List<String> longitude) {
        this.longitude = longitude;
    }

    public List<String> getLatitude() {
        return latitude;
    }

    public void setLatitude(List<String> latitude) {
        this.latitude = latitude;
    }

    public static class CircleInfoBean {
        private int user_id;
        private int school_id;
        private String name;
        private String picture;
        private String introduction;
        private double longitude;
        private double latitude;
        private String created_at;
        private int id;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getSchool_id() {
            return school_id;
        }

        public void setSchool_id(int school_id) {
            this.school_id = school_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
