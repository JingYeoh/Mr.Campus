package com.jkb.api.entity.circle;

import java.util.List;

/**
 * 圈子成员的数据实体类
 * Created by JustKiddingBaby on 2016/11/3.
 */

public class CircleAttentionUserListEntity {

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private String next_page_url;
    private String prev_page_url;
    private int from;
    private int to;

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
        private int count_of_fan;
        private int count_of_visitor;
        private int count_of_attention;
        private int school_id;
        private double longitude;
        private double latitude;
        private String rc_token;
        private String created_at;
        private int isBlack;
        private int hasAttention;
        /**
         * id : 1
         * sname : 金陵科技学院
         * badge : http://image.lyfsmile.cn/school/jlkj.jpg
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

        public int getCount_of_attention() {
            return count_of_attention;
        }

        public void setCount_of_attention(int count_of_attention) {
            this.count_of_attention = count_of_attention;
        }

        public int getSchool_id() {
            return school_id;
        }

        public void setSchool_id(int school_id) {
            this.school_id = school_id;
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

        public String getRc_token() {
            return rc_token;
        }

        public void setRc_token(String rc_token) {
            this.rc_token = rc_token;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getIsBlack() {
            return isBlack;
        }

        public void setIsBlack(int isBlack) {
            this.isBlack = isBlack;
        }

        public int getHasAttention() {
            return hasAttention;
        }

        public void setHasAttention(int hasAttention) {
            this.hasAttention = hasAttention;
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
