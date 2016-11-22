package com.jkb.model.dataSource.personCenter.original.myDynamic.circle;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.dynamic.DynamicCircleListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.user.UserActionCircleEntity;

/**
 * 我的普通数据的数据仓库类
 * Created by JustKiddingBaby on 2016/10/14.
 */

public class MyDynamicCircleRepertory implements MyDynamicCircleDataSource {

    private MyDynamicCircleDataSource localDataSource;
    private MyDynamicCircleDataSource remoteDataSource;

    private MyDynamicCircleRepertory(
            MyDynamicCircleDataSource localDataSource, MyDynamicCircleDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static MyDynamicCircleRepertory INSTANCE = null;

    public static MyDynamicCircleRepertory getInstance(
            @NonNull MyDynamicCircleDataSource localDataSource,
            @NonNull MyDynamicCircleDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new MyDynamicCircleRepertory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getMyDynamicCircle(
            String Authorization, @NonNull int user_id, @NonNull int circle_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicCircleListEntity>> apiCallback) {
        remoteDataSource.getMyDynamicCircle(Authorization, user_id, circle_id, page, apiCallback);
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
    @Override
    public void subscribeCircle(
            @NonNull int user_id, int visitor_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<UserActionCircleEntity>> apiCallback) {
        remoteDataSource.subscribeCircle(user_id, visitor_id, page, apiCallback);
    }
}
