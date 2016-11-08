package com.jkb.core.data.circle.dynamicInBlackList;

import com.jkb.api.entity.circle.CircleDynamicInBlackListEntity;
import com.jkb.core.data.info.dynamic.content.DynamicContentArticleInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章圈子拉黑动态数据类
 * Created by JustKiddingBaby on 2016/11/5.
 */

public class ArticleDynamicInBlackList extends DynamicInBlackList {

    private boolean isDataEffective = true;

    private DynamicContentArticleInfo articleInfo;

    public ArticleDynamicInBlackList(
            List<CircleDynamicInBlackListEntity.DataBean.ContentBean.ArticleBean> articleBean) {
        handleArticleData(articleBean);
    }

    @Override
    public DynamicInBlackList getDynamicInBlackList() {
        if (isDataEffective) {
            return this;
        }
        return null;
    }

    /**
     * 处理文章数据
     */
    private void handleArticleData(
            List<CircleDynamicInBlackListEntity.DataBean.ContentBean.ArticleBean> articleBeans) {
        if (articleBeans == null || articleBeans.size() == 0) {
            isDataEffective = false;
            return;
        }
        articleInfo = new DynamicContentArticleInfo();
        List<DynamicContentArticleInfo.Article> articles = new ArrayList<>();
        for (CircleDynamicInBlackListEntity.DataBean.ContentBean.ArticleBean articleBean :
                articleBeans) {
            DynamicContentArticleInfo.Article article = new DynamicContentArticleInfo.Article();
            article.setImg(articleBean.getImg());
            article.setDoc(articleBean.getDoc());
            articles.add(article);
        }
        articleInfo.setArticle(articles);
        setArticleInfo(articleInfo);
    }

    public DynamicContentArticleInfo getArticleInfo() {
        return articleInfo;
    }

    public void setArticleInfo(DynamicContentArticleInfo articleInfo) {
        this.articleInfo = articleInfo;
    }
}
