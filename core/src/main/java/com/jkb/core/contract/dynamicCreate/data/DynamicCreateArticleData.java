package com.jkb.core.contract.dynamicCreate.data;

/**
 * 创建文章动态的数据类
 * Created by JustKiddingBaby on 2016/9/27.
 */

public class DynamicCreateArticleData {

    private String articleContent;
    private String articleImgUrl;

    public DynamicCreateArticleData(String articleContent, String articleImgUrl) {
        this.articleContent = articleContent;
        this.articleImgUrl = articleImgUrl;
    }

    public DynamicCreateArticleData() {
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleImgUrl() {
        return articleImgUrl;
    }

    public void setArticleImgUrl(String articleImgUrl) {
        this.articleImgUrl = articleImgUrl;
    }
}
