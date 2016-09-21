package com.jkb.api.entity.comment;

import java.util.List;

/**
 * 评论和回复的单个数据实体类
 * Created by JustKiddingBaby on 2016/9/21.
 */

public class Comment$ReplyEntity {

    /**
     * total : 3
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * next_page_url :
     * prev_page_url :
     * from : 1
     * to : 3
     * data : [{"reply_user_id":2,"reply_user_nickname":"assunta.bayer","reply_user_avatar":"http://lorempixel.com/640/480/?73711","target_user_id":2,"target_user_nickname":"assunta.bayer","target_user_avatar":"http://lorempixel.com/640/480/?73711","reply":"啦啦啦","created_at":"2016-09-21 15:19:32"},{"reply_user_id":2,"reply_user_nickname":"assunta.bayer","reply_user_avatar":"http://lorempixel.com/640/480/?73711","target_user_id":2,"target_user_nickname":"assunta.bayer","target_user_avatar":"http://lorempixel.com/640/480/?73711","reply":"啦啦啦","created_at":"2016-09-21 15:19:31"},{"reply_user_id":2,"reply_user_nickname":"assunta.bayer","reply_user_avatar":"http://lorempixel.com/640/480/?73711","target_user_id":2,"target_user_nickname":"assunta.bayer","target_user_avatar":"http://lorempixel.com/640/480/?73711","reply":"回复回复啦啦啦~","created_at":"2016-09-21 15:19:26"}]
     */

    private ReplyBean reply;
    /**
     * comment_user_id : 2
     * comment_user_nickname : assunta.bayer
     * comment_user_avatar : http://lorempixel.com/640/480/?73711
     * comment : 评论啦啦啦
     * created_at : 2016-09-21 15:12:21
     */

    private List<CommentBean> comment;

    public ReplyBean getReply() {
        return reply;
    }

    public void setReply(ReplyBean reply) {
        this.reply = reply;
    }

    public List<CommentBean> getComment() {
        return comment;
    }

    public void setComment(List<CommentBean> comment) {
        this.comment = comment;
    }

    public static class ReplyBean {
        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private String next_page_url;
        private String prev_page_url;
        private int from;
        private int to;
        /**
         * reply_user_id : 2
         * reply_user_nickname : assunta.bayer
         * reply_user_avatar : http://lorempixel.com/640/480/?73711
         * target_user_id : 2
         * target_user_nickname : assunta.bayer
         * target_user_avatar : http://lorempixel.com/640/480/?73711
         * reply : 啦啦啦
         * created_at : 2016-09-21 15:19:32
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
            private int reply_user_id;
            private String reply_user_nickname;
            private String reply_user_avatar;
            private int target_user_id;
            private String target_user_nickname;
            private String target_user_avatar;
            private String reply;
            private String created_at;

            public int getReply_user_id() {
                return reply_user_id;
            }

            public void setReply_user_id(int reply_user_id) {
                this.reply_user_id = reply_user_id;
            }

            public String getReply_user_nickname() {
                return reply_user_nickname;
            }

            public void setReply_user_nickname(String reply_user_nickname) {
                this.reply_user_nickname = reply_user_nickname;
            }

            public String getReply_user_avatar() {
                return reply_user_avatar;
            }

            public void setReply_user_avatar(String reply_user_avatar) {
                this.reply_user_avatar = reply_user_avatar;
            }

            public int getTarget_user_id() {
                return target_user_id;
            }

            public void setTarget_user_id(int target_user_id) {
                this.target_user_id = target_user_id;
            }

            public String getTarget_user_nickname() {
                return target_user_nickname;
            }

            public void setTarget_user_nickname(String target_user_nickname) {
                this.target_user_nickname = target_user_nickname;
            }

            public String getTarget_user_avatar() {
                return target_user_avatar;
            }

            public void setTarget_user_avatar(String target_user_avatar) {
                this.target_user_avatar = target_user_avatar;
            }

            public String getReply() {
                return reply;
            }

            public void setReply(String reply) {
                this.reply = reply;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }
        }
    }

    public static class CommentBean {
        private int comment_user_id;
        private String comment_user_nickname;
        private String comment_user_avatar;
        private String comment;
        private String created_at;

        public int getComment_user_id() {
            return comment_user_id;
        }

        public void setComment_user_id(int comment_user_id) {
            this.comment_user_id = comment_user_id;
        }

        public String getComment_user_nickname() {
            return comment_user_nickname;
        }

        public void setComment_user_nickname(String comment_user_nickname) {
            this.comment_user_nickname = comment_user_nickname;
        }

        public String getComment_user_avatar() {
            return comment_user_avatar;
        }

        public void setComment_user_avatar(String comment_user_avatar) {
            this.comment_user_avatar = comment_user_avatar;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }
    }
}
