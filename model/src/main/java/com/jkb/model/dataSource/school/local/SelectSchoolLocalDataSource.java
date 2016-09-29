package com.jkb.model.dataSource.school.local;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.school.SchoolListEntity;
import com.jkb.model.dataSource.school.SelectSchoolDataSource;
import com.jkb.model.utils.SystemUtils;

import jkb.mrcampus.db.MrCampusDB;
import jkb.mrcampus.db.dao.DaoSession;
import jkb.mrcampus.db.entity.Schools;
import jkb.mrcampus.db.entity.Status;

/**
 * 选择学校的本地数据来源类
 * Created by JustKiddingBaby on 2016/9/29.
 */

public class SelectSchoolLocalDataSource implements SelectSchoolDataSource {

    private Context applicationContext;
    //数据库
    private MrCampusDB mrCampusDB;
    private DaoSession daoSession;

    private SelectSchoolLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
        mrCampusDB = MrCampusDB.getInstance();
        daoSession = mrCampusDB.getDaoSession();
    }

    private static SelectSchoolLocalDataSource INSTANCE = null;

    public static SelectSchoolLocalDataSource getInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new SelectSchoolLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getAllSchool(@NonNull int page, ApiCallback<ApiResponse<SchoolListEntity>> apiCallback) {

    }

    @Override
    public void saveSchoolToDb(Schools schools) {
        daoSession.insertOrReplace(schools);
    }

    @Override
    public void saveStatusToDb(Status status) {
        daoSession.insertOrReplace(status);
    }

    /**
     * 得到当前的版本
     */
    @Override
    public String getCurrentVersion() {
        try {
            String currentVersion = SystemUtils.getCurrentVersion(applicationContext);
            return currentVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
