package com.jkb.model.dataSource.personCenter.original.mySubject.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.subject.SpecialListEntity;
import com.jkb.api.entity.subject.SubjectActionEntity;
import com.jkb.api.net.dynamic.DynamicApi;
import com.jkb.api.net.operation.OperationApi;
import com.jkb.api.net.subject.SpecialApi;
import com.jkb.model.dataSource.personCenter.original.mySubject.MyOriginalSubjectDataSource;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 我的专题数据仓库类
 * Created by JustKiddingBaby on 2016/11/22.
 */

public class MyOriginalSubjectRemoteDataSource implements MyOriginalSubjectDataSource {


    private MyOriginalSubjectRemoteDataSource() {
    }

    private static MyOriginalSubjectRemoteDataSource INSTANCE = null;

    public static MyOriginalSubjectRemoteDataSource newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MyOriginalSubjectRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getMySubject(
            String Authorization, int page, @NonNull String type,
            @NonNull ApiCallback<ApiResponse<SpecialListEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        DynamicApi dynamicApi = factory.createApi(DynamicApi.class);
        Call<ApiResponse<SpecialListEntity>> call;
        call = dynamicApi.getMySubjectDynamic(Authorization, type, page);
        Type mType = new TypeToken<ApiResponse<SpecialListEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<SpecialListEntity>>(apiCallback, call, mType);
    }

    @Override
    public void deleteDynamic(
            @NonNull String Authorization, int dynamic_id, int operator_id,
            @NonNull ApiCallback<ApiResponse<DynamicActionEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        DynamicApi dynamicApi = factory.createApi(DynamicApi.class);
        Call<ApiResponse<DynamicActionEntity>> call;
        call = dynamicApi.deleteDynamic(Authorization, dynamic_id, operator_id);
        Type type = new TypeToken<ApiResponse<DynamicActionEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<DynamicActionEntity>>(apiCallback, call, type);
    }

    @Override
    public void favorite(
            @NonNull String Authorization, int user_id, int target_id,
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

    @Override
    public void changeSubjectMarkStatus(
            @NonNull String Authorization, int dynamicId,
            @NonNull ApiCallback<ApiResponse<SubjectActionEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        SpecialApi specialApi = factory.createApi(SpecialApi.class);
        Call<ApiResponse<SubjectActionEntity>> call;
        call = specialApi.changeSpecialMarkStatus(Authorization, dynamicId);
        Type type = new TypeToken<ApiResponse<SubjectActionEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<SubjectActionEntity>>(apiCallback, call, type);
    }
}
