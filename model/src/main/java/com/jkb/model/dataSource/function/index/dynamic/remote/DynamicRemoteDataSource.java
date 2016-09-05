package com.jkb.model.dataSource.function.index.dynamic.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.net.dynamic.DynamicApi;
import com.jkb.model.dataSource.function.index.dynamic.DynamicDataSource;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 首页——动态：远程数据来源类
 * Created by JustKiddingBaby on 2016/9/3.
 */

public class DynamicRemoteDataSource implements DynamicDataSource {


    private DynamicRemoteDataSource() {
    }

    private static DynamicRemoteDataSource INSTANCE = null;

    public static DynamicRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DynamicRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getAllDynamic(
            @NonNull String Authorization, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicListEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        DynamicApi dynamicApi = factory.createApi(DynamicApi.class);
        Call<ApiResponse<DynamicListEntity>> call;
        call = dynamicApi.getAllDynamic(Authorization, page);
        Type type = new TypeToken<ApiResponse<DynamicListEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<DynamicListEntity>>(apiCallback, call, type);
    }
}
