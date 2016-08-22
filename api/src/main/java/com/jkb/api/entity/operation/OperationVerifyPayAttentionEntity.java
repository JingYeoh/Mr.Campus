package com.jkb.api.entity.operation;

import java.util.List;

/**
 * 方可是否关注用户回调方法
 * Created by JustKiddingBaby on 2016/8/22.
 */

public class OperationVerifyPayAttentionEntity {


    /**
     * hasPayAttention : 1
     * user_id : ["The user id field is required."]
     * visitor_id : ["The visitor id field is required."]
     */

    private int hasPayAttention;
    private List<String> user_id;
    private List<String> visitor_id;

    public int getHasPayAttention() {
        return hasPayAttention;
    }

    public void setHasPayAttention(int hasPayAttention) {
        this.hasPayAttention = hasPayAttention;
    }

    public List<String> getUser_id() {
        return user_id;
    }

    public void setUser_id(List<String> user_id) {
        this.user_id = user_id;
    }

    public List<String> getVisitor_id() {
        return visitor_id;
    }

    public void setVisitor_id(List<String> visitor_id) {
        this.visitor_id = visitor_id;
    }
}
