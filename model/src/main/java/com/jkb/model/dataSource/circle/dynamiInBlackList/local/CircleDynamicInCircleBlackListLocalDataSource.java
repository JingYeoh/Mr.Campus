package com.jkb.model.dataSource.circle.dynamiInBlackList.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleActionEntity;
import com.jkb.api.entity.circle.CircleDynamicInBlackListEntity;
import com.jkb.model.dataSource.circle.dynamiInBlackList.CircleDynamicInCircleBlackListDataSource;

/**
 * 圈子动态黑名单的远程数据来源类
 * Created by JustKiddingBaby on 2016/11/5.
 */

public class CircleDynamicInCircleBlackListLocalDataSource
        implements CircleDynamicInCircleBlackListDataSource {

    private Context applicationContext;

    private CircleDynamicInCircleBlackListLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static CircleDynamicInCircleBlackListLocalDataSource INSTANCE = null;

    public static CircleDynamicInCircleBlackListLocalDataSource newInstance(
            Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new CircleDynamicInCircleBlackListLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getAllDynamicsInBlackList(
            @NonNull String Authorization, @NonNull int circle_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<CircleDynamicInBlackListEntity>> apiCallback) {

    }

    @Override
    public void pullDynamicOutBlackList(
            @NonNull String Authorization, @NonNull int dynamic_id, @NonNull int user_id,
            @NonNull ApiCallback<ApiResponse<CircleActionEntity>> apiCallback) {

    }
}
