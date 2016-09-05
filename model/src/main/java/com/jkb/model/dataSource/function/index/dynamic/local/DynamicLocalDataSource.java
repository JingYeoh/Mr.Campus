package com.jkb.model.dataSource.function.index.dynamic.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicListEntity;
import com.jkb.model.dataSource.function.index.dynamic.DynamicDataSource;

/**
 * 首页——动态：本地数据来源类
 * Created by JustKiddingBaby on 2016/9/3.
 */

public class DynamicLocalDataSource implements DynamicDataSource {

    private Context applicationContext;

    private DynamicLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static DynamicLocalDataSource INSTANCE = null;

    public static DynamicLocalDataSource getInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new DynamicLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getAllDynamic(
            @NonNull String Authorization, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicListEntity>> apiCallback) {

    }
}
