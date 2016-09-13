package com.jkb.model.dataSource.function.index.dynamic;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;

/**
 * 首页——动态：数据来源仓库类
 * Created by JustKiddingBaby on 2016/9/3.
 */

public class DynamicDataRepository implements DynamicDataSource {

    private DynamicDataSource localDataSource;
    private DynamicDataSource remoteDataSource;

    private DynamicDataRepository(
            DynamicDataSource localDataSource, DynamicDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static DynamicDataRepository INSTANCE = null;

    public static DynamicDataRepository getInstance(
            @NonNull DynamicDataSource localDataSource,
            @NonNull DynamicDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new DynamicDataRepository(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getAllDynamic(
            @NonNull String Authorization, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicListEntity>> apiCallback) {
        remoteDataSource.getAllDynamic(Authorization, page, apiCallback);
    }

    @Override
    public void favorite(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
        remoteDataSource.favorite(Authorization, user_id, target_id, apiCallback);
    }
}
