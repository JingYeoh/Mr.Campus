package com.jkb.api.entity.comment;

import java.util.List;

/**
 * 发送评论实体类
 * Created by JustKiddingBaby on 2016/9/18.
 */

public class CommentSendEntity {

    /**
     * success : true
     * user_id : ["The user id field is required."]
     * dynamic_id : ["The dynamic id field is required."]
     * comment : ["The comment field is required."]
     */

    private boolean success;
    private List<String> user_id;
    private List<String> dynamic_id;
    private List<String> comment;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getUser_id() {
        return user_id;
    }

    public void setUser_id(List<String> user_id) {
        this.user_id = user_id;
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
