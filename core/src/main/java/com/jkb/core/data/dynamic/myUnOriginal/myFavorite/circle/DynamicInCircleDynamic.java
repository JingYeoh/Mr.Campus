package com.jkb.core.data.dynamic.myUnOriginal.myFavorite.circle;

import com.jkb.api.entity.dynamic.DynamicMyFavoriteEntity;
import com.jkb.core.data.info.circle.CircleInfo;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.MyFavoriteDynamicData;
import com.jkb.core.data.info.user.UserInfo;

/**
 * 圈子内动态的数据
 * Created by JustKiddingBaby on 2016/10/16.
 */

public class DynamicInCircleDynamic extends MyFavoriteDynamicData {


    //data
    private String title;
    private String tag;
    private UserInfo user;//用户
    private CircleInfo circle;//圈子

    private DynamicInCircleDynamic INSTANCE = null;

    public DynamicInCircleDynamic(
            DynamicMyFavoriteEntity.DataBean.DynamicInCircleBean dynamicInCircleBean) {
        handleDynamicInCircle(dynamicInCircleBean);
    }

    public DynamicInCircleDynamic() {
    }

    @Override
    public MyFavoriteDynamicData getMyFavoriteDynamic() {
        if (INSTANCE == null) {
            return null;
        }
        return INSTANCE.getMyFavoriteDynamic();
    }

    /**
     * 处理圈子中动态数据
     */
    private void handleDynamicInCircle(
            DynamicMyFavoriteEntity.DataBean.DynamicInCircleBean dynamicInCircleBean) {
        if (dynamicInCircleBean == null) {
            INSTANCE = null;
            return;
        }
        String dtype = dynamicInCircleBean.getDtype();
        switch (dtype) {
            case "article":
                INSTANCE = new CircleArticleDynamic(dynamicInCircleBean);
                break;
            case "normal":
                INSTANCE = new CircleNormalDynamic(dynamicInCircleBean);
                break;
            case "topic":
                INSTANCE = new CircleTopicDynamic(dynamicInCircleBean);
                break;
            default:
                INSTANCE = null;
                break;
        }
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


    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public CircleInfo getCircle() {
        return circle;
    }

    public void setCircle(CircleInfo circle) {
        this.circle = circle;
    }
}
