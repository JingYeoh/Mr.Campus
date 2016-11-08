package com.jkb.core.data.circle.dynamicInBlackList;

import com.jkb.api.entity.circle.CircleDynamicInBlackListEntity;
import com.jkb.core.data.info.user.UserInfo;

/**
 * 圈子黑名单动态的数据类
 * Created by JustKiddingBaby on 2016/11/5.
 */

public class DynamicInBlackList {

    public DynamicInBlackList() {
    }

    public DynamicInBlackList(CircleDynamicInBlackListEntity.DataBean dataBean) {
        handleData(dataBean);
    }

    private static DynamicInBlackList INSTANCE = null;

    public DynamicInBlackList getDynamicInBlackList() {
        if (INSTANCE == null) {
            return null;
        }
        return INSTANCE.getDynamicInBlackList();
    }

    /**
     * 处理数据
     */
    private void handleData(CircleDynamicInBlackListEntity.DataBean dataBean) {
        if (dataBean == null) {
            INSTANCE = null;
            return;
        }
        String dtype = dataBean.getDtype();
        if (dataBean.getContent() == null) {
            INSTANCE = null;
            return;
        }
        switch (dtype) {
            case "topic":
                INSTANCE = new TopicDynamicInBlackList(dataBean.getContent().getTopic());
                break;
            case "normal":
                INSTANCE = new NormalDynamicInBlackList(dataBean.getContent().getNormal());
                break;
            case "article":
                INSTANCE = new ArticleDynamicInBlackList(dataBean.getContent().getArticle());
                break;
            default:
                INSTANCE = null;
                break;
        }
        bindDynamicData(dataBean);
    }

    /**
     * 绑定动态数据
     */
    private void bindDynamicData(CircleDynamicInBlackListEntity.DataBean dataBean) {
        if (INSTANCE == null) {
            return;
        }
        INSTANCE.setDynamic_id(dataBean.getId());
        INSTANCE.setDtype(dataBean.getDtype());
        INSTANCE.setTitle(dataBean.getTitle());
        INSTANCE.setTag(dataBean.getTag());
        INSTANCE.setCount_of_comment(dataBean.getCount_of_comment());
        INSTANCE.setCount_of_favorite(dataBean.getCount_of_favorite());
        INSTANCE.setDynamic_created_at(dataBean.getCreated_at());
        INSTANCE.setHas_favorite(false);//默认为为喜欢
        INSTANCE.setCount_of_partIn(dataBean.getCount_of_participation());
        //设置用户数据
        CircleDynamicInBlackListEntity.DataBean.UserBean user = dataBean.getUser();
        if (user == null) {
            INSTANCE = null;
            return;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setNickName(user.getNickname());
        INSTANCE.setUser(userInfo);
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
    private int count_of_partIn;

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

    public int getCount_of_partIn() {
        return count_of_partIn;
    }

    public void setCount_of_partIn(int count_of_partIn) {
        this.count_of_partIn = count_of_partIn;
    }

    public boolean isHas_favorite() {
        return has_favorite;
    }

    public void setHas_favorite(boolean has_favorite) {
        this.has_favorite = has_favorite;
    }
}
