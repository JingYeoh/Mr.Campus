package com.jkb.model.dataSource.search.detail;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.search.SearchEntity;

/**
 * 搜索详情的数据仓库类
 * Created by JustKiddingBaby on 2016/11/26.
 */

public class SearchDetailRepertory implements SearchDetailDataSource {

    private SearchDetailDataSource localDataSource;
    private SearchDetailDataSource remoteDataSource;

    private SearchDetailRepertory(SearchDetailDataSource localDataSource,
                                  SearchDetailDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static SearchDetailRepertory INSTANCE = null;

    public static SearchDetailRepertory newInstance(SearchDetailDataSource localDataSource,
                                                    SearchDetailDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new SearchDetailRepertory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void searchByKeyWords(
            String Authorization, @NonNull String type, @NonNull String keyWords, int page,
            @NonNull ApiCallback<ApiResponse<SearchEntity>> apiCallback) {
        remoteDataSource.searchByKeyWords(Authorization, type, keyWords, page, apiCallback);
    }

    @Override
    public void payAttentionOrCancel(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
        remoteDataSource.payAttentionOrCancel(Authorization, user_id, target_id, apiCallback);
    }

    /**
     * 搜索全部范围
     */
    public void searchAllByKeyWords(String Authorization, @NonNull String keyWords, int page,
                                    @NonNull ApiCallback<ApiResponse<SearchEntity>> apiCallback) {
        searchByKeyWords(Authorization, Config.SEARCH_TYPE_ALL, keyWords, page, apiCallback);
    }

    /**
     * 搜索用户范围
     */
    public void searchUserByKeyWords(String Authorization, @NonNull String keyWords, int page,
                                     @NonNull ApiCallback<ApiResponse<SearchEntity>> apiCallback) {
        searchByKeyWords(Authorization, Config.SEARCH_TYPE_USER, keyWords, page, apiCallback);
    }

    /**
     * 搜索圈子范围
     */
    public void searchCircleByKeyWords(String Authorization, @NonNull String keyWords, int page,
                                       @NonNull ApiCallback<ApiResponse<SearchEntity>> apiCallback) {
        searchByKeyWords(Authorization, Config.SEARCH_TYPE_CIRCLE, keyWords, page, apiCallback);
    }

    /**
     * 搜索动态范围
     */
    public void searchDynamicByKeyWords(
            String Authorization, @NonNull String keyWords, int page,
            @NonNull ApiCallback<ApiResponse<SearchEntity>> apiCallback) {
        searchByKeyWords(Authorization, Config.SEARCH_TYPE_DYNAMIC, keyWords, page, apiCallback);
    }

    /**
     * 搜索全部范围
     */
    public void searchDynamicInCircleByKeyWords(
            String Authorization, @NonNull String keyWords, int page,
            @NonNull ApiCallback<ApiResponse<SearchEntity>> apiCallback) {
        searchByKeyWords(Authorization, Config.SEARCH_TYPE_DYNAMICINCIRCLE, keyWords, page, apiCallback);
    }

    /**
     * 搜索专题范围
     */
    public void searchSubjectByKeyWords(
            String Authorization, @NonNull String keyWords, int page,
            @NonNull ApiCallback<ApiResponse<SearchEntity>> apiCallback) {
        searchByKeyWords(Authorization, Config.SEARCH_TYPE_SUBJECT, keyWords, page, apiCallback);
    }
}
