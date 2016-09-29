package com.jkb.model.dataSource.first.firstlogic;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.jkb.model.base.BaseDataSource;

import java.util.Date;

import jkb.mrcampus.db.entity.Schools;
import jkb.mrcampus.db.entity.Status;
import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;

/**
 * 第一次进入APP的数据处理接口
 * Created by JustKiddingBaby on 2016/7/22.
 */
public interface FirstDataSource extends BaseDataSource {


    interface StatusDataCallback {
        /**
         * 数据加载成功
         */
        void onStatusDataLoaded(Status status);

        /**
         * 数据加载失败
         */
        void onDataNotAvailable();
    }

    interface UsersDataCallback {

        /**
         * 数据加载成功
         */
        void onUserDataLoaded(Users users);

        /**
         * 数据加载失败
         */
        void onDataNotAvailable();
    }

    interface UserAuthsDataCallback {

        /**
         * 数据加载成功
         */
        void onUserDataLoaded(UserAuths userAuths);

        /**
         * 数据加载失败
         */
        void onDataNotAvailable();
    }

    interface BitmapDataCallback {

        /**
         * 数据加载成功
         */
        void onBitmapDataLoaded(Bitmap bitmap);

        /**
         * 数据加载失败
         */
        void onDataNotAvailable(String url);
    }

    /**
     * 获取到缓存的系统状态数据
     */
    void getStatusData(StatusDataCallback callback);

    /**
     * 缓存系统状态
     */
    void cacheStatus(String version, boolean isLogined, boolean isSelectedSchool, int schoolId,
                     int userId, Date date);

    /**
     * 得到个人的数据
     */
    void getUsersData(@NonNull int userId, @NonNull UsersDataCallback callback);

    /**
     * 得到个人的auth数据
     */
    void getUserAuthData(@NonNull int userId, @NonNull UserAuthsDataCallback callback);

    /**
     * 得到系统的当前版本号
     */
    String getCurrentVersion();

    /**
     * 通过网络加载头像
     */
    void loadHeadImgByUrl(String url, BitmapDataCallback callback);

    /**
     * 通过本地地址加载头像
     */
    void loadHeadImgByLocalPath(String path, BitmapDataCallback callback);

    /**
     * 从数据库中得到学校的对象
     *
     * @param schoolId 学校id
     */
    Schools getSchoolFromDb(int schoolId);
}
