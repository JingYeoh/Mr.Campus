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
import com.jkb.api.entity.user.UserInfoEntity;
import com.jkb.api.net.operation.OperationApi;
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
    public void getUserInfo(String authorization, int user_id, ApiCallback<ApiResponse<UserInfoEntity>> apiCallback) {
        //获取网络数据
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        UserInfoApi userInfoApi = factory.createApi(UserInfoApi.class);
        Call<ApiResponse<UserInfoEntity>> call = null;
        call = userInfoApi.getUserInfo(authorization, user_id);
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
}
