package com.jkb.api.entity.operation;

import java.util.List;

/**
 * 关注订阅等操作返回的数据实体类
 * Created by JustKiddingBaby on 2016/8/20.
 */

public class OperationActionEntity {


    private List<String> user_id;

    public List<String> getUser_id() {
        return user_id;
    }

    public void setUser_id(List<String> user_id) {
        this.user_id = user_id;
    }
}
