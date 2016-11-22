package com.jkb.model.dataSource.personCenter.original.mySubject.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.subject.SpecialListEntity;
import com.jkb.api.entity.subject.SubjectActionEntity;
import com.jkb.model.dataSource.personCenter.original.mySubject.MyOriginalSubjectDataSource;

/**
 * 我的专题数据仓库类
 * Created by JustKiddingBaby on 2016/11/22.
 */

public class MyOriginalSubjectLocalDataSource implements MyOriginalSubjectDataSource {

    private Context applicationContext;

    private MyOriginalSubjectLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static MyOriginalSubjectLocalDataSource INSTANCE = null;

    public static MyOriginalSubjectLocalDataSource newInstance(
            Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new MyOriginalSubjectLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getMySubject(
            String Authorization, int page, @NonNull String type,
            @NonNull ApiCallback<ApiResponse<SpecialListEntity>> apiCallback) {
    }

    @Override
    public void deleteDynamic(
            @NonNull String Authorization, int dynamic_id, int operator_id,
            @NonNull ApiCallback<ApiResponse<DynamicActionEntity>> apiCallback) {
    }

    @Override
    public void favorite(
            @NonNull String Authorization, int user_id, int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
    }

    @Override
    public void changeSubjectMarkStatus(
            @NonNull String Authorization, int dynamicId,
            @NonNull ApiCallback<ApiResponse<SubjectActionEntity>> apiCallback) {
    }
}
