package com.jkb.model.dataSource.dynamicDetail.normal.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.comment.CommentListEntity;
import com.jkb.api.entity.comment.CommentSendEntity;
import com.jkb.api.entity.dynamic.DynamicNormalEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.net.comment.CommentApi;
import com.jkb.api.net.dynamic.DynamicApi;
import com.jkb.api.net.operation.OperationApi;
import com.jkb.model.dataSource.dynamicDetail.normal.DynamicDetailNormalDataSource;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 获取普通动态详情的本地数据来源类
 * Created by JustKiddingBaby on 2016/9/17.
 */

public class DynamicDetailNormalRemoteDataSource implements DynamicDetailNormalDataSource {


    private DynamicDetailNormalRemoteDataSource() {
    }

    private static DynamicDetailNormalRemoteDataSource INSTANCE = null;

    public static DynamicDetailNormalRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DynamicDetailNormalRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getNormalDynamic(
            @NonNull int user_id, @NonNull int dynamic_id,
            @NonNull ApiCallback<ApiResponse<DynamicNormalEntity>> apiCallback) {
        ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
        apiFactory.setHttpClient(apiFactory.getHttpClient());
        apiFactory.initRetrofit();
        DynamicApi dynamicApi = apiFactory.createApi(DynamicApi.class);
        Call<ApiResponse<DynamicNormalEntity>> call;
        call = dynamicApi.getNormalDynamic(user_id, dynamic_id);
        Type type = new TypeToken<ApiResponse<DynamicNormalEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<DynamicNormalEntity>>(apiCallback, call, type);
    }

    @Override
    public void getComment$Apply(
            String Authorization, @NonNull int dynamicId, @NonNull int page,
            ApiCallback<ApiResponse<CommentListEntity>> apiCallback) {
        ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
        apiFactory.setHttpClient(apiFactory.getHttpClient());
        apiFactory.initRetrofit();
        CommentApi commentApi = apiFactory.createApi(CommentApi.class);
        Call<ApiResponse<CommentListEntity>> call;
        call = commentApi.getComment$Reply(Authorization, dynamicId, page);
        Type type = new TypeToken<ApiResponse<CommentListEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<CommentListEntity>>(apiCallback, call, type);
    }

    @Override
    public void likeComment(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
        ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
        apiFactory.setHttpClient(apiFactory.genericClient());
        apiFactory.initRetrofit();
        OperationApi operationApi = apiFactory.createApi(OperationApi.class);
        Call<ApiResponse<OperationActionEntity>> call;
        call = operationApi.like(Authorization, Config.ACTION_LIKE, user_id, target_id);
        Type type = new TypeToken<ApiResponse<OperationActionEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<OperationActionEntity>>(apiCallback, call, type);
    }

    @Override
    public void favorite(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        OperationApi operationApi = factory.createApi(OperationApi.class);
        Call<ApiResponse<OperationActionEntity>> call;
        call = operationApi.like(Authorization, Config.ACTION_FAVORITE, user_id, target_id);
        Type type = new TypeToken<ApiResponse<OperationActionEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<OperationActionEntity>>(apiCallback, call, type);
    }

    @Override
    public void sendComment(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int dynamic_id,
            @NonNull String comment,
            @NonNull ApiCallback<ApiResponse<CommentSendEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        CommentApi commentApi = factory.createApi(CommentApi.class);
        Call<ApiResponse<CommentSendEntity>> call;
        call = commentApi.sendComment(Authorization, user_id, dynamic_id, comment);
        Type type = new TypeToken<ApiResponse<CommentSendEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<CommentSendEntity>>(apiCallback, call, type);
    }
}
