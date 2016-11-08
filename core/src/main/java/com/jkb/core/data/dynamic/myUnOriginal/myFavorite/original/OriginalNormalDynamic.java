package com.jkb.core.data.dynamic.myUnOriginal.myFavorite.original;

import android.support.annotation.NonNull;

import com.jkb.api.entity.dynamic.DynamicMyFavoriteEntity;
import com.jkb.core.data.index.hot.dynamic.original.OriginalTopicDynamic;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.MyFavoriteDynamicData;
import com.jkb.core.data.info.user.UserInfo;

import java.util.List;

;

/**
 * 原创的普通动态数据类
 * Created by JustKiddingBaby on 2016/10/16.
 */

public class OriginalNormalDynamic extends OriginalDynamic {

    private boolean isDataEffective;

    //data
    private NormalContent normalContent;

    private OriginalTopicDynamic.TopicContent topicContent;
    private int count_of_participation;

    public OriginalNormalDynamic(@NonNull DynamicMyFavoriteEntity.DataBean.DynamicBean dynamic) {
        handleNormalDynamicData(dynamic);
    }

    @Override
    public MyFavoriteDynamicData getMyFavoriteDynamic() {
        if (!isDataEffective) {
            return null;
        }
        return this;
    }

    /**
     * 据诶系普通动态数据
     */
    private void handleNormalDynamicData(DynamicMyFavoriteEntity.DataBean.DynamicBean dynamic) {
        if (dynamic == null) {
            isDataEffective = false;
            return;
        }
        isDataEffective = true;
        setDynamic_id(dynamic.getId());
        setDtype(dynamic.getDtype());
        setTitle(dynamic.getTitle());
        //设置话题内容数据
        DynamicMyFavoriteEntity.DataBean.DynamicBean.ContentBean
                content = dynamic.getContent();
        if (content == null) {
            isDataEffective = false;
            return;
        }
        DynamicMyFavoriteEntity.DataBean.DynamicBean.ContentBean.NormalBean normal =
                content.getNormal();
        if (normal == null) {
            isDataEffective = false;
            return;
        }
        normalContent = new NormalContent();
        normalContent.setDoc(normal.getDoc());
        normalContent.setImg(normal.getImg());
        setNormalContent(normalContent);
        setTag(dynamic.getTag());
        setCount_of_comment(dynamic.getCount_of_comment());
        setCount_of_favorite(dynamic.getCount_of_favorite());
        setDynamic_created_at(dynamic.getUpdated_at());
        if (dynamic.getHas_favorite() == 0) {
            setHas_favorite(false);
        } else {
            setHas_favorite(true);
        }
        //设置用户数据
        UserInfo userInfo = new UserInfo();
        DynamicMyFavoriteEntity.DataBean.DynamicBean.UserBean user = dynamic.getUser();
        if (user == null) {
            isDataEffective = false;
            return;
        }
        userInfo.setId(user.getId());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setNickName(user.getNickname());
        setUser(userInfo);
    }

    public int getCount_of_participation() {
        return count_of_participation;
    }

    public void setCount_of_participation(int count_of_participation) {
        this.count_of_participation = count_of_participation;
    }

    /**
     * 普通的内容
     */
    public static class NormalContent {
        List<String> img;
        String doc;

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }

        public String getDoc() {
            return doc;
        }

        public void setDoc(String doc) {
            this.doc = doc;
        }
    }

    public NormalContent getNormalContent() {
        return normalContent;
    }

    public void setNormalContent(NormalContent normalContent) {
        this.normalContent = normalContent;
    }
}
