package com.jkb.model.dataSource.myDynamic.normal;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.dynamic.DynamicNormalListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;

/**
 * 我的普通数据的数据仓库类
 * Created by JustKiddingBaby on 2016/10/14.
 */

public class MyDynamicNormalRepertory implements MyDynamicNormalDataSource {

    private MyDynamicNormalDataSource localDataSource;
    private MyDynamicNormalDataSource remoteDataSource;

    private MyDynamicNormalRepertory(
            MyDynamicNormalDataSource localDataSource, MyDynamicNormalDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static MyDynamicNormalRepertory INSTANCE = null;

    public static MyDynamicNormalRepertory getInstance(
            @NonNull MyDynamicNormalDataSource localDataSource,
            @NonNull MyDynamicNormalDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new MyDynamicNormalRepertory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getMyDynamicNormal(
            String Authorization, @NonNull int user_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicNormalListEntity>> apiCallback) {
        remoteDataSource.getMyDynamicNormal(Authorization, user_id, page, apiCallback);
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
