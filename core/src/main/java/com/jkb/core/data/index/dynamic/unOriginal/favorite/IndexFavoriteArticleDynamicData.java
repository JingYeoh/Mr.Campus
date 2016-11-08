package com.jkb.core.data.index.dynamic.unOriginal.favorite;

import com.jkb.api.entity.dynamic.DynamicListEntity;
import com.jkb.core.data.index.dynamic.IndexDynamicData;
import com.jkb.core.data.index.dynamic.original.IndexOriginalDynamicData;
import com.jkb.core.data.info.dynamic.content.DynamicContentArticleInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页-动态的原创話題动态实体类
 * Created by JustKiddingBaby on 2016/11/7.
 */

public class IndexFavoriteArticleDynamicData extends IndexFavoriteDynamicData {

    private boolean isDataEffective = true;

    private DynamicContentArticleInfo article;

    public IndexFavoriteArticleDynamicData() {
    }

    public IndexFavoriteArticleDynamicData(
            List<DynamicListEntity.DataBean.DynamicBean.ContentBean.ArticleBean> articleBeen) {
        handleNormalData(articleBeen);
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
            List<DynamicListEntity.DataBean.DynamicBean.ContentBean.ArticleBean> articleBeen) {
        if (articleBeen == null || articleBeen.size() == 0) {
            isDataEffective = false;
            return;
        }
        article = new DynamicContentArticleInfo();
        List<DynamicContentArticleInfo.Article> articles = new ArrayList<>();
        for (DynamicListEntity.DataBean.DynamicBean.ContentBean.ArticleBean articleItem :
                articleBeen) {
            DynamicContentArticleInfo.Article ar = new DynamicContentArticleInfo.Article();
            ar.setDoc(articleItem.getDoc());
            ar.setImg(articleItem.getImg());
            articles.add(ar);
        }
        article.setArticle(articles);
        setArticle(article);
    }

    public DynamicContentArticleInfo getArticle() {
        return article;
    }

    public void setArticle(DynamicContentArticleInfo article) {
        this.article = article;
    }
}
