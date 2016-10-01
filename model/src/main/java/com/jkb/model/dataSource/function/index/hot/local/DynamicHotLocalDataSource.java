package com.jkb.model.dataSource.function.index.hot.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicPopularListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.model.dataSource.function.index.hot.DynamicHotDataSource;

/**
 * 首页——动态：本地数据来源类
 * Created by JustKiddingBaby on 2016/9/3.
 */

public class DynamicHotLocalDataSource implements DynamicHotDataSource {

    private Context applicationContext;

    private DynamicHotLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static DynamicHotLocalDataSource INSTANCE = null;

    public static DynamicHotLocalDataSource getInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new DynamicHotLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getAllHotDynamic(
            @NonNull String Authorization, @NonNull int schoolId, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicPopularListEntity>> apiCallback) {

    }

    @Override
    public void favorite(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {

    }
}
