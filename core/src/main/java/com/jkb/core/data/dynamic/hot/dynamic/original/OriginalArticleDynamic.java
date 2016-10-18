package com.jkb.core.data.dynamic.hot.dynamic.original;

import com.jkb.api.entity.dynamic.DynamicPopularListEntity;
import com.jkb.core.data.dynamic.hot.HotDynamic;
import com.jkb.core.data.info.user.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 原创的文章热门动态数据
 * Created by JustKiddingBaby on 2016/9/30.
 */

public class OriginalArticleDynamic extends OriginalDynamic {

    private boolean isDataEffective;

    private List<ArticleContent> articleContents;

    public OriginalArticleDynamic(DynamicPopularListEntity.DataBean.DynamicBean dynamic) {
        handleArticleDynamic(dynamic);
    }


    @Override
    public HotDynamic getHotDynamic() {
        if (isDataEffective) {
            return this;
        }
        return null;
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

    /**
     * 解析文章动态数据
     */
    private void handleArticleDynamic(DynamicPopularListEntity.DataBean.DynamicBean dynamic) {
        if (dynamic == null) {
            isDataEffective = false;
            return;
        }
        isDataEffective = true;
        setDynamic_id(dynamic.getDynamic_id());
        setDtype(dynamic.getDtype());
        setTitle(dynamic.getTitle());
        //设置文章内容数据
        DynamicPopularListEntity.DataBean.DynamicBean.ContentBean content = dynamic.getContent();
        articleContents = new ArrayList<>();
        if (content == null) {
            articleContents.clear();
        } else {
            List<DynamicPopularListEntity.DataBean.DynamicBean.ContentBean.ArticleBean> articles
                    = content.getArticle();
            if (articles == null || articles.size() == 0) {
                articleContents.clear();
            } else {
                for (DynamicPopularListEntity.DataBean.DynamicBean.ContentBean.ArticleBean
                        article : articles) {
                    ArticleContent articleContent = new ArticleContent();
                    articleContent.setDoc(article.getDoc());
                    articleContent.setImg(article.getImg());

                    articleContents.add(articleContent);
                }
            }
        }
        setArticleContents(articleContents);
        setTag(dynamic.getTag());
        setCount_of_comment(dynamic.getCount_of_comment());
        setCount_of_favorite(dynamic.getCount_of_favorite());
        setDynamic_created_at(dynamic.getDynamic_created_at());
        if (dynamic.getHas_favorite() == 0) {
            setHas_favorite(false);
        } else {
            setHas_favorite(true);
        }
        //设置用户数据
        UserInfo userInfo = new UserInfo();
        userInfo.setId(dynamic.getCreator_id());
        userInfo.setAvatar(dynamic.getCreator_avatar());
        userInfo.setNickName(dynamic.getCreator_nickname());
        setUser(userInfo);
    }

    public List<ArticleContent> getArticleContents() {
        return articleContents;
    }

    public void setArticleContents(List<ArticleContent> articleContents) {
        this.articleContents = articleContents;
    }
}
