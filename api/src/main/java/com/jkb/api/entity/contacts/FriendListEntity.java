package com.jkb.api.entity.contacts;

import java.util.List;

/**
 * 好友列表实体类
 * Created by JustKiddingBaby on 2016/9/6.
 */

public class FriendListEntity {

    /**
     * total : 3
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * next_page_url :
     * prev_page_url :
     * from : 1
     * to : 3
     * data : [{"id":29,"UID":"7138862","nickname":"gusikowski.nikki","avatar":"http://lorempixel.com/640/480/?37949","sex":"男","name":"Erick Feest III的菌菌","bref_introduction":"Consequatur excepturi rerum voluptatem animi incidunt. Quod enim et id itaque vero aut. Vel eos optio velit sint et totam. Deserunt omnis mollitia aut blanditiis aut iusto natus.","background":"http://lorempixel.com/640/480/?60683","longitude":-175.319306,"latitude":52.232495,"created_at":"2016-08-03 16:23:30","school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg","summary":"jlkj","longitude":"118.905518","latitude":"31.912587"}},{"id":218,"UID":"5202464","nickname":"morar.betsy","avatar":"http://lorempixel.com/640/480/?14519","sex":"女","name":"Ms. Aaliyah Schneider Jr.的菌菌","bref_introduction":"Nobis vel nam aliquid voluptatem velit facere eveniet. Omnis repellendus beatae voluptatum veniam enim. Ut veniam maxime quia impedit in dolore autem exercitationem.","background":"http://lorempixel.com/640/480/?94508","longitude":-102.547721,"latitude":22.214497,"created_at":"2016-08-03 16:23:52","school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg","summary":"jlkj","longitude":"118.905518","latitude":"31.912587"}},{"id":278,"UID":"9195832","nickname":"valerie.wolf","avatar":"http://lorempixel.com/640/480/?21519","sex":"女","name":"Jayson Schimmel的菌菌","bref_introduction":"Dolores illum id et. Aut facilis ipsam est cupiditate impedit quos. Est natus hic numquam ut assumenda id.","background":"http://lorempixel.com/640/480/?43655","longitude":-98.267981,"latitude":-16.37927,"created_at":"2016-08-03 16:23:59","school":{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg","summary":"jlkj","longitude":"118.905518","latitude":"31.912587"}}]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private String next_page_url;
    private String prev_page_url;
    private int from;
    private int to;
    /**
     * id : 29
     * UID : 7138862
     * nickname : gusikowski.nikki
     * avatar : http://lorempixel.com/640/480/?37949
     * sex : 男
     * name : Erick Feest III的菌菌
     * bref_introduction : Consequatur excepturi rerum voluptatem animi incidunt. Quod enim et id itaque vero aut. Vel eos optio velit sint et totam. Deserunt omnis mollitia aut blanditiis aut iusto natus.
     * background : http://lorempixel.com/640/480/?60683
     * longitude : -175.319306
     * latitude : 52.232495
     * created_at : 2016-08-03 16:23:30
     * school : {"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg","summary":"jlkj","longitude":"118.905518","latitude":"31.912587"}
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
        /**
         * id : 1
         * sname : 金陵科技学院
         * badge : /home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg
         * summary : jlkj
         * longitude : 118.905518
         * latitude : 31.912587
         */

        private SchoolBean school;

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

        public SchoolBean getSchool() {
            return school;
        }

        public void setSchool(SchoolBean school) {
            this.school = school;
        }

        public static class SchoolBean {
            private int id;
            private String sname;
            private String badge;
            private String summary;
            private String longitude;
            private String latitude;

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

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }
        }
    }
}
