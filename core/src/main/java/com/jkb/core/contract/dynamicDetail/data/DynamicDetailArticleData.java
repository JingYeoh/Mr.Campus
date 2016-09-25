package com.jkb.core.contract.dynamicDetail.data;

import java.util.List;

/**
 * 普通动态详情数据
 * Created by JustKiddingBaby on 2016/9/17.
 */

public class DynamicDetailArticleData {
    private int id;
    private String title;
    private String tag;
    private boolean hasFavorite;
    private int comments_count;//评论数
    private String created_at;
    private int operation_count;//喜欢数
    private int readed_count;//阅读数
    private String articleBgImg;//文章背景图片
    private List<ArticleContent> articles;
    //用户的内容
    private DynamicDetailUserData dynamicDetailUserData;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isHasFavorite() {
        return hasFavorite;
    }

    public void setHasFavorite(boolean hasFavorite) {
        this.hasFavorite = hasFavorite;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getOperation_count() {
        return operation_count;
    }

    public void setOperation_count(int operation_count) {
        this.operation_count = operation_count;
    }

    public DynamicDetailUserData getDynamicDetailUserData() {
        return dynamicDetailUserData;
    }

    public void setDynamicDetailUserData(DynamicDetailUserData dynamicDetailUserData) {
        this.dynamicDetailUserData = dynamicDetailUserData;
    }

    public String getArticleBgImg() {
        return articleBgImg;
    }

    public void setArticleBgImg(String articleBgImg) {
        this.articleBgImg = articleBgImg;
    }

    public int getReaded_count() {
        return readed_count;
    }

    public void setReaded_count(int readed_count) {
        this.readed_count = readed_count;
    }

    public List<ArticleContent> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleContent> articles) {
        this.articles = articles;
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
}
