package com.jkb.model.dataSource.circle.circleIndex.remote;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleCreateEntity;
import com.jkb.api.entity.circle.CircleInfoEntity;
import com.jkb.api.net.circle.CircleInfoApi;
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
}
