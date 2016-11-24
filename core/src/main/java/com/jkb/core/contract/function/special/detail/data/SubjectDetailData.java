package com.jkb.core.contract.function.special.detail.data;

import com.jkb.core.data.info.user.UserInfo;

import java.util.List;

/**
 * 专题详情：表白墙数据
 * Created by JustKiddingBaby on 2016/11/22.
 */

public class SubjectDetailData {

    private int id;
    private List<String> imageUlrs;
    private String subjectTitle;
    private String subjectText;
    private UserInfo userInfo;
    private int count_of_comment;
    private int count_of_favorite;
    private int count_of_participation;
    private String created_at;
    private String updated_at;
    private boolean hasFavorite;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getImageUlrs() {
        return imageUlrs;
    }

    public void setImageUlrs(List<String> imageUlrs) {
        this.imageUlrs = imageUlrs;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public String getSubjectText() {
        return subjectText;
    }

    public void setSubjectText(String subjectText) {
        this.subjectText = subjectText;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public int getCount_of_comment() {
        return count_of_comment;
    }

    public void setCount_of_comment(int count_of_comment) {
        this.count_of_comment = count_of_comment;
    }

    public int getCount_of_favorite() {
        return count_of_favorite;
    }

    public void setCount_of_favorite(int count_of_favorite) {
        this.count_of_favorite = count_of_favorite;
    }

    public int getCount_of_participation() {
        return count_of_participation;
    }

    public void setCount_of_participation(int count_of_participation) {
        this.count_of_participation = count_of_participation;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public boolean isHasFavorite() {
        return hasFavorite;
    }

    public void setHasFavorite(boolean hasFavorite) {
        this.hasFavorite = hasFavorite;
    }
}
