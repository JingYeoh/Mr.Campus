package com.jkb.model.dataSource.usersList.fans;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.operation.OperationUserEntity;

/**
 * 粉丝的数据仓库类
 * Created by JustKiddingBaby on 2016/8/21.
 */

public class FansDataResponsitory implements FansDataSource {

    private FansDataSource localDataSource;
    private FansDataSource remoteDataSource;

    private FansDataResponsitory(FansDataSource localDataSource, FansDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static FansDataResponsitory INSTANCE;

    public static FansDataResponsitory getInstance(
            @NonNull FansDataSource localDataSource, @NonNull FansDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new FansDataResponsitory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void fans(
            @NonNull String Authorization, @NonNull int page,
            @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationUserEntity>> apiCallback) {
        remoteDataSource.fans(Authorization, page, target_id, apiCallback);
    }

    @Override
    public void payAttentionOrCancle(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
        remoteDataSource.payAttentionOrCancle(Authorization, user_id, target_id, apiCallback);
    }
}
