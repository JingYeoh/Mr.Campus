package com.jkb.api.entity.dynamic;

import java.util.List;

/**
 * 创建动态的数据实体类
 * Created by JustKiddingBaby on 2016/9/26.
 */

public class DynamicPostEntity {


    private List<String> user_id;
    private List<String> title;
    private List<String> dcontent;

    public List<String> getUser_id() {
        return user_id;
    }

    public void setUser_id(List<String> user_id) {
        this.user_id = user_id;
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public List<String> getDcontent() {
        return dcontent;
    }

    public void setDcontent(List<String> dcontent) {
        this.dcontent = dcontent;
    }
}
