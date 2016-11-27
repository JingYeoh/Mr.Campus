package com.jkb.core.data.search.detail;

import com.jkb.api.config.Config;
import com.jkb.api.entity.search.SearchEntity;
import com.jkb.core.data.search.detail.circle.SearchCircleData;
import com.jkb.core.data.search.detail.dynamic.SearchDynamicData;
import com.jkb.core.data.search.detail.dynamicInCircle.SearchDynamicInCircleData;
import com.jkb.core.data.search.detail.subject.SearchSubjectData;
import com.jkb.core.data.search.detail.user.SearchUserData;

/**
 * 搜索的数据类
 * Created by JustKiddingBaby on 2016/11/26.
 */

public class SearchData {

    protected boolean isDataEffective = true;

    protected SearchData() {
    }

    public SearchData(SearchEntity.DataBean searchEntity) {
        handleSearchData(searchEntity);
    }

    private SearchData INSTANCE = null;

    /**
     * 得到搜索的数据
     */
    public SearchData getSearchData() {
        if (isDataEffective) {
            return INSTANCE.getSearchData();
        }
        return null;
    }

    /**
     * 处理搜索的数据
     */
    protected void handleSearchData(SearchEntity.DataBean searchEntity) {
        if (searchEntity == null) {
            isDataEffective = false;
            return;
        }
        //处理数据
        String search_type = searchEntity.getSearch_type();
        switch (search_type) {
            case Config.SEARCH_TYPE_USER:
                INSTANCE = new SearchUserData(searchEntity);
                break;
            case Config.SEARCH_TYPE_CIRCLE:
                INSTANCE = new SearchCircleData(searchEntity);
                break;
            case Config.SEARCH_TYPE_DYNAMIC:
                INSTANCE = new SearchDynamicData(searchEntity);
                break;
            case Config.SEARCH_TYPE_DYNAMICINCIRCLE:
                INSTANCE = new SearchDynamicInCircleData(searchEntity);
                break;
            case Config.SEARCH_TYPE_SUBJECT:
                INSTANCE = new SearchSubjectData(searchEntity);
                break;
            default:
                isDataEffective = false;
                break;
        }
        if (!isDataEffective) {
            return;
        }
        bindSearchData(searchEntity);
    }

    /**
     * 绑定搜索的数据
     */
    private void bindSearchData(SearchEntity.DataBean search_type) {
        INSTANCE.setSearch_type(search_type.getSearch_type());
    }

    private String search_type;

    public String getSearch_type() {
        return search_type;
    }

    public void setSearch_type(String search_type) {
        this.search_type = search_type;
    }
}
