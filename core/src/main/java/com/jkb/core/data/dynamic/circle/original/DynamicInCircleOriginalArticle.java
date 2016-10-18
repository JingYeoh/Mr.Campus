package com.jkb.core.data.dynamic.circle.original;

import com.jkb.api.entity.circle.DynamicInCircleListEntity;
import com.jkb.core.data.dynamic.circle.DynamicInCircle;
import com.jkb.core.data.info.user.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子内原创动态：文章
 * Created by JustKiddingBaby on 2016/10/6.
 */

public class DynamicInCircleOriginalArticle extends DynamicInCircle {

    private boolean isDataEffective;

    private List<ArticleContent> articleContents;

    public DynamicInCircleOriginalArticle(DynamicInCircleListEntity.DataBean dataBean) {
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
        //设置文章内容数据
        DynamicInCircleListEntity.DataBean.ContentBean content = dynamic.getContent();
        articleContents = new ArrayList<>();
        if (content == null) {
            articleContents.clear();
        } else {
            List<DynamicInCircleListEntity.DataBean.ContentBean.ArticleBean> articles = content.getArticle();
            if (articles == null || articles.size() == 0) {
                articleContents.clear();
            } else {
                for (DynamicInCircleListEntity.DataBean.ContentBean.ArticleBean
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
        setDynamic_created_at(dynamic.getCreated_at());
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
