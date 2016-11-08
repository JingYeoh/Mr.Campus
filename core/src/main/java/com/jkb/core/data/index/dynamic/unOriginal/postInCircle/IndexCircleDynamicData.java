package com.jkb.core.data.index.dynamic.unOriginal.postInCircle;

import com.jkb.api.entity.dynamic.DynamicListEntity;
import com.jkb.core.data.index.dynamic.IndexDynamicData;
import com.jkb.core.data.info.circle.CircleInfo;
import com.jkb.core.data.info.user.UserInfo;

/**
 * 首页动态非原创的喜欢动态
 * Created by JustKiddingBaby on 2016/11/7.
 */

public class IndexCircleDynamicData extends IndexDynamicData {

    /**
     * id : 538
     * user_id : 3
     * dtype : normal
     * tag :
     * count_of_comment : 0
     * count_of_favorite : 0
     * count_of_participation : 0
     * updated_at : 2016-11-02 14:28:56
     * comments_count : 0
     * operation_count : 0
     * hasFavorite : false
     */

    private int id;
    private int user_id;
    private String dtype;
    private String tag;
    private String title;
    private int count_of_comment;
    private int count_of_favorite;
    private int count_of_participation;
    private String updated_at;
    private int comments_count;
    private int operation_count;
    private boolean hasFavorite;
    private UserInfo user;
    private CircleInfo circle;

    public IndexCircleDynamicData() {
    }

    public IndexCircleDynamicData(DynamicListEntity.DataBean.DynamicBean dynamicBean) {
        handleOriginalDynamicData(dynamicBean);
    }

    private IndexCircleDynamicData INSTANCE = null;

    @Override
    public IndexDynamicData getIndexDynamic() {
        if (INSTANCE == null) {
            return null;
        }
        return INSTANCE.getIndexDynamic();
    }

    /**
     * 处理原创的动态数据
     */
    private void handleOriginalDynamicData(DynamicListEntity.DataBean.DynamicBean dynamicBean) {
        if (dynamicBean == null) {
            INSTANCE = null;
            return;
        }
        if (dynamicBean.getContent() == null) {
            INSTANCE = null;
            return;
        }
        String dtype = dynamicBean.getDtype();
        switch (dtype) {
            case "normal":
                INSTANCE = new IndexCircleNormalDynamicData(dynamicBean.getContent().getNormal());
                break;
            case "topic":
                INSTANCE = new IndexCircleTopicDynamicData(dynamicBean.getContent().getTopic());
                break;
            case "article":
                INSTANCE = new IndexCircleArticleDynamicData(dynamicBean.getContent().getArticle());
                break;
            default:
                INSTANCE = null;
                break;
        }
        bindGeneralOriginalDynamicData(dynamicBean);
    }

    /**
     * 绑定通用动态数据
     */
    private void bindGeneralOriginalDynamicData(DynamicListEntity.DataBean.DynamicBean dynamicBean) {
        if (INSTANCE == null) {
            return;
        }
        INSTANCE.setId(dynamicBean.getId());
        INSTANCE.setUser_id(dynamicBean.getUser_id());
        INSTANCE.setDtype(dynamicBean.getDtype());
        INSTANCE.setDynamicType(dynamicBean.getDtype());
        INSTANCE.setTitle(dynamicBean.getTitle());
        INSTANCE.setTag(dynamicBean.getTag());
        INSTANCE.setCount_of_comment(dynamicBean.getCount_of_comment());
        INSTANCE.setCount_of_favorite(dynamicBean.getCount_of_favorite());
        INSTANCE.setCount_of_participation(dynamicBean.getCount_of_participation());
        INSTANCE.setCreated_at(dynamicBean.getCreated_at());
        INSTANCE.setUpdated_at(dynamicBean.getUpdated_at());
        INSTANCE.setHasFavorite(dynamicBean.isHasFavorite());
        //用户
        DynamicListEntity.DataBean.DynamicBean.UserBean user = dynamicBean.getUser();
        if (user == null) {
            INSTANCE = null;
            return;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setNickName(user.getNickname());
        userInfo.setAvatar(user.getAvatar());
        INSTANCE.setUser(userInfo);
        //圈子
        DynamicListEntity.DataBean.DynamicBean.CircleBean circleBean = dynamicBean.getCircle();
        if (circleBean == null) {
            INSTANCE = null;
            return;
        }
        circle = new CircleInfo();
        circle.setCircleId(circleBean.getId());
        circle.setCircleName(circleBean.getName());
        circle.setCircleType(circleBean.getType());
        circle.setPictureUrl(circleBean.getPicture());
        circle.setIntroduction(circleBean.getIntroduction());
        circle.setDynamicsCount(circleBean.getCount_of_dynamic());
        circle.setOperatorCount(circleBean.getCount_of_subscription());
        INSTANCE.setCircle(circle);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
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

    public int getCount_of_participation() {
        return count_of_participation;
    }

    public void setCount_of_participation(int count_of_participation) {
        this.count_of_participation = count_of_participation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getOperation_count() {
        return operation_count;
    }

    public void setOperation_count(int operation_count) {
        this.operation_count = operation_count;
    }

    public boolean isHasFavorite() {
        return hasFavorite;
    }

    public void setHasFavorite(boolean hasFavorite) {
        this.hasFavorite = hasFavorite;
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
