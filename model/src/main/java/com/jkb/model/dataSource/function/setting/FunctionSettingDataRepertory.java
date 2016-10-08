package com.jkb.model.dataSource.function.setting;

import android.support.annotation.NonNull;

import jkb.mrcampus.db.entity.Status;

/**
 * 功能设置的数据仓库类
 * Created by JustKiddingBaby on 2016/10/8.
 */

public class FunctionSettingDataRepertory implements FunctionSettingDataSource {

    private FunctionSettingDataSource localDataSource;
    private FunctionSettingDataSource remoteDataSource;

    private FunctionSettingDataRepertory(
            FunctionSettingDataSource localDataSource, FunctionSettingDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static FunctionSettingDataRepertory INSTANCE = null;

    public static FunctionSettingDataRepertory getInstance(
            @NonNull FunctionSettingDataSource localDataSource,
            @NonNull FunctionSettingDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new FunctionSettingDataRepertory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void saveStatusToDb(Status status) {
        localDataSource.saveStatusToDb(status);
    }

    @Override
    public String getSystemCurrentVision() {
        return localDataSource.getSystemCurrentVision();
    }
}
