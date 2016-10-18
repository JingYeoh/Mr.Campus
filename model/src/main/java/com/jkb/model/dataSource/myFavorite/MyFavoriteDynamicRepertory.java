package com.jkb.model.dataSource.myFavorite;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.dynamic.DynamicMyFavoriteEntity;
import com.jkb.api.entity.operation.OperationActionEntity;

/**
 * 我的普通数据的数据仓库类
 * Created by JustKiddingBaby on 2016/10/14.
 */

public class MyFavoriteDynamicRepertory implements MyFavoriteDataSource {

    private MyFavoriteDataSource localDataSource;
    private MyFavoriteDataSource remoteDataSource;

    private MyFavoriteDynamicRepertory(
            MyFavoriteDataSource localDataSource, MyFavoriteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static MyFavoriteDynamicRepertory INSTANCE = null;

    public static MyFavoriteDynamicRepertory getInstance(
            @NonNull MyFavoriteDataSource localDataSource,
            @NonNull MyFavoriteDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new MyFavoriteDynamicRepertory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getMyFavoriteAllDynamic(
            String Authorization, int user_id, @NonNull int ownerId, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicMyFavoriteEntity>> apiCallback) {
        remoteDataSource.getMyFavoriteAllDynamic(Authorization,
                user_id, ownerId, page, apiCallback);
    }

    @Override
    public void getMyFavoriteNormalDynamic(
            String Authorization, int user_id, @NonNull int ownerId, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicMyFavoriteEntity>> apiCallback) {
        remoteDataSource.getMyFavoriteNormalDynamic(Authorization,
                user_id, ownerId, page, apiCallback);
    }

    @Override
    public void getMyFavoriteTopicDynamic(
            String Authorization, int user_id, @NonNull int ownerId, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicMyFavoriteEntity>> apiCallback) {
        remoteDataSource.getMyFavoriteTopicDynamic(Authorization,
                user_id, ownerId, page, apiCallback);
    }

    @Override
    public void getMyFavoriteArticleDynamic(
            String Authorization, int user_id, @NonNull int ownerId, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicMyFavoriteEntity>> apiCallback) {
        remoteDataSource.getMyFavoriteArticleDynamic(Authorization,
                user_id, ownerId, page, apiCallback);
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
