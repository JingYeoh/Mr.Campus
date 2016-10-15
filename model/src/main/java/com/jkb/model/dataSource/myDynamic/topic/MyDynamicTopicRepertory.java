package com.jkb.model.dataSource.myDynamic.topic;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.dynamic.DynamicTopicEntity;
import com.jkb.api.entity.dynamic.DynamicTopicListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;

/**
 * 我的普通数据的数据仓库类
 * Created by JustKiddingBaby on 2016/10/14.
 */

public class MyDynamicTopicRepertory implements MyDynamicTopicDataSource {

    private MyDynamicTopicDataSource localDataSource;
    private MyDynamicTopicDataSource remoteDataSource;

    private MyDynamicTopicRepertory(
            MyDynamicTopicDataSource localDataSource, MyDynamicTopicDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static MyDynamicTopicRepertory INSTANCE = null;

    public static MyDynamicTopicRepertory getInstance(
            @NonNull MyDynamicTopicDataSource localDataSource,
            @NonNull MyDynamicTopicDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new MyDynamicTopicRepertory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getMyDynamicTopic(
            String Authorization, @NonNull int user_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicTopicListEntity>> apiCallback) {
        remoteDataSource.getMyDynamicTopic(Authorization, user_id, page, apiCallback);
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
