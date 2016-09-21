package com.jkb.core.contract.dynamicDetail.data.comment;

import com.jkb.core.contract.dynamicDetail.data.DynamicDetailUserData;

/**
 * 评论回复的数据实体类
 * Created by JustKiddingBaby on 2016/9/18.
 */

public class DynamicDetailCommentReplyData {
    private int reply_id;
    private DynamicDetailUserData reply_user;
    private DynamicDetailUserData reply_target_user;
    private String comment;
    private String reply_time;

    public int getReply_id() {
        return reply_id;
    }

    public void setReply_id(int reply_id) {
        this.reply_id = reply_id;
    }

    public DynamicDetailUserData getReply_user() {
        return reply_user;
    }

    public void setReply_user(DynamicDetailUserData reply_user) {
        this.reply_user = reply_user;
    }

    public DynamicDetailUserData getReply_target_user() {
        return reply_target_user;
    }

    public void setReply_target_user(DynamicDetailUserData reply_target_user) {
        this.reply_target_user = reply_target_user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReply_time() {
        return reply_time;
    }

    public void setReply_time(String reply_time) {
        this.reply_time = reply_time;
    }
}
