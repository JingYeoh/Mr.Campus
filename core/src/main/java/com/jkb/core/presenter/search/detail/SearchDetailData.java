package com.jkb.core.presenter.search.detail;

import com.jkb.core.data.search.detail.SearchData;

import java.util.List;

/**
 * 搜索详情的数据类
 * Created by JustKiddingBaby on 2016/11/26.
 */

public class SearchDetailData {


    private List<SearchData> searchData;
    /**
     * userCount : 210
     * articleCount : 48
     * normalCount : 121
     * topicCount : 66
     * subjectCount : 1723
     * circleCount : 234
     */

    private int allCount;
    private int dynamicCount;
    private int userCount;
    private int subjectCount;
    private int circleCount;

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public int getDynamicCount() {
        return dynamicCount;
    }

    public void setDynamicCount(int dynamicCount) {
        this.dynamicCount = dynamicCount;
    }

    public int getSubjectCount() {
        return subjectCount;
    }

    public void setSubjectCount(int subjectCount) {
        this.subjectCount = subjectCount;
    }

    public int getCircleCount() {
        return circleCount;
    }

    public void setCircleCount(int circleCount) {
        this.circleCount = circleCount;
    }

    public List<SearchData> getSearchData() {
        return searchData;
    }

    public void setSearchData(List<SearchData> searchData) {
        this.searchData = searchData;
    }
}
