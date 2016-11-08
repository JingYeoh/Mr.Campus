package com.jkb.core.data.index.hot.dynamic.original;

import com.jkb.api.entity.dynamic.DynamicPopularListEntity;
import com.jkb.core.data.index.hot.HotDynamic;
import com.jkb.core.data.info.user.UserInfo;

/**
 * 原创的热门动态
 * Created by JustKiddingBaby on 2016/9/30.
 */

public class OriginalDynamic extends HotDynamic {

    private String originalDynamicType;//原创的热门动态的类型
    private OriginalDynamic INSTANCE;


    //data
    private int dynamic_id;
    private String dtype;
    private String title;
    private String tag;
    private int count_of_comment;
    private int count_of_favorite;
    private String dynamic_created_at;
    private boolean has_favorite;

    private UserInfo user;//用户


    public OriginalDynamic(DynamicPopularListEntity.DataBean hotDynamicData) {
        handleOriginalDynamicData(hotDynamicData);
    }

    public OriginalDynamic() {
    }

    @Override
    public HotDynamic getHotDynamic() {
        if (INSTANCE == null) {
            return null;
        }
        return INSTANCE.getHotDynamic();
    }

    /**
     * 处理原创的热门动态数据
     */
    private void handleOriginalDynamicData(DynamicPopularListEntity.DataBean hotDynamicData) {
        if (hotDynamicData == null) {
            INSTANCE = null;
            return;
        }
        DynamicPopularListEntity.DataBean.DynamicBean dynamic = hotDynamicData.getDynamic();
        if (dynamic == null) {
            INSTANCE = null;
            return;
        }
        originalDynamicType = dynamic.getDtype();
        //设置动态分类
        switch (originalDynamicType) {
            case "article":
                INSTANCE = new OriginalArticleDynamic(dynamic);
                break;
            case "normal":
                INSTANCE = new OriginalNormalDynamic(dynamic);
                break;
            case "topic":
                INSTANCE = new OriginalTopicDynamic(dynamic);
                break;
            default:
                INSTANCE = null;
                break;
        }
    }

    public int getDynamic_id() {
        return dynamic_id;
    }

    public void setDynamic_id(int dynamic_id) {
        this.dynamic_id = dynamic_id;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getCount_of_comment() {
        return count_of_comment;
    }

    public void setCount_of_comment(int count_of_comment) {
        this.count_of_comment = count_of_comment;
    }

    public int getCount_of_favorite() {
        return count_of_favorite;
    }

    public void setCount_of_favorite(int count_of_favorite) {
        this.count_of_favorite = count_of_favorite;
    }

    public String getDynamic_created_at() {
        return dynamic_created_at;
    }

    public void setDynamic_created_at(String dynamic_created_at) {
        this.dynamic_created_at = dynamic_created_at;
    }

    public boolean isHas_favorite() {
        return has_favorite;
    }

    public void setHas_favorite(boolean has_favorite) {
        this.has_favorite = has_favorite;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}
