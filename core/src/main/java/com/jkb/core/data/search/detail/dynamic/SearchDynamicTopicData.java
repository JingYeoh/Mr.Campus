package com.jkb.core.data.search.detail.dynamic;

import com.jkb.api.entity.search.SearchEntity;
import com.jkb.core.data.info.dynamic.content.DynamicContentArticleInfo;
import com.jkb.core.data.info.dynamic.content.DynamicContentTopicInfo;
import com.jkb.core.data.search.detail.SearchData;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索的话题数据类
 * Created by JustKiddingBaby on 2016/11/26.
 */

public final class SearchDynamicTopicData extends SearchDynamicData {

    private DynamicContentTopicInfo topic;

    public SearchDynamicTopicData(
            SearchEntity.DataBean.ContentBean.TopicBean topicBeen) {
        handleArticleData(topicBeen);
    }

    @Override
    public SearchData getSearchData() {
        if (isDataEffective) {
            return this;
        }
        return null;
    }

    /**
     * 处理文章数据
     */
    private void handleArticleData(SearchEntity.DataBean.ContentBean.TopicBean topicBean) {
        if (topicBean == null) {
            isDataEffective = false;
            return;
        }
        //处理文章数据
        topic = new DynamicContentTopicInfo();
        topic.setDoc(topicBean.getDoc());
        topic.setImg(topicBean.getImg());
        setTopic(topic);
    }

    public DynamicContentTopicInfo getTopic() {
        return topic;
    }

    public void setTopic(DynamicContentTopicInfo topic) {
        this.topic = topic;
    }
}
