package com.jkb.model.dataSource.circle.attentionUserList;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleActionEntity;
import com.jkb.api.entity.circle.CircleAttentionUserListEntity;

/**
 * 用户成员的数据仓库类
 * Created by JustKiddingBaby on 2016/11/3.
 */

public class CircleAttentionUserListRepertory implements CircleAttentionUserListDataSource {

    private CircleAttentionUserListDataSource localDataSource;
    private CircleAttentionUserListDataSource remoteDataSource;

    private CircleAttentionUserListRepertory(
            CircleAttentionUserListDataSource localDataSource,
            CircleAttentionUserListDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static CircleAttentionUserListRepertory INSTANCE = null;

    public static CircleAttentionUserListRepertory newInstance(
            CircleAttentionUserListDataSource localDataSource,
            CircleAttentionUserListDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new CircleAttentionUserListRepertory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getUsersInCircle(
            String Authorization, @NonNull int circleId, @NonNull int page,
            ApiCallback<ApiResponse<CircleAttentionUserListEntity>> apiCallback) {
        remoteDataSource.getUsersInCircle(Authorization, circleId, page, apiCallback);
    }

    @Override
    public void getUsersInCircleInBlackList(
            String Authorization, @NonNull int circleId, @NonNull int page,
            ApiCallback<ApiResponse<CircleAttentionUserListEntity>> apiCallback) {
        remoteDataSource.getUsersInCircleInBlackList(Authorization, circleId, page, apiCallback);
    }

    @Override
    public void putUserInBlackList(
            @NonNull String Authorization, @NonNull int id, @NonNull int user_id,
            @NonNull int operator_id,
            @NonNull ApiCallback<ApiResponse<CircleActionEntity>> apiCallback) {
        remoteDataSource.putUserInBlackList(Authorization, id, user_id, operator_id, apiCallback);
    }

    @Override
    public void pullUserOutBlackList(
            @NonNull String Authorization, @NonNull int id, @NonNull int user_id,
            @NonNull int operator_id,
            @NonNull ApiCallback<ApiResponse<CircleActionEntity>> apiCallback) {
        remoteDataSource.pullUserOutBlackList(Authorization, id, user_id, operator_id, apiCallback);
    }
}
