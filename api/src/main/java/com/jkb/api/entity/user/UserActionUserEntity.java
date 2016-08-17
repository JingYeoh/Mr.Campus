package com.jkb.api.entity.user;

import java.util.List;

/**
 * 获取用户关注、喜欢等接口返回的数据实体类
 * 有关用户的数据
 * Created by JustKiddingBaby on 2016/8/17.
 */

public class UserActionUserEntity {

    /**
     * total : 1
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * next_page_url :
     * prev_page_url :
     * from : 1
     * to : 1
     * data : [{"id":160,"UID":"9184348","nickname":"lonny.upton","avatar":"http://lorempixel.com/640/480/?49165","sex":"女","name":"Kali Zieme的菌菌","bref_introduction":"Quos nostrum et rerum...","background":"http://lorempixel.com/640/480/?60180","longitude":40.084106,"latitude":77.570465,"created_at":"2016-08-03 16:23:45"}]
     */

    private UserBean user;
    /**
     * user : {"total":1,"per_page":10,"current_page":1,"last_page":1,"next_page_url":"","prev_page_url":"","from":1,"to":1,"data":[{"id":160,"UID":"9184348","nickname":"lonny.upton","avatar":"http://lorempixel.com/640/480/?49165","sex":"女","name":"Kali Zieme的菌菌","bref_introduction":"Quos nostrum et rerum...","background":"http://lorempixel.com/640/480/?60180","longitude":40.084106,"latitude":77.570465,"created_at":"2016-08-03 16:23:45"}]}
     * error : ["action不在取值范围内."]
     */

    private List<String> error;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }

    public static class UserBean {
        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private String next_page_url;
        private String prev_page_url;
        private int from;
        private int to;
        /**
         * id : 160
         * UID : 9184348
         * nickname : lonny.upton
         * avatar : http://lorempixel.com/640/480/?49165
         * sex : 女
         * name : Kali Zieme的菌菌
         * bref_introduction : Quos nostrum et rerum...
         * background : http://lorempixel.com/640/480/?60180
         * longitude : 40.084106
         * latitude : 77.570465
         * created_at : 2016-08-03 16:23:45
         */

        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public String getNext_page_url() {
            return next_page_url;
        }

        public void setNext_page_url(String next_page_url) {
            this.next_page_url = next_page_url;
        }

        public String getPrev_page_url() {
            return prev_page_url;
        }

        public void setPrev_page_url(String prev_page_url) {
            this.prev_page_url = prev_page_url;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
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
        }
    }
}
