package com.jkb.core.data.dynamic.myUnOriginal.myFavorite.circle;

import com.jkb.api.entity.dynamic.DynamicMyFavoriteEntity;
import com.jkb.core.data.info.circle.CircleInfo;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.MyFavoriteDynamicData;
import com.jkb.core.data.info.user.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子内动态：文章的数据类
 * Created by JustKiddingBaby on 2016/10/16.
 */

public class CircleArticleDynamic extends DynamicInCircleDynamic {


    private boolean isDataEffective;

    private List<ArticleContent> articleContents;

    public CircleArticleDynamic(
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
        List<DynamicMyFavoriteEntity.DataBean.DynamicInCircleBean.ContentBean.ArticleBean> article
                = content.getArticle();
        if (article == null || article.size() == 0) {
            isDataEffective = false;
            return;
        }
        articleContents = new ArrayList<>();
        for (DynamicMyFavoriteEntity.DataBean.DynamicInCircleBean.ContentBean.ArticleBean articleBean :
                article) {
            ArticleContent articleContent = new ArticleContent();
            articleContent.setImg(articleBean.getImg());
            articleContent.setDoc(articleBean.getDoc());

            articleContents.add(articleContent);
        }
        setArticleContents(articleContents);
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


    public static class ArticleContent {
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

    public List<ArticleContent> getArticleContents() {
        return articleContents;
    }

    public void setArticleContents(List<ArticleContent> articleContents) {
        this.articleContents = articleContents;
    }
}
