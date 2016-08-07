package com.jkb.model.first.firstlogic.local;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.model.first.firstlogic.FirstData;
import com.jkb.model.first.firstlogic.FirstDataSource;
import com.jkb.model.utils.Config;
import com.jkb.model.utils.SharePreferenceUtils;
import com.jkb.model.utils.SystemUtils;

import java.util.Date;

import jkb.mrcampus.db.MrCampusDB;
import jkb.mrcampus.db.dao.DaoMaster;
import jkb.mrcampus.db.dao.DaoSession;
import jkb.mrcampus.db.dao.StatusDao;
import jkb.mrcampus.db.entity.Status;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * FirstActivity控制层要用到的数据的本地来源类
 * Created by JustKiddingBaby on 2016/7/22.
 */
public class FirstLocalDataSource implements FirstDataSource {

    private static FirstLocalDataSource INSTANCE;
    private String TAG = getClass().getSimpleName();

    private Context context;

    //数据库相关
    private MrCampusDB mrCampusDB;
    private DaoSession daoSession;


    /**
     * 获取WelcomeRemoteDataSource的单例类对象
     *
     * @return
     */
    public static FirstLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new FirstLocalDataSource(context);
        }
        return INSTANCE;
    }

    private FirstLocalDataSource(Context context) {
        this.context = checkNotNull(context);
        //从数据库中读取数据
        mrCampusDB = MrCampusDB.getInstance();
        daoSession = mrCampusDB.getDaoSession();
    }


    @Override
    public void getStatusData(StatusDataCallback callback) {
        //从数据库中读取数据
        MrCampusDB mrCampusDB = MrCampusDB.getInstance();
        DaoSession daoSession = mrCampusDB.getDaoSession();
        StatusDao statusDao = daoSession.getStatusDao();
        Status status = statusDao.load(statusDao.count());
        if (callback != null) {
            if (status != null) {
                Log.d(TAG, "status.version=" + status.getVersion());
                Log.d(TAG, "status.isLogin=" + status.getFlag_login());
                Log.d(TAG, "status.userId=" + status.getUser_id());
                Log.d(TAG, "status.getCreateData=" + status.getCreated_at().toString());
                callback.onStatusDataLoaded(status);
            } else {
                callback.onDataNotAvailable();
            }
        }
    }

    @Override
    public void cacheStatus(String version, boolean isLogined, int userId, Date date) {
        Status status = new Status();
        status.setVersion(version);
        status.setFlag_login(isLogined);
        status.setUser_id(userId);
        status.setCreated_at(date);
        daoSession.insertOrReplace(status);
    }

    /**
     * 得到当前的版本
     *
     * @return
     */
    @Override
    public String getCurrentVersion() {
        try {
            String currentVersion = SystemUtils.getCurrentVersion(context);
            Log.d(TAG, "currentVersion=" + currentVersion);
            return currentVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}