package com.jkb.model.dataSource.usersList.fans.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.operation.OperationUserEntity;
import com.jkb.api.net.operation.OperationApi;
import com.jkb.api.net.operation.OptionUserApi;
import com.jkb.model.dataSource.usersList.fans.FansDataSource;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 获取粉丝列表的远程数据仓库来源类
 * Created by JustKiddingBaby on 2016/8/21.
 */

public class FansDataRemoteSource implements FansDataSource {

    private FansDataRemoteSource() {
    }

    private static FansDataRemoteSource INSTANCE = null;

    public static FansDataRemoteSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FansDataRemoteSource();
        }
        return INSTANCE;
    }

    @Override
    public void fans(
            int user_id, @NonNull int page,
            @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationUserEntity>> apiCallback) {
        //请求网络数据
        ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
        apiFactory.setHttpClient(apiFactory.genericClient());
        apiFactory.initRetrofit();
        OptionUserApi optionUserApi = apiFactory.createApi(OptionUserApi.class);
        Call<ApiResponse<OperationUserEntity>> call;
        call = optionUserApi.payAttention(
                Config.ACTION_PAYATTENTION, user_id, target_id, page);
        Type type = new TypeToken<ApiResponse<OperationUserEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<OperationUserEntity>>(apiCallback, call, type);
    }

    @Override
    public void payAttentionOrCancle(
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
