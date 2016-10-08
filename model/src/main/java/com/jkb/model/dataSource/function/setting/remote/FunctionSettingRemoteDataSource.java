package com.jkb.model.dataSource.function.setting.remote;

import com.jkb.model.dataSource.function.setting.FunctionSettingDataSource;

import jkb.mrcampus.db.entity.Status;

/**
 * 功能设置的数据仓库类
 * Created by JustKiddingBaby on 2016/10/8.
 */

public class FunctionSettingRemoteDataSource implements FunctionSettingDataSource {


    private FunctionSettingRemoteDataSource() {
    }

    private static FunctionSettingRemoteDataSource INSTANCE = null;

    public static FunctionSettingRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FunctionSettingRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void saveStatusToDb(Status status) {
    }

    @Override
    public String getSystemCurrentVision() {
        return null;
    }
}
