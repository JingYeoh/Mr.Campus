package com.jkb.model.dataSource.myDynamic.circleSelector;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.user.UserActionCircleEntity;

/**
 * 圈子选择器的数据仓库类
 * Created by JustKiddingBaby on 2016/10/18.
 */

public class CircleSelectorRepertory implements CircleSelectorDataSource {


    private CircleSelectorDataSource localDataSource;
    private CircleSelectorDataSource remoteDataSource;

    private CircleSelectorRepertory(
            @NonNull CircleSelectorDataSource localDataSource,
            @NonNull CircleSelectorDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static CircleSelectorRepertory INSTANCE = null;

    public static CircleSelectorRepertory newInstance(
            @NonNull CircleSelectorDataSource localDataSource,
            @NonNull CircleSelectorDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new CircleSelectorRepertory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void subscribeCircle(
            @NonNull int user_id, int visitor_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<UserActionCircleEntity>> apiCallback) {
        remoteDataSource.subscribeCircle(user_id, visitor_id, page, apiCallback);
    }
}
