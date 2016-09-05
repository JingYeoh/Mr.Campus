package com.jkb.model.dataSource.function.index.dynamic;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicListEntity;

/**
 * 首页——动态：数据来源仓库类
 * Created by JustKiddingBaby on 2016/9/3.
 */

public class DynamicDataResponsitory implements DynamicDataSource {

    private DynamicDataSource localDataSource;
    private DynamicDataSource remoteDataSource;

    private DynamicDataResponsitory(
            DynamicDataSource localDataSource, DynamicDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static DynamicDataResponsitory INSTANCE = null;

    public static DynamicDataResponsitory getInstance(
            @NonNull DynamicDataSource localDataSource,
            @NonNull DynamicDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new DynamicDataResponsitory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getAllDynamic(
            @NonNull String Authorization, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicListEntity>> apiCallback) {
        remoteDataSource.getAllDynamic(Authorization, page, apiCallback);
    }
}
