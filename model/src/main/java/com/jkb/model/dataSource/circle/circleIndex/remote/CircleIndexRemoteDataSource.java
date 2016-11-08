package com.jkb.model.dataSource.circle.circleIndex.remote;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.circle.CircleActionEntity;
import com.jkb.api.entity.circle.CircleInfoEntity;
import com.jkb.api.entity.circle.DynamicInCircleListEntity;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.net.circle.CircleInfoApi;
import com.jkb.api.net.operation.OperationApi;
import com.jkb.model.dataSource.circle.circleIndex.CircleIndexDataSource;
import com.jkb.model.intfc.BitmapLoadedCallback;
import com.jkb.model.net.ImageLoaderFactory;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 圈子首页的远程数据来源类
 * Created by JustKiddingBaby on 2016/8/29.
 */

public class CircleIndexRemoteDataSource implements CircleIndexDataSource {

    private static CircleIndexRemoteDataSource INSTANCE = null;

    private CircleIndexRemoteDataSource() {
    }

    public static CircleIndexRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CircleIndexRemoteDataSource();
        }
        return INSTANCE;
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

    @Override
    public void loadBitmapByUrl(
            @NonNull final String url, @NonNull final BitmapLoadedCallback callback) {
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
    public void circleSubscribeOrNot(
            @NonNull int user_id, @NonNull int target_id, @NonNull String Authorization,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
        //请求网络数据
        ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
        apiFactory.setHttpClient(apiFactory.genericClient());
        apiFactory.initRetrofit();
        OperationApi operationApi = apiFactory.createApi(OperationApi.class);
        Call<ApiResponse<OperationActionEntity>> call;
        call = operationApi.subscribe(
                Authorization, Config.ACTION_SUBSCRIBE, user_id, target_id);
        Type type = new TypeToken<ApiResponse<OperationActionEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<OperationActionEntity>>(apiCallback, call, type);
    }

    @Override
    public void getAllDynamicInCircle(
            String Authorization, @NonNull int circleId, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicInCircleListEntity>> apiCallback) {
        //请求网络数据
        ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
        apiFactory.setHttpClient(apiFactory.genericClient());
        apiFactory.initRetrofit();
        CircleInfoApi circleInfoApi = apiFactory.createApi(CircleInfoApi.class);
        Call<ApiResponse<DynamicInCircleListEntity>> call;
        call = circleInfoApi.getAllDynamicInCircle(
                Authorization, circleId, page);
        Type type = new TypeToken<ApiResponse<DynamicInCircleListEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<DynamicInCircleListEntity>>(apiCallback, call, type);
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
    public void putDynamicInBlackList(
            @NonNull String Authorization, @NonNull int dynamic_id, @NonNull int user_id,
            @NonNull ApiCallback<ApiResponse<CircleActionEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        CircleInfoApi circleInfoApi = factory.createApi(CircleInfoApi.class);
        Call<ApiResponse<CircleActionEntity>> call;
        call = circleInfoApi.putDynamicInBlackList(Authorization, dynamic_id, user_id);
        Type type = new TypeToken<ApiResponse<CircleActionEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<CircleActionEntity>>(apiCallback, call, type);
    }
}
