package com.jkb.core.data.dynamic.myUnOriginal.myFavorite.original;

import android.support.annotation.NonNull;

import com.jkb.api.entity.dynamic.DynamicMyFavoriteEntity;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.MyFavoriteDynamicData;
import com.jkb.core.data.info.user.UserInfo;

/**
 * 原创的动态数据类
 * Created by JustKiddingBaby on 2016/10/16.
 */

public class OriginalDynamic extends MyFavoriteDynamicData {

    private OriginalDynamic INSTANCE;

    //data
    private UserInfo user;//用户
    private String title;
    private String tag;


    public OriginalDynamic(@NonNull DynamicMyFavoriteEntity.DataBean.DynamicBean dynamic) {
        handleOriginalDynamicData(dynamic);
    }

    public OriginalDynamic() {
    }

    @Override
    public MyFavoriteDynamicData getMyFavoriteDynamic() {
        if (INSTANCE == null) {
            return null;
        }
        return INSTANCE.getMyFavoriteDynamic();
    }

    /**
     * 解析数据
     */
    private void handleOriginalDynamicData(DynamicMyFavoriteEntity.DataBean.DynamicBean dynamic) {
        if (dynamic == null) {
            INSTANCE = null;
            return;
        }
        String originalDynamicType = dynamic.getDtype();
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
}
