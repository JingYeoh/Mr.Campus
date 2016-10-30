package com.jkb.model.dataSource.circle.circleSetting.user;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleActionEntity;
import com.jkb.api.entity.circle.CircleInfoEntity;

import okhttp3.MultipartBody;

/**
 * 用户的圈子设置数据仓库类
 * Created by JustKiddingBaby on 2016/10/30.
 */

public class CircleSettingUserDataRepertory implements CircleSettingUserDataSource {

    private CircleSettingUserDataSource localDataSource;
    private CircleSettingUserDataSource remoteDataSource;

    private CircleSettingUserDataRepertory(
            CircleSettingUserDataSource localDataSource, CircleSettingUserDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static CircleSettingUserDataRepertory INSTANCE;

    public static CircleSettingUserDataRepertory newInstance(
            CircleSettingUserDataSource localDataSource,
            CircleSettingUserDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new CircleSettingUserDataRepertory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getCircleInfo(
            @NonNull int userId, @NonNull int id,
            @NonNull ApiCallback<ApiResponse<CircleInfoEntity>> apiCallback) {
        remoteDataSource.getCircleInfo(userId, id, apiCallback);
    }

    @Override
    public void updateCircleInfo(
            @NonNull String Authorization, @NonNull int id, @NonNull String column,
            @NonNull String value, @NonNull int user_id,
            @NonNull ApiCallback<ApiResponse<CircleActionEntity>> apiCallback) {
        remoteDataSource.updateCircleInfo(Authorization, id, column, value, user_id, apiCallback);
    }

    @Override
    public void updateCircleImage(
            @NonNull String Authorization, @NonNull int id, @NonNull MultipartBody.Part image,
            @NonNull int user_id,
            @NonNull ApiCallback<ApiResponse<CircleActionEntity>> apiCallback) {
        remoteDataSource.updateCircleImage(Authorization, id, image, user_id, apiCallback);
    }
}
