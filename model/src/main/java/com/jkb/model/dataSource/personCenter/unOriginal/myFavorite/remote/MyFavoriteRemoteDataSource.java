package com.jkb.model.dataSource.personCenter.unOriginal.myFavorite.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.dynamic.DynamicMyFavoriteEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.net.dynamic.DynamicApi;
import com.jkb.api.net.operation.OperationApi;
import com.jkb.model.dataSource.personCenter.unOriginal.myFavorite.MyFavoriteDataSource;
import com.jkb.model.utils.StringUtils;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 我的普通数据的数据仓库类
 * Created by JustKiddingBaby on 2016/10/14.
 */

public class MyFavoriteRemoteDataSource implements MyFavoriteDataSource {


    private MyFavoriteRemoteDataSource() {
    }

    private static MyFavoriteRemoteDataSource INSTANCE = null;

    public static MyFavoriteRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MyFavoriteRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getMyFavoriteAllDynamic(
            String Authorization, int user_id, @NonNull int ownerId, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicMyFavoriteEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        DynamicApi dynamicApi = factory.createApi(DynamicApi.class);
        Call<ApiResponse<DynamicMyFavoriteEntity>> call;
        if (StringUtils.isEmpty(Authorization)) {
            call = dynamicApi.getMyFavoriteDynamic(Config.ACTION_TYPE_FAVORITE,
                    ownerId, page);
        } else {
            call = dynamicApi.getMyFavoriteDynamic(Authorization, Config.ACTION_TYPE_FAVORITE,
                    user_id, ownerId, page);
        }
        Type type = new TypeToken<ApiResponse<DynamicMyFavoriteEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<DynamicMyFavoriteEntity>>(apiCallback, call, type);
    }

    @Override
    public void getMyFavoriteNormalDynamic(
            String Authorization, int user_id, @NonNull int ownerId, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicMyFavoriteEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        DynamicApi dynamicApi = factory.createApi(DynamicApi.class);
        Call<ApiResponse<DynamicMyFavoriteEntity>> call;
        if (StringUtils.isEmpty(Authorization)) {
            call = dynamicApi.getMyFavoriteDynamic(Config.ACTION_TYPE_FAVORITE,
                    ownerId, page, Config.PARTIAL_TYPE_NORMAL);
        } else {
            call = dynamicApi.getMyFavoriteDynamic(Authorization, Config.ACTION_TYPE_FAVORITE,
                    user_id, ownerId, page, Config.PARTIAL_TYPE_NORMAL);
        }
        Type type = new TypeToken<ApiResponse<DynamicMyFavoriteEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<DynamicMyFavoriteEntity>>(apiCallback, call, type);
    }

    @Override
    public void getMyFavoriteTopicDynamic(
            String Authorization, int user_id, @NonNull int ownerId, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicMyFavoriteEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        DynamicApi dynamicApi = factory.createApi(DynamicApi.class);
        Call<ApiResponse<DynamicMyFavoriteEntity>> call;
        if (StringUtils.isEmpty(Authorization)) {
            call = dynamicApi.getMyFavoriteDynamic(Config.ACTION_TYPE_FAVORITE,
                    ownerId, page, Config.PARTIAL_TYPE_TOPIC);
        } else {
            call = dynamicApi.getMyFavoriteDynamic(Authorization, Config.ACTION_TYPE_FAVORITE,
                    user_id, ownerId, page, Config.PARTIAL_TYPE_TOPIC);
        }
        Type type = new TypeToken<ApiResponse<DynamicMyFavoriteEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<DynamicMyFavoriteEntity>>(apiCallback, call, type);
    }

    @Override
    public void getMyFavoriteArticleDynamic(
            String Authorization, int user_id, @NonNull int ownerId, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicMyFavoriteEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        DynamicApi dynamicApi = factory.createApi(DynamicApi.class);
        Call<ApiResponse<DynamicMyFavoriteEntity>> call;
        if (StringUtils.isEmpty(Authorization)) {
            call = dynamicApi.getMyFavoriteDynamic(Config.ACTION_TYPE_FAVORITE,
                    ownerId, page, Config.PARTIAL_TYPE_ARTICLE);
        } else {
            call = dynamicApi.getMyFavoriteDynamic(Authorization, Config.ACTION_TYPE_FAVORITE,
                    user_id, ownerId, page, Config.PARTIAL_TYPE_ARTICLE);
        }
        Type type = new TypeToken<ApiResponse<DynamicMyFavoriteEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<DynamicMyFavoriteEntity>>(apiCallback, call, type);
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
