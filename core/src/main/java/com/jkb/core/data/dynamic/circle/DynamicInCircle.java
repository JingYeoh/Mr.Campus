package com.jkb.core.data.dynamic.circle;

import android.support.annotation.NonNull;

import com.jkb.api.entity.circle.DynamicInCircleListEntity;
import com.jkb.core.data.dynamic.circle.original.DynamicInCircleOriginalArticle;
import com.jkb.core.data.dynamic.circle.original.DynamicInCircleOriginalNormal;
import com.jkb.core.data.dynamic.circle.original.DynamicInCircleOriginalTopic;
import com.jkb.core.data.user.UserInfo;

/**
 * 圈子动态的基类
 * 用于区分圈子内不同的动态
 * Created by JustKiddingBaby on 2016/10/6.
 */

public class DynamicInCircle {

    private DynamicInCircleListEntity.DataBean dataBean;

    public DynamicInCircle(@NonNull DynamicInCircleListEntity.DataBean dataBean) {
        this.dataBean = dataBean;
        handleDynamicInCircle();
    }

    public DynamicInCircle() {
    }

    private DynamicInCircle INSTANCE = null;

    /**
     * 得到具体的动态
     */
    public DynamicInCircle getDynamic() {
        if (INSTANCE == null) {
            return null;
        }
        return INSTANCE.getDynamic();
    }

    /**
     * 解析圈子内的动态信息
     */
    private void handleDynamicInCircle() {
        if (dataBean == null) {
            INSTANCE = null;
            return;
        }
        String dynamicType = dataBean.getDtype();
        switch (dynamicType) {
            case "article":
                INSTANCE = new DynamicInCircleOriginalArticle(dataBean);
                break;
            case "normal":
                INSTANCE = new DynamicInCircleOriginalNormal(dataBean);
                break;
            case "topic":
                INSTANCE = new DynamicInCircleOriginalTopic(dataBean);
                break;
            default:
                INSTANCE = null;
                break;
        }
    }

    //data
    private UserInfo user;//用户
    //data
    private int dynamic_id;
    private String dtype;
    private String title;
    private String tag;
    private int count_of_comment;
    private int count_of_favorite;
    private String dynamic_created_at;
    private boolean has_favorite;

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
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
}
