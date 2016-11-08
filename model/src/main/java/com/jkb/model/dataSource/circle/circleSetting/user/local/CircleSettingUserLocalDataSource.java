package com.jkb.model.dataSource.circle.circleSetting.user.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleActionEntity;
import com.jkb.api.entity.circle.CircleInfoEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.model.dataSource.circle.circleSetting.user.CircleSettingUserDataSource;

import okhttp3.MultipartBody;

/**
 * 用户的圈子设置远程数据来源类
 * Created by JustKiddingBaby on 2016/10/30.
 */

public class CircleSettingUserLocalDataSource implements CircleSettingUserDataSource {

    private Context applicationContext;

    private static CircleSettingUserLocalDataSource INSTANCE = null;

    private CircleSettingUserLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static CircleSettingUserLocalDataSource newInstance(Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new CircleSettingUserLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getCircleInfo(
            @NonNull int userId, @NonNull int id,
            @NonNull ApiCallback<ApiResponse<CircleInfoEntity>> apiCallback) {

    }

    @Override
    public void updateCircleInfo(
            @NonNull String Authorization, @NonNull int id, @NonNull String column,
            @NonNull String value, @NonNull int user_id,
            @NonNull ApiCallback<ApiResponse<CircleActionEntity>> apiCallback) {

    }

    @Override
    public void updateCircleImage(
            @NonNull String Authorization, @NonNull int id,
            @NonNull MultipartBody.Part image, @NonNull int user_id,
            @NonNull ApiCallback<ApiResponse<CircleActionEntity>> apiCallback) {

    }

    @Override
    public void setInCommonUseCircleOrCancel(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
    }
}
