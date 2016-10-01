package com.jkb.model.dataSource.function.index.hot.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicPopularListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.net.dynamic.DynamicApi;
import com.jkb.api.net.operation.OperationApi;
import com.jkb.model.dataSource.function.index.hot.DynamicHotDataSource;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 首页——动态：远程数据来源类
 * Created by JustKiddingBaby on 2016/9/3.
 */

public class DynamicHotRemoteDataSource implements DynamicHotDataSource {


    private DynamicHotRemoteDataSource() {
    }

    private static DynamicHotRemoteDataSource INSTANCE = null;

    public static DynamicHotRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DynamicHotRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getAllHotDynamic(
            @NonNull String Authorization, @NonNull int schoolId, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicPopularListEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        DynamicApi dynamicApi = factory.createApi(DynamicApi.class);
        Call<ApiResponse<DynamicPopularListEntity>> call;
        call = dynamicApi.getAllPopularDynamic(Authorization, schoolId, page);
        Type type = new TypeToken<ApiResponse<DynamicPopularListEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<DynamicPopularListEntity>>(apiCallback, call, type);
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
}
