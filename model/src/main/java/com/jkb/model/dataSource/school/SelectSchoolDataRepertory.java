package com.jkb.model.dataSource.school;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.school.SchoolListEntity;

import jkb.mrcampus.db.entity.Schools;
import jkb.mrcampus.db.entity.Status;

/**
 * 选择学校的数据来源仓库类
 * Created by JustKiddingBaby on 2016/9/29.
 */

public class SelectSchoolDataRepertory implements SelectSchoolDataSource {

    private SelectSchoolDataSource localDataSource;
    private SelectSchoolDataSource remoteDataSource;

    private SelectSchoolDataRepertory(SelectSchoolDataSource localDataSource,
                                      SelectSchoolDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static SelectSchoolDataRepertory INSTANCE = null;

    public static SelectSchoolDataRepertory getInstance(
            @NonNull SelectSchoolDataSource localDataSource,
            @NonNull SelectSchoolDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new SelectSchoolDataRepertory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getAllSchool(@NonNull int page, ApiCallback<ApiResponse<SchoolListEntity>> apiCallback) {
        remoteDataSource.getAllSchool(page, apiCallback);
    }

    @Override
    public void saveSchoolToDb(Schools schools) {
        localDataSource.saveSchoolToDb(schools);
    }

    @Override
    public void saveStatusToDb(Status status) {
        localDataSource.saveStatusToDb(status);
    }

    @Override
    public String getCurrentVersion() {
        return localDataSource.getCurrentVersion();
    }
}
