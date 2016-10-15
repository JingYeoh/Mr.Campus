package com.jkb.core.contract.dynamicDetail.data;

/**
 * 普通动态详情数据
 * Created by JustKiddingBaby on 2016/9/17.
 */

public class DynamicDetailTopicData {
    private int id;
    private String title;
    private String tag;
    private String doc;
    private String img;
    private boolean hasFavorite;
    private boolean isOriginal;
    private int comments_count;//评论数
    private String created_at;
    private int operation_count;//喜欢数
    private int partIn_count;//评论数
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

    public boolean isHasFavorite() {
        return hasFavorite;
    }

    public void setHasFavorite(boolean hasFavorite) {
        this.hasFavorite = hasFavorite;
    }

    public boolean isOriginal() {
        return isOriginal;
    }

    public void setOriginal(boolean original) {
        isOriginal = original;
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

    public int getPartIn_count() {
        return partIn_count;
    }

    public void setPartIn_count(int partIn_count) {
        this.partIn_count = partIn_count;
    }

    public DynamicDetailUserData getDynamicDetailUserData() {
        return dynamicDetailUserData;
    }

    public void setDynamicDetailUserData(DynamicDetailUserData dynamicDetailUserData) {
        this.dynamicDetailUserData = dynamicDetailUserData;
    }
}
