package com.jkb.model.dataSource.circle.dynamiInBlackList.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleActionEntity;
import com.jkb.api.entity.circle.CircleDynamicInBlackListEntity;
import com.jkb.api.net.circle.CircleInfoApi;
import com.jkb.model.dataSource.circle.dynamiInBlackList.CircleDynamicInCircleBlackListDataSource;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 圈子动态黑名单的远程数据来源类
 * Created by JustKiddingBaby on 2016/11/5.
 */

public class CircleDynamicInCircleBlackListRemoteDataSource
        implements CircleDynamicInCircleBlackListDataSource {

    private CircleDynamicInCircleBlackListRemoteDataSource() {
    }

    private static CircleDynamicInCircleBlackListRemoteDataSource INSTANCE = null;

    public static CircleDynamicInCircleBlackListRemoteDataSource newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CircleDynamicInCircleBlackListRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getAllDynamicsInBlackList(
            @NonNull String Authorization, @NonNull int circle_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<CircleDynamicInBlackListEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        CircleInfoApi circleInfoApi = factory.createApi(CircleInfoApi.class);
        Call<ApiResponse<CircleDynamicInBlackListEntity>> call;
        call = circleInfoApi.getDynamicsInCircleBlackList(Authorization, circle_id, page);
        Type type = new TypeToken<ApiResponse<CircleDynamicInBlackListEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<CircleDynamicInBlackListEntity>>(apiCallback, call, type);
    }

    @Override
    public void pullDynamicOutBlackList(
            @NonNull String Authorization, @NonNull int dynamic_id, @NonNull int user_id,
            @NonNull ApiCallback<ApiResponse<CircleActionEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        CircleInfoApi circleInfoApi = factory.createApi(CircleInfoApi.class);
        Call<ApiResponse<CircleActionEntity>> call;
        call = circleInfoApi.pullDynamicOutBlackList(Authorization, dynamic_id, user_id);
        Type type = new TypeToken<ApiResponse<CircleActionEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<CircleActionEntity>>(apiCallback, call, type);
    }
}
