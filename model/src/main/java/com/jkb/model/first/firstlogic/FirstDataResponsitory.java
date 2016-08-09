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
    private FirstDataSource remoteDataSource;

    //缓存
    private FirstData cacheData;
    //是否过期
    private boolean overdue = false;

    private FirstDataResponsitory(@NonNull FirstDataSource firstLocalDataSource,
                                  @NonNull FirstDataSource remoteDataSource) {
        this.firstLocalDataSource = checkNotNull(firstLocalDataSource);
        this.remoteDataSource = remoteDataSource;
    }

    /**
     * 获取单利实例对象
     *
     * @param firstLocalDataSource
     * @return
     */
    public static FirstDataResponsitory getInstance(@NonNull FirstDataSource firstLocalDataSource,
                                                    @NonNull FirstDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new FirstDataResponsitory(firstLocalDataSource, remoteDataSource);
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
    public void getUsersData(@NonNull int userId, UsersDataCallback callback) {
        firstLocalDataSource.getUsersData(userId, callback);
    }

    @Override
    public void getUserAuthData(@NonNull int userId, UserAuthsDataCallback callback) {
        firstLocalDataSource.getUserAuthData(userId, callback);
    }


    @Override
    public String getCurrentVersion() {
        return firstLocalDataSource.getCurrentVersion();
    }

    @Override
    public void loadHeadImgByUrl(String url, BitmapDataCallback callback) {
        remoteDataSource.loadHeadImgByUrl(url, callback);
    }

    @Override
    public void loadHeadImgByLocalPath(String path, BitmapDataCallback callback) {
        firstLocalDataSource.loadHeadImgByLocalPath(path, callback);
    }
}
