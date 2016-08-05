package com.jkb.api.entity.auth;

import java.util.List;

/**
 * 重置密码的返回信息实体类
 * Created by JustKiddingBaby on 2016/8/5.
 */

public class ResetPasswordEntity {


    /**
     * id : 1
     * UID : 1000001
     * nickname : 231
     * avatar : null
     * sex : null
     * name : null
     * bref_introduction : null
     * background : null
     * longitude : null
     * latitude : null
     * created_at : 2016-07-27 16:40:27
     * email : null
     * phone : null
     * schoolInfo : {"id":1,"sname":"金陵科技学院","badge":null,"summary":null}
     */

    private UserInfoBean userInfo;
    /**
     * userInfo : {"id":1,"UID":"1000001","nickname":"231","avatar":null,"sex":null,"name":null,"bref_introduction":null,"background":null,"longitude":null,"latitude":null,"created_at":"2016-07-27 16:40:27","email":null,"phone":null,"schoolInfo":{"id":1,"sname":"金陵科技学院","badge":null,"summary":null}}
     * identifier : ["The identifier must be 11 digits."]
     */

    private List<String> identifier;

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public List<String> getIdentifier() {
        return identifier;
    }

    public void setIdentifier(List<String> identifier) {
        this.identifier = identifier;
    }

    public static class UserInfoBean {
        private int id;
        private String UID;
        private String nickname;
        private Object avatar;
        private Object sex;
        private Object name;
        private Object bref_introduction;
        private Object background;
        private Object longitude;
        private Object latitude;
        private String created_at;
        private Object email;
        private Object phone;
        /**
         * id : 1
         * sname : 金陵科技学院
         * badge : null
         * summary : null
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

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }

        public Object getSex() {
            return sex;
        }

        public void setSex(Object sex) {
            this.sex = sex;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public Object getBref_introduction() {
            return bref_introduction;
        }

        public void setBref_introduction(Object bref_introduction) {
            this.bref_introduction = bref_introduction;
        }

        public Object getBackground() {
            return background;
        }

        public void setBackground(Object background) {
            this.background = background;
        }

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
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
            private Object badge;
            private Object summary;

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

            public Object getBadge() {
                return badge;
            }

            public void setBadge(Object badge) {
                this.badge = badge;
            }

            public Object getSummary() {
                return summary;
            }

            public void setSummary(Object summary) {
                this.summary = summary;
            }
        }
    }
}
