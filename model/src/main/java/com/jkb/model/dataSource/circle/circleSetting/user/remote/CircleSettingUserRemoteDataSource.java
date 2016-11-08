package com.jkb.model.dataSource.circle.circleSetting.user.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.circle.CircleActionEntity;
import com.jkb.api.entity.circle.CircleInfoEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.net.circle.CircleInfoApi;
import com.jkb.api.net.operation.OperationApi;
import com.jkb.model.dataSource.circle.circleSetting.user.CircleSettingUserDataSource;

import java.lang.reflect.Type;

import okhttp3.MultipartBody;
import retrofit2.Call;

/**
 * 用户的圈子设置远程数据来源类
 * Created by JustKiddingBaby on 2016/10/30.
 */

public class CircleSettingUserRemoteDataSource implements CircleSettingUserDataSource {

    private static CircleSettingUserRemoteDataSource INSTANCE = null;

    private CircleSettingUserRemoteDataSource() {
    }

    public static CircleSettingUserRemoteDataSource newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CircleSettingUserRemoteDataSource();
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
    public void updateCircleInfo(
            @NonNull String Authorization, @NonNull int id, @NonNull String column,
            @NonNull String value, @NonNull int user_id,
            @NonNull ApiCallback<ApiResponse<CircleActionEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        CircleInfoApi circleInfoApi = factory.createApi(CircleInfoApi.class);
        Call<ApiResponse<CircleActionEntity>> call;
        call = circleInfoApi.updateCircleInfo(Authorization, id, column, value, user_id);
        Type type = new TypeToken<ApiResponse<CircleActionEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<CircleActionEntity>>(apiCallback, call, type);
    }

    @Override
    public void updateCircleImage(
            @NonNull String Authorization, @NonNull int id,
            @NonNull MultipartBody.Part image, @NonNull int user_id,
            @NonNull ApiCallback<ApiResponse<CircleActionEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        CircleInfoApi circleInfoApi = factory.createApi(CircleInfoApi.class);
        Call<ApiResponse<CircleActionEntity>> call;
        call = circleInfoApi.updateCirclePicture(Authorization, id, image,
                Config.FLAG_CIRCLE, user_id);
        Type type = new TypeToken<ApiResponse<CircleActionEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<CircleActionEntity>>(apiCallback, call, type);
    }

    @Override
    public void setInCommonUseCircleOrCancel(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        OperationApi operationApi = factory.createApi(OperationApi.class);
        Call<ApiResponse<OperationActionEntity>> call;
        call = operationApi.inCommonUse(Authorization, Config.ACTION_INCOMMONUSE, user_id, target_id);
        Type type = new TypeToken<ApiResponse<OperationActionEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<OperationActionEntity>>(apiCallback, call, type);
    }
}
