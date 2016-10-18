package com.jkb.model.dataSource.myDynamic.circleSelector.remote;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.user.UserActionCircleEntity;
import com.jkb.api.net.user.UserActionApi;
import com.jkb.model.dataSource.myDynamic.circleSelector.CircleSelectorDataSource;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * Created by JustKiddingBaby on 2016/10/18.
 */

public class CircleSelectorRemoteDataSource implements CircleSelectorDataSource {

    private static CircleSelectorRemoteDataSource INSTANCE;

    public static CircleSelectorRemoteDataSource newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CircleSelectorRemoteDataSource();
        }
        return INSTANCE;
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
