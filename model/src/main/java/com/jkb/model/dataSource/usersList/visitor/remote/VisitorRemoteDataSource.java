package com.jkb.model.dataSource.usersList.visitor.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.user.UserActionUserEntity;
import com.jkb.api.entity.user.UserActionVisitorEntity;
import com.jkb.api.net.operation.OperationApi;
import com.jkb.api.net.user.UserActionApi;
import com.jkb.model.dataSource.usersList.visitor.VisitorDataSource;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 获取访客的本地数据仓库
 * Created by JustKiddingBaby on 2016/8/19.
 */

public class VisitorRemoteDataSource implements VisitorDataSource {

    private Context applicationContext;
    private static VisitorRemoteDataSource INSTANCE;

    public VisitorRemoteDataSource() {
    }


    public static VisitorRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new VisitorRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void visit(@NonNull int page, @NonNull int userId,
                      @NonNull ApiCallback<ApiResponse<UserActionVisitorEntity>> apiCallback) {
        //请求网络数据
        ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
        apiFactory.setHttpClient(apiFactory.genericClient());
        apiFactory.initRetrofit();
        UserActionApi userActionApi = apiFactory.createApi(UserActionApi.class);
        Call<ApiResponse<UserActionVisitorEntity>> call;
        call = userActionApi.visitor(Config.ACTION_VISIT, userId, page);
        Type type = new TypeToken<ApiResponse<UserActionVisitorEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<UserActionVisitorEntity>>(apiCallback, call, type);
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
        call = operationApi.payAttention(Authorization, Config.ACTION_SUBSCRIBE, user_id, target_id);
        Type type = new TypeToken<ApiResponse<OperationActionEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<OperationActionEntity>>(apiCallback, call, type);
    }
}
