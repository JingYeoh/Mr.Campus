package com.jkb.core.contract.dynamicDetail.data;

import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentData;

import java.util.List;

/**
 * 普通动态详情数据
 * Created by JustKiddingBaby on 2016/9/17.
 */

public class DynamicDetailNormalData {
    private int id;
    private String title;
    private String tag;
    private String doc;
    private String[] imgs;
    private boolean hasFavorite;
    private int comments_count;//评论数
    private String created_at;
    private int operation_count;//喜欢数
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

    public String[] getImgs() {
        return imgs;
    }

    public void setImgs(String[] imgs) {
        this.imgs = imgs;
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
}
