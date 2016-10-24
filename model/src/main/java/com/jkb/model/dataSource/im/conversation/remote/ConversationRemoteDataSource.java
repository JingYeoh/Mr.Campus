package com.jkb.model.dataSource.im.conversation.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleInfoEntity;
import com.jkb.api.entity.user.UserInfoEntity;
import com.jkb.api.net.circle.CircleInfoApi;
import com.jkb.api.net.user.UserInfoApi;
import com.jkb.model.dataSource.im.conversation.ConversationDataSource;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 会话的本地数据来源类
 * Created by JustKiddingBaby on 2016/10/20.
 */

public class ConversationRemoteDataSource implements ConversationDataSource {


    public ConversationRemoteDataSource() {
    }

    private static ConversationRemoteDataSource INSTANCE = null;

    public static ConversationRemoteDataSource newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConversationRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getUserInfo(int user_id, ApiCallback<ApiResponse<UserInfoEntity>> apiCallback) {
        //获取网络数据
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        UserInfoApi userInfoApi = factory.createApi(UserInfoApi.class);
        Call<ApiResponse<UserInfoEntity>> call;
        call = userInfoApi.getUserInfo(user_id);
        Type type = new TypeToken<ApiResponse<UserInfoEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<UserInfoEntity>>(apiCallback, call, type);
    }

    @Override
    public void getCircleInfo(
            @NonNull int userId, @NonNull int id,
            @NonNull ApiCallback<ApiResponse<CircleInfoEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        CircleInfoApi circleInfoApi = factory.createApi(CircleInfoApi.class);
        Call<ApiResponse<CircleInfoEntity>> call;
        call = circleInfoApi.circleInfo(userId, id);
        Type type = new TypeToken<ApiResponse<CircleInfoEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<CircleInfoEntity>>(apiCallback, call, type);
    }
}
