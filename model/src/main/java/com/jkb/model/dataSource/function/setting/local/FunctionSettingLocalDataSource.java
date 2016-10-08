package com.jkb.model.dataSource.function.setting.local;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.model.dataSource.function.setting.FunctionSettingDataSource;
import com.jkb.model.utils.SystemUtils;

import jkb.mrcampus.db.MrCampusDB;
import jkb.mrcampus.db.dao.DaoSession;
import jkb.mrcampus.db.entity.Status;

/**
 * 功能设置的数据仓库类
 * Created by JustKiddingBaby on 2016/10/8.
 */

public class FunctionSettingLocalDataSource implements FunctionSettingDataSource {


    private Context applicationContext;

    //数据库
    private MrCampusDB mrCampusDB;
    private DaoSession daoSession;

    private FunctionSettingLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;

        mrCampusDB = MrCampusDB.getInstance();
        daoSession = mrCampusDB.getDaoSession();
    }

    private static FunctionSettingLocalDataSource INSTANCE = null;

    public static FunctionSettingLocalDataSource getInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new FunctionSettingLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void saveStatusToDb(Status status) {
        daoSession.insertOrReplace(status);
    }

    @Override
    public String getSystemCurrentVision() {
        try {
            String currentVersion = SystemUtils.getCurrentVersion(applicationContext);
            return currentVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
