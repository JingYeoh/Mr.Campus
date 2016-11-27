package com.jkb.core.data.search.detail.dynamic;

import com.jkb.api.config.Config;
import com.jkb.api.entity.search.SearchEntity;
import com.jkb.core.data.search.detail.SearchData;
import com.jkb.model.utils.LogUtils;

/**
 * 搜索的动态数据类
 * Created by JustKiddingBaby on 2016/11/26.
 */

public class SearchDynamicData extends SearchData {

    public SearchDynamicData(SearchEntity.DataBean dataBean) {
        handleDynamicData(dataBean);
    }

    protected SearchDynamicData() {
    }

    private SearchDynamicData INSTANCE = null;

    @Override
    public SearchData getSearchData() {
        if (isDataEffective) {
            return INSTANCE.getSearchData();
        }
        return null;
    }

    /**
     * 处理数据
     */
    private void handleDynamicData(SearchEntity.DataBean dataBean) {
        SearchEntity.DataBean.DynamicBean dynamic = dataBean.getDynamic();
        if (dynamic == null) {
            isDataEffective = false;
            return;
        }
        SearchEntity.DataBean.ContentBean content = dynamic.getContent();
        if (content == null) {
            isDataEffective = false;
            return;
        }
        String dtype = dynamic.getDtype();
        switch (dtype) {
            case Config.D_TYPE_ARTICLE:
                INSTANCE = new SearchDynamicArticleData(content.getArticle());
                break;
            case Config.D_TYPE_NORMAL:
                INSTANCE = new SearchDynamicNormalData(content.getNormal());
                break;
            case Config.D_TYPE_TOPIC:
                INSTANCE = new SearchDynamicTopicData(content.getTopic());
                break;
            default:
                isDataEffective = false;
                break;
        }
        //绑定数据
        bindData(dynamic);
    }

    /**
     * 绑定数据
     */
    private void bindData(SearchEntity.DataBean.DynamicBean reqDynamic) {
        if (!isDataEffective) {
            return;
        }
        LogUtils.d(SearchDynamicData.class, "bindData");
        INSTANCE.setId(reqDynamic.getId());
        INSTANCE.setDtype(reqDynamic.getDtype());
        INSTANCE.setTitle(reqDynamic.getTitle());
        INSTANCE.setTag(reqDynamic.getTag());
        INSTANCE.setCount_of_favorite(reqDynamic.getCount_of_favorite());
        INSTANCE.setCount_of_participation(reqDynamic.getCount_of_participation());
    }

    //数据
    private int id;
    private String dtype;
    private String title;
    private String tag;
    private int count_of_favorite;
    private int count_of_participation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
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
}
