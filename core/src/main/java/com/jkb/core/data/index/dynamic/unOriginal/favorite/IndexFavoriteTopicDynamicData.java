package com.jkb.core.data.index.dynamic.unOriginal.favorite;

import com.jkb.api.entity.dynamic.DynamicListEntity;
import com.jkb.core.data.index.dynamic.IndexDynamicData;
import com.jkb.core.data.index.dynamic.original.IndexOriginalDynamicData;
import com.jkb.core.data.info.dynamic.content.DynamicContentTopicInfo;

/**
 * 首页-动态的原创話題动态实体类
 * Created by JustKiddingBaby on 2016/11/7.
 */

public class IndexFavoriteTopicDynamicData extends IndexFavoriteDynamicData {

    private boolean isDataEffective = true;

    private DynamicContentTopicInfo topic;

    public IndexFavoriteTopicDynamicData() {
    }

    public IndexFavoriteTopicDynamicData(
            DynamicListEntity.DataBean.DynamicBean.ContentBean.TopicBean topicBean) {
        handleNormalData(topicBean);
    }

    @Override
    public IndexDynamicData getIndexDynamic() {
        if (isDataEffective) {
            return this;
        }
        return null;
    }

    /**
     * 处理普通动态数据
     */
    private void handleNormalData(
            DynamicListEntity.DataBean.DynamicBean.ContentBean.TopicBean topicBean) {
        if (topicBean == null) {
            isDataEffective = false;
            return;
        }
        topic = new DynamicContentTopicInfo();
        topic.setImg(topicBean.getImg());
        topic.setDoc(topicBean.getDoc());
        setTopic(topic);
    }

    public DynamicContentTopicInfo getTopic() {
        return topic;
    }

    public void setTopic(DynamicContentTopicInfo topic) {
        this.topic = topic;
    }
}
