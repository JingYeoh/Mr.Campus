package com.jkb.model.dataSource.circle.attentionUserList.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleActionEntity;
import com.jkb.api.entity.circle.CircleAttentionUserListEntity;
import com.jkb.model.dataSource.circle.attentionUserList.CircleAttentionUserListDataSource;

/**
 * 圈子成员的远程数据来源类
 * Created by JustKiddingBaby on 2016/11/3.
 */

public class CircleAttentionUserListLocalDataSource implements CircleAttentionUserListDataSource {

    private static CircleAttentionUserListLocalDataSource INSTANCE;

    private Context applicationContext;

    private CircleAttentionUserListLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static CircleAttentionUserListLocalDataSource newInstance(Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new CircleAttentionUserListLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getUsersInCircle(
            String Authorization, @NonNull int circleId, @NonNull int page,
            ApiCallback<ApiResponse<CircleAttentionUserListEntity>> apiCallback) {

    }

    @Override
    public void getUsersInCircleInBlackList(
            String Authorization, @NonNull int circleId, @NonNull int page,
            ApiCallback<ApiResponse<CircleAttentionUserListEntity>> apiCallback) {

    }

    @Override
    public void putUserInBlackList(
            @NonNull String Authorization, @NonNull int id, @NonNull int user_id,
            @NonNull int operator_id,
            @NonNull ApiCallback<ApiResponse<CircleActionEntity>> apiCallback) {

    }

    @Override
    public void pullUserOutBlackList(
            @NonNull String Authorization, @NonNull int id, @NonNull int user_id,
            @NonNull int operator_id,
            @NonNull ApiCallback<ApiResponse<CircleActionEntity>> apiCallback) {

    }
}
