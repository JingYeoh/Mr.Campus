package com.jkb.model.dataSource.function.special.list;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.subject.SpecialListEntity;
import com.jkb.api.entity.subject.SubjectActionEntity;

/**
 * 专题的数据仓库类
 * Created by JustKiddingBaby on 2016/11/19.
 */

public class SpecialRepertory implements SpecialDataSource {

    private SpecialDataSource localDataSource;
    private SpecialDataSource remoteDataSource;

    private SpecialRepertory(SpecialDataSource localDataSource,
                             SpecialDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static SpecialRepertory INSTANCE = null;

    public static SpecialRepertory newInstance(SpecialDataSource localDataSource,
                                               SpecialDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new SpecialRepertory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getAllSpecial(
            String authorization, @NonNull String subject, int schoolId, int page,
            @NonNull ApiCallback<ApiResponse<SpecialListEntity>> apiCallback) {
        remoteDataSource.getAllSpecial(authorization, subject, schoolId, page, apiCallback);
    }

    @Override
    public void changeSpecialMarkStatus(
            @NonNull String authorization, int subjectId,
            @NonNull ApiCallback<ApiResponse<SubjectActionEntity>> apiCallback) {
        remoteDataSource.changeSpecialMarkStatus(authorization, subjectId, apiCallback);
    }

    @Override
    public void favorite(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
        remoteDataSource.favorite(Authorization, user_id, target_id, apiCallback);
    }

    /**
     * 得到表白墙专题数据
     */
    public void getAllSpecialConfession(
            String authorization, int schoolId, int page,
            @NonNull ApiCallback<ApiResponse<SpecialListEntity>> apiCallback) {
        getAllSpecial(authorization, Config.SUBJECT_ACTION_VINDICATION, schoolId, page, apiCallback);
    }

    /**
     * 得到表白墙专题数据
     */
    public void getAllSpecialComplaint(
            String authorization, int schoolId, int page,
            @NonNull ApiCallback<ApiResponse<SpecialListEntity>> apiCallback) {
        getAllSpecial(authorization, Config.SUBJECT_ACTION_COMPLAINT, schoolId, page, apiCallback);
    }

    /**
     * 得到表白墙专题数据
     */
    public void getAllSpecialLost$Found(
            String authorization, int schoolId, int page,
            @NonNull ApiCallback<ApiResponse<SpecialListEntity>> apiCallback) {
        getAllSpecial(authorization, Config.SUBJECT_ACTION_LOSTANDFOUND, schoolId, page, apiCallback);
    }

    /**
     * 得到表白墙专题数据
     */
    public void getAllSpecialGrind(
            String authorization, int schoolId, int page,
            @NonNull ApiCallback<ApiResponse<SpecialListEntity>> apiCallback) {
        getAllSpecial(authorization, Config.SUBJECT_ACTION_GRIND, schoolId, page, apiCallback);
    }

    /**
     * 得到表白墙专题数据
     */
    public void getAllSpecialPartner(
            String authorization, int schoolId, int page,
            @NonNull ApiCallback<ApiResponse<SpecialListEntity>> apiCallback) {
        getAllSpecial(authorization, Config.SUBJECT_ACTION_PARTNER, schoolId, page, apiCallback);
    }

    /**
     * 得到表白墙专题数据
     */
    public void getAllSpecialFleaMarket(
            String authorization, int schoolId, int page,
            @NonNull ApiCallback<ApiResponse<SpecialListEntity>> apiCallback) {
        getAllSpecial(authorization, Config.SUBJECT_ACTION_FLEAMARKET, schoolId, page, apiCallback);
    }
}
