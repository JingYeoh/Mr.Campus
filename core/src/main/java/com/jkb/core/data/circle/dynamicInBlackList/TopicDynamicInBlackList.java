package com.jkb.core.data.circle.dynamicInBlackList;

import com.jkb.api.entity.circle.CircleDynamicInBlackListEntity;
import com.jkb.core.data.info.dynamic.content.DynamicContentTopicInfo;

/**
 * 话题的圈子动态黑名单页面数据类
 * Created by JustKiddingBaby on 2016/11/5.
 */

public class TopicDynamicInBlackList extends DynamicInBlackList {

    private boolean isDataEffective = true;

    private DynamicContentTopicInfo topicInfo;

    public TopicDynamicInBlackList(
            CircleDynamicInBlackListEntity.DataBean.ContentBean.TopicBean topicBean) {
        handleTopicData(topicBean);
    }


    @Override
    public DynamicInBlackList getDynamicInBlackList() {
        if (isDataEffective) {
            return this;
        }
        return null;
    }

    /**
     * 处理话题数据
     */
    private void handleTopicData(
            CircleDynamicInBlackListEntity.DataBean.ContentBean.TopicBean topicBean) {
        if (topicBean == null) {
            return;
        }
        topicInfo = new DynamicContentTopicInfo();
        topicInfo.setDoc(topicBean.getDoc());
        topicInfo.setImg(topicBean.getImg());
        setTopicInfo(topicInfo);
    }

    public DynamicContentTopicInfo getTopicInfo() {
        return topicInfo;
    }

    public void setTopicInfo(DynamicContentTopicInfo topicInfo) {
        this.topicInfo = topicInfo;
    }
}
