package com.jkb.model.dataSource.search.detail.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.search.SearchEntity;
import com.jkb.api.entity.user.UserActionCircleEntity;
import com.jkb.api.net.operation.OperationApi;
import com.jkb.api.net.search.SearchApi;
import com.jkb.api.net.user.UserActionApi;
import com.jkb.model.dataSource.search.detail.SearchDetailDataSource;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 搜索详情的数据来源类
 * Created by JustKiddingBaby on 2016/11/26.
 */

public class SearchDetailRemoteDataSource implements SearchDetailDataSource {

    private SearchDetailRemoteDataSource() {
    }

    private static SearchDetailRemoteDataSource INSTANCE = null;

    public static SearchDetailRemoteDataSource newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SearchDetailRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void searchByKeyWords(
            String Authorization, @NonNull String type, @NonNull String keyWords, int page,
            @NonNull ApiCallback<ApiResponse<SearchEntity>> apiCallback) {
        //获取网络数据
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        SearchApi searchApi = factory.createApi(SearchApi.class);
        Call<ApiResponse<SearchEntity>> call;
        call = searchApi.searchByKeyWords(Authorization, type, keyWords, page);
        Type mType = new TypeToken<ApiResponse<SearchEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<SearchEntity>>(apiCallback, call, mType);
    }

    @Override
    public void payAttentionOrCancel(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
        //请求网络数据
        ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
        apiFactory.setHttpClient(apiFactory.genericClient());
        apiFactory.initRetrofit();
        OperationApi operationApi = apiFactory.createApi(OperationApi.class);
        Call<ApiResponse<OperationActionEntity>> call;
        call = operationApi.payAttention(
                Authorization, Config.ACTION_PAYATTENTION, user_id, target_id);
        Type type = new TypeToken<ApiResponse<OperationActionEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<OperationActionEntity>>(apiCallback, call, type);
    }
}
