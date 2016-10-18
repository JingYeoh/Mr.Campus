package com.jkb.core.data.dynamic.myUnOriginal.myFavorite.circle;

import com.jkb.api.entity.dynamic.DynamicMyFavoriteEntity;
import com.jkb.core.data.info.circle.CircleInfo;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.MyFavoriteDynamicData;
import com.jkb.core.data.info.user.UserInfo;

import java.util.List;

/**
 * 圈子内动态：普通的数据类
 * Created by JustKiddingBaby on 2016/10/16.
 */

public class CircleNormalDynamic extends DynamicInCircleDynamic {

    private boolean isDataEffective;
    //data
    private NormalContent normalContent;

    public CircleNormalDynamic(
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
        if(content==null){
            isDataEffective = false;
            return;
        }
        DynamicMyFavoriteEntity.DataBean.DynamicInCircleBean.ContentBean.NormalBean normal =
                content.getNormal();
        if (normal == null ) {
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
