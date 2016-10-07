package com.jkb.model.dataSource.circle.circleIndex.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleInfoEntity;
import com.jkb.api.entity.circle.DynamicInCircleListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.model.dataSource.circle.circleIndex.CircleIndexDataSource;
import com.jkb.model.intfc.BitmapLoadedCallback;

/**
 * 圈子首页的本地数据来源类
 * Created by JustKiddingBaby on 2016/8/29.
 */

public class CircleIndexLocalDataSource implements CircleIndexDataSource {

    private Context applicationContext;
    private static CircleIndexLocalDataSource INSTANCE = null;

    private CircleIndexLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static CircleIndexLocalDataSource getInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new CircleIndexLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getCircleInfo(
            @NonNull int userId, @NonNull int id,
            @NonNull ApiCallback<ApiResponse<CircleInfoEntity>> apiCallback) {

    }

    @Override
    public void loadBitmapByUrl(@NonNull String url, @NonNull BitmapLoadedCallback callback) {

    }

    @Override
    public void circleSubscribeOrNot(
            @NonNull int user_id, @NonNull int target_id, @NonNull String Authorization,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {

    }

    @Override
    public void getAllDynamicInCircle(
            String Authorization, @NonNull int circleId, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicInCircleListEntity>> apiCallback) {

    }

    @Override
    public void favorite(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {

    }

}
