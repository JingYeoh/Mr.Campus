package com.jkb.api.entity.comment;

import java.util.List;

/**
 * 评论和回复的单个数据实体类
 * Created by JustKiddingBaby on 2016/9/21.
 */

public class Comment$ReplyEntity {


    /**
     * comment_user_id : 2
     * comment_user_nickname : assunta.bayer
     * comment_user_avatar : http://lorempixel.com/640/480/?73711
     * comment : 回复评论2
     * created_at : 2016-09-22 17:07:03
     * count_of_like : 1
     * has_like : 1
     */

    private CommentBean comment;
    /**
     * reply_user_id : 2
     * reply_user_nickname : assunta.bayer
     * reply_user_avatar : http://lorempixel.com/640/480/?73711
     * target_user_id : 2
     * target_user_nickname : assunta.bayer
     * target_user_avatar : http://lorempixel.com/640/480/?73711
     * reply_id : 10072
     * reply : 哈哈
     * created_at : 2016-09-22 18:03:48
     */

    private List<ReplyBean> reply;

    public CommentBean getComment() {
        return comment;
    }

    public void setComment(CommentBean comment) {
        this.comment = comment;
    }

    public List<ReplyBean> getReply() {
        return reply;
    }

    public void setReply(List<ReplyBean> reply) {
        this.reply = reply;
    }

    public static class CommentBean {
        private int comment_user_id;
        private String comment_user_nickname;
        private String comment_user_avatar;
        private String comment;
        private String created_at;
        private int count_of_like;
        private int has_like;

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

        public int getCount_of_like() {
            return count_of_like;
        }

        public void setCount_of_like(int count_of_like) {
            this.count_of_like = count_of_like;
        }

        public int getHas_like() {
            return has_like;
        }

        public void setHas_like(int has_like) {
            this.has_like = has_like;
        }
    }

    public static class ReplyBean {
        private int reply_user_id;
        private String reply_user_nickname;
        private String reply_user_avatar;
        private int target_user_id;
        private String target_user_nickname;
        private String target_user_avatar;
        private int reply_id;
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

        public int getReply_id() {
            return reply_id;
        }

        public void setReply_id(int reply_id) {
            this.reply_id = reply_id;
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
