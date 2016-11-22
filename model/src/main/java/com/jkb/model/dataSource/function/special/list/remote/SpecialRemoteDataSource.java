package com.jkb.model.dataSource.function.special.list.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.subject.SpecialListEntity;
import com.jkb.api.entity.subject.SubjectActionEntity;
import com.jkb.api.net.operation.OperationApi;
import com.jkb.api.net.subject.SpecialApi;
import com.jkb.model.dataSource.function.special.list.SpecialDataSource;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 专题的远程数据来源类
 * Created by JustKiddingBaby on 2016/11/19.
 */

public class SpecialRemoteDataSource implements SpecialDataSource {

    private SpecialRemoteDataSource() {
    }

    private static SpecialRemoteDataSource INSTANCE = null;

    public static SpecialRemoteDataSource newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SpecialRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getAllSpecial(String authorization, @NonNull String subject, int schoolId, int page,
                              @NonNull ApiCallback<ApiResponse<SpecialListEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        SpecialApi specialApi = factory.createApi(SpecialApi.class);
        Call<ApiResponse<SpecialListEntity>> call
                = specialApi.getSpecialList(authorization, subject, schoolId, page);
        Type type = new TypeToken<ApiResponse<SpecialListEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<SpecialListEntity>>(apiCallback, call, type);
    }

    @Override
    public void changeSpecialMarkStatus(
            @NonNull String authorization, int subjectId,
            @NonNull ApiCallback<ApiResponse<SubjectActionEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        SpecialApi specialApi = factory.createApi(SpecialApi.class);
        Call<ApiResponse<SubjectActionEntity>> call
                = specialApi.changeSpecialMarkStatus(authorization, subjectId);
        Type type = new TypeToken<ApiResponse<SubjectActionEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<SubjectActionEntity>>(apiCallback, call, type);
    }

    @Override
    public void favorite(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        OperationApi operationApi = factory.createApi(OperationApi.class);
        Call<ApiResponse<OperationActionEntity>> call;
        call = operationApi.like(Authorization, Config.ACTION_FAVORITE, user_id, target_id);
        Type type = new TypeToken<ApiResponse<OperationActionEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<OperationActionEntity>>(apiCallback, call, type);
    }
}
