package com.jkb.model.dataSource.personCenter.remote;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.operation.OperationVerifyPayAttentionEntity;
import com.jkb.api.entity.user.UserActionCircleEntity;
import com.jkb.api.entity.user.UserInfoEntity;
import com.jkb.api.net.operation.OperationApi;
import com.jkb.api.net.operation.OperationVerifyPayAttentionApi;
import com.jkb.api.net.user.UserActionApi;
import com.jkb.api.net.user.UserInfoApi;
import com.jkb.model.dataSource.personCenter.PersonCenterDataSource;
import com.jkb.model.intfc.BitmapLoadedCallback;
import com.jkb.model.net.ImageLoaderFactory;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 个人中心的远程数据来源类
 * Created by JustKiddingBaby on 2016/8/17.
 */

public class PersonCenterRemoteDataSource implements PersonCenterDataSource {


    private static PersonCenterRemoteDataSource INSTANCE;

    private PersonCenterRemoteDataSource() {
    }

    public static PersonCenterRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PersonCenterRemoteDataSource();
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
    public void loadHeadImgByUrl(@NonNull final String url, @NonNull final BitmapLoadedCallback callback) {
        ImageLoaderFactory.getInstance().loadImage(url, null, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
//                callback.onDataNotAvailable(url);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                callback.onDataNotAvailable(url);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                callback.onBitmapDataLoaded(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                callback.onDataNotAvailable(url);
            }
        });
    }

    @Override
    public void visit(@NonNull String authorization, @NonNull int user_id,
                      @NonNull int target_id,
                      @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
        //获取网络数据
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        OperationApi operationApi = factory.createApi(OperationApi.class);
        Call<ApiResponse<OperationActionEntity>> call = null;
        call = operationApi.visit(authorization, Config.ACTION_VISIT, user_id, target_id);
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

    @Override
    public void verifyIfPayAttention(
            @NonNull int user_id, @NonNull int visitor_id,
            @NonNull ApiCallback<ApiResponse<OperationVerifyPayAttentionEntity>> apiCallback) {
        //获取网络数据
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        OperationVerifyPayAttentionApi payAttentionApi =
                factory.createApi(OperationVerifyPayAttentionApi.class);
        Call<ApiResponse<OperationVerifyPayAttentionEntity>> call;
        call = payAttentionApi.verifyIfPayAttention(user_id, visitor_id);
        Type type = new TypeToken<ApiResponse<OperationVerifyPayAttentionEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<OperationVerifyPayAttentionEntity>>(apiCallback, call, type);
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
