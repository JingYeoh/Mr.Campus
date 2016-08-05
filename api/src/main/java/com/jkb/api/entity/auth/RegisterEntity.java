package com.jkb.api.entity.auth;

import java.util.List;

/**
 * 注册的请求接口返回的实体类
 * Created by JustKiddingBaby on 2016/8/1.
 */
public class RegisterEntity {


    /**
     * identifier : ["The identifier has already been taken."]
     * error : ["密码错误."]
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9...
     * userInfo : {"id":1,"UID":"1000001","nickname":"l1","avatar":null,"sex":"男","name":null,"bref_introduction":null,"background":null,"longitude":null,"latitude":null,"created_at":"2016-08-02 15:36:34","email":"664275973@qq.com","phone":null,"schoolInfo":null}
     */

    private String token;
    /**
     * id : 1
     * UID : 1000001
     * nickname : l1
     * avatar : null
     * sex : 男
     * name : null
     * bref_introduction : null
     * background : null
     * longitude : null
     * latitude : null
     * created_at : 2016-08-02 15:36:34
     * email : 664275973@qq.com
     * phone : null
     * schoolInfo : null
     */

    private UserInfoBean userInfo;
    private List<String> identifier;
    private List<String> error;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }

    public static class UserInfoBean {
        private int id;
        private String UID;
        private String nickname;
        private Object avatar;
        private String sex;
        private Object name;
        private Object bref_introduction;
        private Object background;
        private Object longitude;
        private Object latitude;
        private String created_at;
        private String email;
        private Object phone;
        private Object schoolInfo;

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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public Object getSchoolInfo() {
            return schoolInfo;
        }

        public void setSchoolInfo(Object schoolInfo) {
            this.schoolInfo = schoolInfo;
        }
    }
}
