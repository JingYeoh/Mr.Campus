package com.jkb.api.entity.comment;

import java.util.List;

/**
 * 回复的数据实体类
 * Created by JustKiddingBaby on 2016/9/22.
 */

public class CommentReplyEntity {

    private List<String> error;
    private List<String> target_user_id;
    private List<String> comment_id;
    private List<String> dynamic_id;
    private List<String> comment;

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }

    public List<String> getTarget_user_id() {
        return target_user_id;
    }

    public void setTarget_user_id(List<String> target_user_id) {
        this.target_user_id = target_user_id;
    }

    public List<String> getComment_id() {
        return comment_id;
    }

    public void setComment_id(List<String> comment_id) {
        this.comment_id = comment_id;
    }

    public List<String> getDynamic_id() {
        return dynamic_id;
    }

    public void setDynamic_id(List<String> dynamic_id) {
        this.dynamic_id = dynamic_id;
    }

    public List<String> getComment() {
        return comment;
    }

    public void setComment(List<String> comment) {
        this.comment = comment;
    }
}
