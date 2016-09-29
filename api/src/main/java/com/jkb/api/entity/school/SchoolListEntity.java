package com.jkb.api.entity.school;

import java.util.List;

/**
 * 学校列表的实体类
 * Created by JustKiddingBaby on 2016/9/29.
 */

public class SchoolListEntity {


    /**
     * total : 1
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * next_page_url :
     * prev_page_url :
     * from : 1
     * to : 1
     * data : [{"id":1,"sname":"金陵科技学院","badge":"/home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg","summary":"jlkj","longitude":118.905518,"latitude":31.912587}]
     */

    private SchoolBean school;

    public SchoolBean getSchool() {
        return school;
    }

    public void setSchool(SchoolBean school) {
        this.school = school;
    }

    public static class SchoolBean {
        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private String next_page_url;
        private String prev_page_url;
        private int from;
        private int to;
        /**
         * id : 1
         * sname : 金陵科技学院
         * badge : /home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg
         * summary : jlkj
         * longitude : 118.905518
         * latitude : 31.912587
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
            private String sname;
            private String badge;
            private String summary;
            private double longitude;
            private double latitude;

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
        }
    }
}
