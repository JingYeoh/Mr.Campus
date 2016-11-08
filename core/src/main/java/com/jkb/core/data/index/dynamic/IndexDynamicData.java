package com.jkb.core.data.index.dynamic;

import com.jkb.api.entity.dynamic.DynamicListEntity;
import com.jkb.core.data.index.dynamic.original.IndexOriginalDynamicData;
import com.jkb.core.data.index.dynamic.unOriginal.circle.IndexSubscribeDynamicData;
import com.jkb.core.data.index.dynamic.unOriginal.favorite.IndexFavoriteDynamicData;
import com.jkb.core.data.index.dynamic.unOriginal.postInCircle.IndexCircleDynamicData;

/**
 * 首页的数据类
 * Created by JustKiddingBaby on 2016/11/7.
 */

public class IndexDynamicData {

    /**
     * target_type : circle
     * action : subscribe
     * title : 订阅了一个圈子
     * target_id : 304
     * created_at : 2016-11-01 19:35:03
     * creator_id : 3
     * creator_nickname : veda.shields
     * creator_avatar : http://lorempixel.com/640/480/?79132
     */

    private String target_type;
    private String action;
    private String actionTitle;
    private int target_id;
    private String created_at;
    private int creator_id;
    private String creator_nickname;
    private String creator_avatar;
    private String dynamicType;

    public IndexDynamicData() {
    }

    public IndexDynamicData(DynamicListEntity.DataBean dataBean) {
        handleDynamicData(dataBean);
    }

    private IndexDynamicData INSTANCE = null;

    public IndexDynamicData getIndexDynamic() {
        if (INSTANCE == null) {
            return null;
        }
        return INSTANCE.getIndexDynamic();
    }

    /**
     * 处理动态数据
     */
    private void handleDynamicData(DynamicListEntity.DataBean dataBean) {
        if (dataBean == null) {
            INSTANCE = null;
            return;
        }
        String action = dataBean.getAction();
        switch (action) {
            case "post":
                INSTANCE = new IndexOriginalDynamicData(dataBean.getDynamic()).getIndexDynamic();
                break;
            case "favorite":
            case "favoriteDynamicInCircle":
                INSTANCE = new IndexFavoriteDynamicData(dataBean.getDynamic()).getIndexDynamic();
                break;
            case "postInCircle":
                INSTANCE = new IndexCircleDynamicData(dataBean.getDynamic()).getIndexDynamic();
                break;
            case "subscribe":
                INSTANCE = new IndexSubscribeDynamicData(dataBean.getCircle()).getIndexDynamic();
                break;
            default:
                INSTANCE = null;
                break;
        }
        //绑定通用数据
        bindGeneralDynamicData(dataBean);
    }

    /**
     * 绑定通用动态数据
     */
    private void bindGeneralDynamicData(DynamicListEntity.DataBean dataBean) {
        if (INSTANCE == null) {
            return;
        }
        INSTANCE.setTarget_type(dataBean.getTarget_type());
        INSTANCE.setAction(dataBean.getAction());
        INSTANCE.setActionTitle(dataBean.getTitle());
        INSTANCE.setTarget_id(dataBean.getTarget_id());
        INSTANCE.setCreated_at(dataBean.getCreated_at());
        INSTANCE.setCreator_id(dataBean.getCreator_id());
        INSTANCE.setCreator_nickname(dataBean.getCreator_nickname());
        INSTANCE.setCreator_avatar(dataBean.getCreator_avatar());
    }

    public String getTarget_type() {
        return target_type;
    }

    public void setTarget_type(String target_type) {
        this.target_type = target_type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionTitle() {
        return actionTitle;
    }

    public void setActionTitle(String actionTitle) {
        this.actionTitle = actionTitle;
    }

    public int getTarget_id() {
        return target_id;
    }

    public void setTarget_id(int target_id) {
        this.target_id = target_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(int creator_id) {
        this.creator_id = creator_id;
    }

    public String getCreator_nickname() {
        return creator_nickname;
    }

    public void setCreator_nickname(String creator_nickname) {
        this.creator_nickname = creator_nickname;
    }

    public String getCreator_avatar() {
        return creator_avatar;
    }

    public void setCreator_avatar(String creator_avatar) {
        this.creator_avatar = creator_avatar;
    }

    public String getDynamicType() {
        return dynamicType;
    }

    public void setDynamicType(String dynamicType) {
        this.dynamicType = dynamicType;
    }
}
