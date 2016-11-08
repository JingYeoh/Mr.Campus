package com.jkb.model.dataSource.circle.dynamiInBlackList;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleActionEntity;
import com.jkb.api.entity.circle.CircleDynamicInBlackListEntity;

/**
 * 圈子动态黑名单的数据仓库类
 * Created by JustKiddingBaby on 2016/11/5.
 */

public class CircleDynamicInCircleBlackListRepertory
        implements CircleDynamicInCircleBlackListDataSource {

    private CircleDynamicInCircleBlackListDataSource localDataSource;
    private CircleDynamicInCircleBlackListDataSource remoteDataSource;

    private CircleDynamicInCircleBlackListRepertory(
            CircleDynamicInCircleBlackListDataSource localDataSource,
            CircleDynamicInCircleBlackListDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static CircleDynamicInCircleBlackListRepertory INSTANCE = null;

    public static CircleDynamicInCircleBlackListRepertory newInstance(
            CircleDynamicInCircleBlackListDataSource localDataSource,
            CircleDynamicInCircleBlackListDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new CircleDynamicInCircleBlackListRepertory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getAllDynamicsInBlackList(
            @NonNull String Authorization, @NonNull int circle_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<CircleDynamicInBlackListEntity>> apiCallback) {
        remoteDataSource.getAllDynamicsInBlackList(Authorization, circle_id, page, apiCallback);
    }

    @Override
    public void pullDynamicOutBlackList(
            @NonNull String Authorization, @NonNull int dynamic_id, @NonNull int user_id,
            @NonNull ApiCallback<ApiResponse<CircleActionEntity>> apiCallback) {
        remoteDataSource.pullDynamicOutBlackList(Authorization, dynamic_id, user_id, apiCallback);
    }
}
