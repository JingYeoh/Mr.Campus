package com.jkb.core.data.info.dynamic.content;

import java.util.List;

/**
 * 动态内容：文章
 * Created by JustKiddingBaby on 2016/10/17.
 */

public class DynamicContentArticleInfo {

    private List<Article> article;

    public List<Article> getArticle() {
        return article;
    }

    public void setArticle(List<Article> article) {
        this.article = article;
    }

    public static class Article {
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
}
