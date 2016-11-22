package com.jkb.model.dataSource.personCenter.original.myDynamic.topic.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.dynamic.DynamicTopicListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.net.dynamic.DynamicApi;
import com.jkb.api.net.operation.OperationApi;
import com.jkb.model.dataSource.personCenter.original.myDynamic.topic.MyDynamicTopicDataSource;
import com.jkb.model.utils.StringUtils;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 我的普通数据的数据仓库类
 * Created by JustKiddingBaby on 2016/10/14.
 */

public class MyDynamicTopicRemoteDataSource implements MyDynamicTopicDataSource {


    private MyDynamicTopicRemoteDataSource() {
    }

    private static MyDynamicTopicRemoteDataSource INSTANCE = null;

    public static MyDynamicTopicRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MyDynamicTopicRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getMyDynamicTopic(
            String Authorization, @NonNull int user_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicTopicListEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        DynamicApi dynamicApi = factory.createApi(DynamicApi.class);
        Call<ApiResponse<DynamicTopicListEntity>> call;
        if (StringUtils.isEmpty(Authorization)) {
            call = dynamicApi.getTopicMyDynamic(
                    Config.D_TYPE_TOPIC, user_id,
                    page, Config.PARTIAL_TYPE_TOPIC);
        } else {
            call = dynamicApi.getTopicMyDynamic(
                    Authorization, Config.D_TYPE_TOPIC, user_id,
                    page, Config.PARTIAL_TYPE_TOPIC);
        }
        Type type = new TypeToken<ApiResponse<DynamicTopicListEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<DynamicTopicListEntity>>(apiCallback, call, type);
    }

    @Override
    public void deleteDynamic(
            @NonNull String Authorization, @NonNull int dynamic_id, @NonNull int operator_id,
            @NonNull ApiCallback<ApiResponse<DynamicActionEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        DynamicApi dynamicApi = factory.createApi(DynamicApi.class);
        Call<ApiResponse<DynamicActionEntity>> call;
        call = dynamicApi.deleteDynamic(Authorization, dynamic_id, operator_id);
        Type type = new TypeToken<ApiResponse<DynamicActionEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<DynamicActionEntity>>(apiCallback, call, type);
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
