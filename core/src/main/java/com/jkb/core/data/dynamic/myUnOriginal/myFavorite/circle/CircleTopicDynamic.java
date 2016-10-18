package com.jkb.core.data.dynamic.myUnOriginal.myFavorite.circle;

import com.jkb.api.entity.dynamic.DynamicMyFavoriteEntity;
import com.jkb.core.data.info.circle.CircleInfo;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.MyFavoriteDynamicData;
import com.jkb.core.data.info.user.UserInfo;

/**
 * 圈子内动态：话题的数据类
 * Created by JustKiddingBaby on 2016/10/16.
 */

public class CircleTopicDynamic extends DynamicInCircleDynamic {

    private boolean isDataEffective;

    private TopicContent topicContent;
    private int count_of_participation;


    public CircleTopicDynamic(
            DynamicMyFavoriteEntity.DataBean.DynamicInCircleBean dynamicInCircleBean) {
        handleTopicDynamicData(dynamicInCircleBean);
    }

    @Override
    public MyFavoriteDynamicData getMyFavoriteDynamic() {
        if (!isDataEffective) {
            return null;
        }
        return this;
    }

    /**
     * 解析数据
     */
    private void handleTopicDynamicData(
            DynamicMyFavoriteEntity.DataBean.DynamicInCircleBean dynamic) {
        if (dynamic == null) {
            isDataEffective = false;
            return;
        }
        isDataEffective = true;
        setDynamic_id(dynamic.getId());
        setDtype(dynamic.getDtype());
        setTitle(dynamic.getTitle());
        //设置话题内容数据
        DynamicMyFavoriteEntity.DataBean.DynamicInCircleBean.ContentBean
                content = dynamic.getContent();
        DynamicMyFavoriteEntity.DataBean.DynamicInCircleBean.ContentBean.TopicBean
                topic = content.getTopic();
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
        setDynamic_created_at(dynamic.getUpdated_at());
        setCount_of_participation(dynamic.getCount_of_participation());
        if (dynamic.getHas_favorite() == 0) {
            setHas_favorite(false);
        } else {
            setHas_favorite(true);
        }
        //设置用户数据
        UserInfo userInfo = new UserInfo();
        DynamicMyFavoriteEntity.DataBean.DynamicInCircleBean.UserBean user = dynamic.getUser();
        if (user == null) {
            isDataEffective = false;
            return;
        }
        userInfo.setId(user.getId());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setNickName(user.getNickname());
        setUser(userInfo);
        //设置圈子数据
        CircleInfo circleInfo = new CircleInfo();
        DynamicMyFavoriteEntity.DataBean.DynamicInCircleBean.CircleBean circle = dynamic.getCircle();
        if (circle == null) {
            isDataEffective = false;
            return;
        }
        circleInfo.setCircleId(circle.getId());
        circleInfo.setCircleName(circle.getName());
        circleInfo.setCircleType(circle.getType());
        circleInfo.setPictureUrl(circle.getPicture());
        circleInfo.setIntroduction(circle.getIntroduction());
        setCircle(circleInfo);
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
