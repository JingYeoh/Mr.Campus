package com.jkb.model.first.firstlogic;

import android.support.annotation.NonNull;

import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * FirstActivity的数据仓库类
 * Created by JustKiddingBaby on 2016/7/22.
 */
public class FirstDataResponsitory implements FirstDataSource {


    private static FirstDataResponsitory INSTANCE = null;

    private FirstDataSource firstLocalDataSource;

    //缓存
    private FirstData cacheData;
    //是否过期
    private boolean overdue = false;

    private FirstDataResponsitory(@NonNull FirstDataSource firstLocalDataSource) {
        this.firstLocalDataSource = checkNotNull(firstLocalDataSource);
    }

    /**
     * 获取单利实例对象
     *
     * @param firstLocalDataSource
     * @return
     */
    public static FirstDataResponsitory getInstance(@NonNull FirstDataSource firstLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new FirstDataResponsitory(firstLocalDataSource);
        }
        return INSTANCE;
    }


    @Override
    public void getStatusData(StatusDataCallback callback) {
        firstLocalDataSource.getStatusData(callback);
    }

    @Override
    public void cacheStatus(String version, boolean isLogined, int userId, Date date) {
        firstLocalDataSource.cacheStatus(version, isLogined, userId, date);
    }


    @Override
    public String getCurrentVersion() {
        return firstLocalDataSource.getCurrentVersion();
    }
}
