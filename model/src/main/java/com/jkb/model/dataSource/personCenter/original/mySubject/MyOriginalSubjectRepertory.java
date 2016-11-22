package com.jkb.model.dataSource.personCenter.original.mySubject;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.subject.SpecialListEntity;
import com.jkb.api.entity.subject.SubjectActionEntity;

/**
 * 我的专题数据仓库类
 * Created by JustKiddingBaby on 2016/11/22.
 */

public class MyOriginalSubjectRepertory implements MyOriginalSubjectDataSource {

    private MyOriginalSubjectDataSource localDataSource;
    private MyOriginalSubjectDataSource remoteDataSource;

    private MyOriginalSubjectRepertory(
            MyOriginalSubjectDataSource localDataSource,
            MyOriginalSubjectDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    private static MyOriginalSubjectRepertory INSTANCE = null;

    public static MyOriginalSubjectRepertory newInstance(
            MyOriginalSubjectDataSource localDataSource,
            MyOriginalSubjectDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new MyOriginalSubjectRepertory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getMySubject(
            String Authorization, int page, @NonNull String type,
            @NonNull ApiCallback<ApiResponse<SpecialListEntity>> apiCallback) {
        remoteDataSource.getMySubject(Authorization, page, type, apiCallback);
    }

    @Override
    public void deleteDynamic(
            @NonNull String Authorization, int dynamic_id, int operator_id,
            @NonNull ApiCallback<ApiResponse<DynamicActionEntity>> apiCallback) {
        remoteDataSource.deleteDynamic(Authorization, dynamic_id, operator_id, apiCallback);
    }

    @Override
    public void favorite(
            @NonNull String Authorization, int user_id, int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
        remoteDataSource.favorite(Authorization, user_id, target_id, apiCallback);
    }

    @Override
    public void changeSubjectMarkStatus(
            @NonNull String Authorization, int dynamicId,
            @NonNull ApiCallback<ApiResponse<SubjectActionEntity>> apiCallback) {
        remoteDataSource.changeSubjectMarkStatus(Authorization, dynamicId, apiCallback);
    }
}
