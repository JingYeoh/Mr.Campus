package com.jkb.api.entity.user;

import java.util.List;

/**
 * 获取用户数据的实体类
 * Created by JustKiddingBaby on 2016/8/17.
 */

public class UserInfoEntity {


    /**
     * id : 1
     * UID : 1000001
     * nickname : 231
     * avatar :
     * sex :
     * name :
     * bref_introduction :
     * background :
     * longitude : 123.5
     * latitude : 123.5
     * created_at : 2016-07-27 16:40:27
     * email :
     * phone :
     * attentionCount : 1
     * attentionUserCount : 20
     * fansCount : 0
     * visitorCount : 1
     * schoolInfo : {"id":1,"sname":"金陵科技学院","badge":"","summary":""}
     */

    private UserInfoBean userInfo;
    /**
     * userInfo : {"id":1,"UID":"1000001","nickname":"231","avatar":"","sex":"","name":"","bref_introduction":"","background":"","longitude":123.5,"latitude":123.5,"created_at":"2016-07-27 16:40:27","email":"","phone":"","attentionCount":1,"attentionUserCount":20,"fansCount":0,"visitorCount":1,"schoolInfo":{"id":1,"sname":"金陵科技学院","badge":"","summary":""}}
     * user : ["用户不存在."]
     */

    private List<String> user;

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public List<String> getUser() {
        return user;
    }

    public void setUser(List<String> user) {
        this.user = user;
    }

    public static class UserInfoBean {
        private int id;
        private String UID;
        private String nickname;
        private String avatar;
        private String sex;
        private String name;
        private String bref_introduction;
        private String background;
        private double longitude;
        private double latitude;
        private String created_at;
        private String email;
        private String phone;
        private int attentionCount;
        private int attentionUserCount;
        private int fansCount;
        private int visitorCount;
        /**
         * id : 1
         * sname : 金陵科技学院
         * badge :
         * summary :
         */

        private SchoolInfoBean schoolInfo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUID() {
            return UID;
        }

        public void setUID(String UID) {
            this.UID = UID;
        }

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getAttentionCount() {
            return attentionCount;
        }

        public void setAttentionCount(int attentionCount) {
            this.attentionCount = attentionCount;
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

        public int getVisitorCount() {
            return visitorCount;
        }

        public void setVisitorCount(int visitorCount) {
            this.visitorCount = visitorCount;
        }

        public SchoolInfoBean getSchoolInfo() {
            return schoolInfo;
        }

        public void setSchoolInfo(SchoolInfoBean schoolInfo) {
            this.schoolInfo = schoolInfo;
        }

        public static class SchoolInfoBean {
            private int id;
            private String sname;
            private String badge;
            private String summary;

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

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }
        }
    }
}
