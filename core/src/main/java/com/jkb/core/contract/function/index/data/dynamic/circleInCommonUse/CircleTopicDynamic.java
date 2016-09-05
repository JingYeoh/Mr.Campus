package com.jkb.core.contract.function.index.data.dynamic.circleInCommonUse;

import com.jkb.core.contract.function.index.data.dynamic.entity.DynamicCircleData;

/**
 * 圈子动态——话题数据
 * Created by JustKiddingBaby on 2016/9/3.
 */

public class CircleTopicDynamic {
    private int dynamic_id;
    private String title;
    private String content;
    private String create_at;

    private DynamicCircleData dynamicCircleData;

    public int getDynamic_id() {
        return dynamic_id;
    }

    public void setDynamic_id(int dynamic_id) {
        this.dynamic_id = dynamic_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public DynamicCircleData getDynamicCircleData() {
        return dynamicCircleData;
    }

    public void setDynamicCircleData(DynamicCircleData dynamicCircleData) {
        this.dynamicCircleData = dynamicCircleData;
    }
}
