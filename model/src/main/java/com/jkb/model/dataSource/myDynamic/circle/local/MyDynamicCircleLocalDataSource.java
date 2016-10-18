package com.jkb.model.dataSource.myDynamic.circle.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.dynamic.DynamicArticleListEntity;
import com.jkb.api.entity.dynamic.DynamicCircleListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.user.UserActionCircleEntity;
import com.jkb.model.dataSource.myDynamic.circle.MyDynamicCircleDataSource;

/**
 * 我的普通数据的数据仓库类
 * Created by JustKiddingBaby on 2016/10/14.
 */

public class MyDynamicCircleLocalDataSource implements MyDynamicCircleDataSource {

    private Context applicationContext;

    private MyDynamicCircleLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static MyDynamicCircleLocalDataSource INSTANCE = null;

    public static MyDynamicCircleLocalDataSource getInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new MyDynamicCircleLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getMyDynamicCircle(
            String Authorization, @NonNull int user_id, @NonNull int circle_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicCircleListEntity>> apiCallback) {

    }

    @Override
    public void deleteDynamic(
            @NonNull String Authorization, @NonNull int dynamic_id, @NonNull int operator_id,
            @NonNull ApiCallback<ApiResponse<DynamicActionEntity>> apiCallback) {
    }

    @Override
    public void favorite(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
    }

    @Override
    public void subscribeCircle(
            @NonNull int user_id, int visitor_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<UserActionCircleEntity>> apiCallback) {
    }
}
