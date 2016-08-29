package com.jkb.model.dataSource.usersList.attention.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.user.UserActionUserEntity;
import com.jkb.api.net.operation.OperationApi;
import com.jkb.api.net.user.UserActionApi;
import com.jkb.model.dataSource.usersList.attention.AttentionDataSource;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 关注用户列表的远程数据来源类
 * Created by JustKiddingBaby on 2016/8/17.
 */

public class AttentionRemoteDataSource implements AttentionDataSource {

    private AttentionRemoteDataSource() {
    }

    private static AttentionRemoteDataSource INSTANCE = null;

    public static AttentionRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AttentionRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void payAttention(
            int page, @NonNull int userId, int visitor_id,
            @NonNull ApiCallback<ApiResponse<UserActionUserEntity>> apiCallback) {
        //请求网络数据
        ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
        apiFactory.setHttpClient(apiFactory.genericClient());
        apiFactory.initRetrofit();
        UserActionApi userActionApi = apiFactory.createApi(UserActionApi.class);
        Call<ApiResponse<UserActionUserEntity>> call;
        call = userActionApi.payAttention(Config.ACTION_PAYATTENTION, userId, visitor_id, page);
        Type type = new TypeToken<ApiResponse<UserActionUserEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<UserActionUserEntity>>(apiCallback, call, type);
    }

    @Override
    public void payAttentionOrCancle(
            @NonNull String Authorization, @NonNull int user_id,
            @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
        //请求网络数据
        ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
        apiFactory.setHttpClient(apiFactory.genericClient());
        apiFactory.initRetrofit();
        OperationApi operationApi = apiFactory.createApi(OperationApi.class);
        Call<ApiResponse<OperationActionEntity>> call;
        call = operationApi.payAttention(Authorization, Config.ACTION_PAYATTENTION, user_id, target_id);
        Type type = new TypeToken<ApiResponse<OperationActionEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<OperationActionEntity>>(apiCallback, call, type);
    }
}
