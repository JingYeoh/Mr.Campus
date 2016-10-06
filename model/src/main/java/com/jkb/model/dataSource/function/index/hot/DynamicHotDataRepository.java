package com.jkb.model.dataSource.function.index.hot;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicPopularListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;

/**
 * 首页——动态：数据来源仓库类
 * Created by JustKiddingBaby on 2016/9/3.
 */

public class DynamicHotDataRepository implements DynamicHotDataSource {

    private DynamicHotDataSource localDataSource;
    private DynamicHotDataSource remoteDataSource;

    private DynamicHotDataRepository(
            DynamicHotDataSource localDataSource, DynamicHotDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static DynamicHotDataRepository INSTANCE = null;

    public static DynamicHotDataRepository getInstance(
            @NonNull DynamicHotDataSource localDataSource,
            @NonNull DynamicHotDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new DynamicHotDataRepository(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getAllHotDynamic(
            @NonNull String Authorization, @NonNull int schoolId, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicPopularListEntity>> apiCallback) {
        remoteDataSource.getAllHotDynamic(Authorization, schoolId, page, apiCallback);
    }

    @Override
    public void favorite(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
        remoteDataSource.favorite(Authorization, user_id, target_id, apiCallback);
    }

    @Override
    public void subscribeCircle(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
        remoteDataSource.subscribeCircle(Authorization, user_id, target_id, apiCallback);
    }

    @Override
    public void payAttentionUser(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
        remoteDataSource.payAttentionUser(Authorization, user_id, target_id, apiCallback);
    }
}
