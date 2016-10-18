package com.jkb.core.data.dynamic.circle.original;

import com.jkb.api.entity.circle.DynamicInCircleListEntity;
import com.jkb.core.data.dynamic.circle.DynamicInCircle;
import com.jkb.core.data.info.user.UserInfo;

/**
 * 圈子内原创动态：话题
 * Created by JustKiddingBaby on 2016/10/6.
 */

public class DynamicInCircleOriginalTopic extends DynamicInCircle {

    private boolean isDataEffective;

    private TopicContent topicContent;
    private int count_of_participation;

    public DynamicInCircleOriginalTopic(DynamicInCircleListEntity.DataBean dataBean) {
        handleDynamicNormal(dataBean);
    }

    @Override
    public DynamicInCircle getDynamic() {
        if (isDataEffective) {
            return this;
        }
        return null;
    }

    /**
     * 处理普通动态数据
     */
    private void handleDynamicNormal(DynamicInCircleListEntity.DataBean dynamic) {
        if (dynamic == null) {
            isDataEffective = false;
            return;
        }
        isDataEffective = true;
        setDynamic_id(dynamic.getDynamic_id());
        setDtype(dynamic.getDtype());
        setTitle(dynamic.getTitle());
        //设置话题内容数据
        DynamicInCircleListEntity.DataBean.ContentBean content = dynamic.getContent();
        DynamicInCircleListEntity.DataBean.ContentBean.TopicBean topic = content.getTopic();
        if (topic == null) {
            setTopicContent(null);
        } else {
            TopicContent topicContent = new TopicContent();
            topicContent.setImg(topic.getImg());
            topicContent.setDoc(topic.getDoc());
            setTopicContent(topicContent);
        }
        setTag(dynamic.getTag());
        setCount_of_comment(dynamic.getCount_of_comment());
        setCount_of_favorite(dynamic.getCount_of_favorite());
        setDynamic_created_at(dynamic.getCreated_at());
        setCount_of_participation(dynamic.getCount_of_participation());
        if (dynamic.getHas_favorite() == 0) {
            setHas_favorite(false);
        } else {
            setHas_favorite(true);
        }
        //设置用户数据
        UserInfo userInfo = new UserInfo();
        userInfo.setId(dynamic.getAuthor_id());
        userInfo.setAvatar(dynamic.getAuthor_avatar());
        userInfo.setNickName(dynamic.getAuthor_nickname());
        setUser(userInfo);
    }

    public static class TopicContent {
        String doc;
        String img;

        public String getDoc() {
            return doc;
        }

        public void setDoc(String doc) {
            this.doc = doc;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }

    public TopicContent getTopicContent() {
        return topicContent;
    }

    public void setTopicContent(TopicContent topicContent) {
        this.topicContent = topicContent;
    }

    public int getCount_of_participation() {
        return count_of_participation;
    }

    public void setCount_of_participation(int count_of_participation) {
        this.count_of_participation = count_of_participation;
    }
}
