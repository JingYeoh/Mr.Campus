package com.jkb.core.contract.dynamicDetail.data.comment;

import com.jkb.core.contract.dynamicDetail.data.DynamicDetailUserData;

import java.util.List;

/**
 * 动态详情：评论数据实体类
 * Created by JustKiddingBaby on 2016/9/18.
 */

public class DynamicDetailCommentData {

    private int comment_id;
    private DynamicDetailUserData comment_user;
    private String comment;
    private String comment_time;
    private boolean has_like;
    private int likeCount;
    private List<DynamicDetailCommentReplyData> replyDatas;
    private int replyCount = 0;

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public DynamicDetailUserData getComment_user() {
        return comment_user;
    }

    public void setComment_user(DynamicDetailUserData comment_user) {
        this.comment_user = comment_user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public boolean isHas_like() {
        return has_like;
    }

    public void setHas_like(boolean has_like) {
        this.has_like = has_like;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public List<DynamicDetailCommentReplyData> getReplyDatas() {
        return replyDatas;
    }

    public void setReplyDatas(List<DynamicDetailCommentReplyData> replyDatas) {
        this.replyDatas = replyDatas;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }
}
