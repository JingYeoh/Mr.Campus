package com.jkb.model.dataSource.myDynamic.article.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.dynamic.DynamicArticleEntity;
import com.jkb.api.entity.dynamic.DynamicArticleListEntity;
import com.jkb.api.entity.dynamic.DynamicNormalEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.net.dynamic.DynamicApi;
import com.jkb.api.net.operation.OperationApi;
import com.jkb.model.dataSource.myDynamic.article.MyDynamicArticleDataSource;
import com.jkb.model.utils.StringUtils;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 我的普通数据的数据仓库类
 * Created by JustKiddingBaby on 2016/10/14.
 */

public class MyDynamicArticleRemoteDataSource implements MyDynamicArticleDataSource {


    private MyDynamicArticleRemoteDataSource() {
    }

    private static MyDynamicArticleRemoteDataSource INSTANCE = null;

    public static MyDynamicArticleRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MyDynamicArticleRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getMyDynamicArticle(
            String Authorization, @NonNull int user_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicArticleListEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        DynamicApi dynamicApi = factory.createApi(DynamicApi.class);
        Call<ApiResponse<DynamicArticleListEntity>> call;
        if (StringUtils.isEmpty(Authorization)) {
            call = dynamicApi.getArticleMyDynamic(
                    Config.D_TYPE_ARTICLE, user_id,
                    page, Config.PARTIAL_TYPE_ARTICLE);
        } else {
            call = dynamicApi.getArticleMyDynamic(
                    Authorization, Config.D_TYPE_ARTICLE, user_id,
                    page, Config.PARTIAL_TYPE_ARTICLE);
        }
        Type type = new TypeToken<ApiResponse<DynamicArticleListEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<DynamicArticleListEntity>>(apiCallback, call, type);
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
