package com.jkb.core.data.search.detail.dynamic;

import com.jkb.api.entity.search.SearchEntity;
import com.jkb.core.data.info.dynamic.content.DynamicContentArticleInfo;
import com.jkb.core.data.search.detail.SearchData;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索的文章数据类
 * Created by JustKiddingBaby on 2016/11/26.
 */

public final class SearchDynamicArticleData extends SearchDynamicData {

    private DynamicContentArticleInfo articles;

    public SearchDynamicArticleData(
            List<SearchEntity.DataBean.ContentBean.ArticleBean> articleBean) {
        handleArticleData(articleBean);
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
    private void handleArticleData(List<SearchEntity.DataBean.ContentBean.ArticleBean> articleBean) {
        if (articleBean == null || articleBean.size() == 0) {
            isDataEffective = false;
            return;
        }
        //处理文章数据
        articles = new DynamicContentArticleInfo();
        List<DynamicContentArticleInfo.Article> mArticles = new ArrayList<>();
        for (SearchEntity.DataBean.ContentBean.ArticleBean bean : articleBean) {
            DynamicContentArticleInfo.Article article = new DynamicContentArticleInfo.Article();
            article.setDoc(bean.getDoc());
            article.setImg(bean.getImg());
            mArticles.add(article);
        }
        articles.setArticle(mArticles);
        setArticle(articles);
    }

    public DynamicContentArticleInfo getArticle() {
        return articles;
    }

    public void setArticle(DynamicContentArticleInfo article) {
        this.articles = article;
    }
}
