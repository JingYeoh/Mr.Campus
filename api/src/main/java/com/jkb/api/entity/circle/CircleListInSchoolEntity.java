package com.jkb.api.entity.circle;

import java.util.List;

/**
 * 学校中的圈子列表实体类
 * Created by JustKiddingBaby on 2016/11/14.
 */

public class CircleListInSchoolEntity {


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
        private int circle_id;
        private String circle_name;
        private String type;
        private String picture;
        private String introduction;
        private int count_of_dynamic;
        private int count_of_subscription;
        private double circle_longitude;
        private double circle_latitude;
        private String created_at;
        private int has_subscribe;
        private int creator_id;
        private String creator_nickname;
        private String creator_avatar;

        public int getCircle_id() {
            return circle_id;
        }

        public void setCircle_id(int circle_id) {
            this.circle_id = circle_id;
        }

        public String getCircle_name() {
            return circle_name;
        }

        public void setCircle_name(String circle_name) {
            this.circle_name = circle_name;
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

        public int getCount_of_dynamic() {
            return count_of_dynamic;
        }

        public void setCount_of_dynamic(int count_of_dynamic) {
            this.count_of_dynamic = count_of_dynamic;
        }

        public int getCount_of_subscription() {
            return count_of_subscription;
        }

        public void setCount_of_subscription(int count_of_subscription) {
            this.count_of_subscription = count_of_subscription;
        }

        public double getCircle_longitude() {
            return circle_longitude;
        }

        public void setCircle_longitude(double circle_longitude) {
            this.circle_longitude = circle_longitude;
        }

        public double getCircle_latitude() {
            return circle_latitude;
        }

        public void setCircle_latitude(double circle_latitude) {
            this.circle_latitude = circle_latitude;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getHas_subscribe() {
            return has_subscribe;
        }

        public void setHas_subscribe(int has_subscribe) {
            this.has_subscribe = has_subscribe;
        }

        public int getCreator_id() {
            return creator_id;
        }

        public void setCreator_id(int creator_id) {
            this.creator_id = creator_id;
        }

        public String getCreator_nickname() {
            return creator_nickname;
        }

        public void setCreator_nickname(String creator_nickname) {
            this.creator_nickname = creator_nickname;
        }

        public String getCreator_avatar() {
            return creator_avatar;
        }

        public void setCreator_avatar(String creator_avatar) {
            this.creator_avatar = creator_avatar;
        }
    }
}
