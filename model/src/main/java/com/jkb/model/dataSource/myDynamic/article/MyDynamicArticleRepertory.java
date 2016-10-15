package com.jkb.model.dataSource.myDynamic.article;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.dynamic.DynamicArticleEntity;
import com.jkb.api.entity.dynamic.DynamicArticleListEntity;
import com.jkb.api.entity.dynamic.DynamicNormalEntity;
import com.jkb.api.entity.operation.OperationActionEntity;

/**
 * 我的普通数据的数据仓库类
 * Created by JustKiddingBaby on 2016/10/14.
 */

public class MyDynamicArticleRepertory implements MyDynamicArticleDataSource {

    private MyDynamicArticleDataSource localDataSource;
    private MyDynamicArticleDataSource remoteDataSource;

    private MyDynamicArticleRepertory(
            MyDynamicArticleDataSource localDataSource, MyDynamicArticleDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static MyDynamicArticleRepertory INSTANCE = null;

    public static MyDynamicArticleRepertory getInstance(
            @NonNull MyDynamicArticleDataSource localDataSource,
            @NonNull MyDynamicArticleDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new MyDynamicArticleRepertory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getMyDynamicArticle(
            String Authorization, @NonNull int user_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicArticleListEntity>> apiCallback) {
        remoteDataSource.getMyDynamicArticle(Authorization, user_id, page, apiCallback);
    }

    @Override
    public void deleteDynamic(
            @NonNull String Authorization, @NonNull int dynamic_id, @NonNull int operator_id,
            @NonNull ApiCallback<ApiResponse<DynamicActionEntity>> apiCallback) {
        remoteDataSource.deleteDynamic(Authorization, dynamic_id, operator_id, apiCallback);
    }

    @Override
    public void favorite(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
        remoteDataSource.favorite(Authorization, user_id, target_id, apiCallback);
    }
}
