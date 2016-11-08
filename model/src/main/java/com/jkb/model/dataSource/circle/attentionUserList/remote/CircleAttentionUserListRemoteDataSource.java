package com.jkb.model.dataSource.circle.attentionUserList.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleActionEntity;
import com.jkb.api.entity.circle.CircleAttentionUserListEntity;
import com.jkb.api.entity.circle.CircleInfoEntity;
import com.jkb.api.net.circle.CircleInfoApi;
import com.jkb.model.dataSource.circle.attentionUserList.CircleAttentionUserListDataSource;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 圈子成员的远程数据来源类
 * Created by JustKiddingBaby on 2016/11/3.
 */

public class CircleAttentionUserListRemoteDataSource implements CircleAttentionUserListDataSource {

    private static CircleAttentionUserListRemoteDataSource INSTANCE;

    private CircleAttentionUserListRemoteDataSource() {
    }

    public static CircleAttentionUserListRemoteDataSource newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CircleAttentionUserListRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getUsersInCircle(
            String Authorization, @NonNull int circleId, @NonNull int page,
            ApiCallback<ApiResponse<CircleAttentionUserListEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        CircleInfoApi circleInfoApi = factory.createApi(CircleInfoApi.class);
        Call<ApiResponse<CircleAttentionUserListEntity>> call;
        call = circleInfoApi.getUsersInCircle(Authorization, circleId, page);
        Type type = new TypeToken<ApiResponse<CircleAttentionUserListEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<CircleAttentionUserListEntity>>(apiCallback, call, type);
    }

    @Override
    public void getUsersInCircleInBlackList(
            String Authorization, @NonNull int circleId, @NonNull int page,
            ApiCallback<ApiResponse<CircleAttentionUserListEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        CircleInfoApi circleInfoApi = factory.createApi(CircleInfoApi.class);
        Call<ApiResponse<CircleAttentionUserListEntity>> call;
        call = circleInfoApi.getUsersInCircleBlackList(Authorization, circleId, page);
        Type type = new TypeToken<ApiResponse<CircleAttentionUserListEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<CircleAttentionUserListEntity>>(apiCallback, call, type);
    }


    @Override
    public void putUserInBlackList(
            @NonNull String Authorization, @NonNull int id, @NonNull int user_id,
            @NonNull int operator_id,
            @NonNull ApiCallback<ApiResponse<CircleActionEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        CircleInfoApi circleInfoApi = factory.createApi(CircleInfoApi.class);
        Call<ApiResponse<CircleActionEntity>> call;
        call = circleInfoApi.putUserToBlackList(Authorization, id, user_id, operator_id);
        Type type = new TypeToken<ApiResponse<CircleActionEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<CircleActionEntity>>(apiCallback, call, type);
    }

    @Override
    public void pullUserOutBlackList(
            @NonNull String Authorization, @NonNull int id, @NonNull int user_id,
            @NonNull int operator_id,
            @NonNull ApiCallback<ApiResponse<CircleActionEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        CircleInfoApi circleInfoApi = factory.createApi(CircleInfoApi.class);
        Call<ApiResponse<CircleActionEntity>> call;
        call = circleInfoApi.pullUserOutBlackList(Authorization, id, user_id, operator_id);
        Type type = new TypeToken<ApiResponse<CircleActionEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<CircleActionEntity>>(apiCallback, call, type);
    }
}
