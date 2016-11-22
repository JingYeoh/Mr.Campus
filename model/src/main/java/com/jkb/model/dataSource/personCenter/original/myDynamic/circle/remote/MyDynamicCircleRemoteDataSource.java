package com.jkb.model.dataSource.personCenter.original.myDynamic.circle.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.dynamic.DynamicCircleListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.user.UserActionCircleEntity;
import com.jkb.api.net.dynamic.DynamicApi;
import com.jkb.api.net.dynamic.MyDynamicApi;
import com.jkb.api.net.operation.OperationApi;
import com.jkb.api.net.user.UserActionApi;
import com.jkb.model.dataSource.personCenter.original.myDynamic.circle.MyDynamicCircleDataSource;
import com.jkb.model.utils.LogUtils;
import com.jkb.model.utils.StringUtils;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 我的普通数据的数据仓库类
 * Created by JustKiddingBaby on 2016/10/14.
 */

public class MyDynamicCircleRemoteDataSource implements MyDynamicCircleDataSource {

    private static final String TAG = "MyDynamicCircleRDS";

    private MyDynamicCircleRemoteDataSource() {
    }

    private static MyDynamicCircleRemoteDataSource INSTANCE = null;

    public static MyDynamicCircleRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MyDynamicCircleRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getMyDynamicCircle(
            String Authorization, @NonNull int user_id, @NonNull int circle_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicCircleListEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        MyDynamicApi myDynamicApi = factory.createApi(MyDynamicApi.class);
        Call<ApiResponse<DynamicCircleListEntity>> call;
        String partial;
        if (circle_id <= 0) {
            partial = null;
        } else {
            partial = "circle|" + circle_id;
        }
        LogUtils.d(TAG, partial);
        if (StringUtils.isEmpty(Authorization)) {
            if (StringUtils.isEmpty(partial)) {
                call = myDynamicApi.getCircleDynamic(
                        Config.TYPE_CIRCLE, user_id, page);
            } else {
                call = myDynamicApi.getCircleDynamic(
                        Config.TYPE_CIRCLE, user_id, page, partial);
            }
        } else {
            if (StringUtils.isEmpty(partial)) {
                call = myDynamicApi.getCircleDynamic(
                        Authorization, Config.TYPE_CIRCLE, user_id, page);
            } else {
                call = myDynamicApi.getCircleDynamic(
                        Authorization, Config.TYPE_CIRCLE, user_id, page, partial);
            }
        }
        Type type = new TypeToken<ApiResponse<DynamicCircleListEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<DynamicCircleListEntity>>(apiCallback, call, type);
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

    @Override
    public void subscribeCircle(
            @NonNull int user_id, int visitor_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<UserActionCircleEntity>> apiCallback) {
        //获取网络数据
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        UserActionApi userActionApi = factory.createApi(UserActionApi.class);
        Call<ApiResponse<UserActionCircleEntity>> call;
        call = userActionApi.subscribe(Config.ACTION_SUBSCRIBE, user_id, visitor_id, page);
        Type type = new TypeToken<ApiResponse<UserActionCircleEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<UserActionCircleEntity>>(apiCallback, call, type);
    }
}
