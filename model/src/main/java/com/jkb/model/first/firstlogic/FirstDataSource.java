package com.jkb.model.first.firstlogic;

import android.support.annotation.NonNull;

import com.jkb.model.base.BaseDataSource;

import java.util.Date;

import jkb.mrcampus.db.entity.Status;

/**
 * 第一次进入APP的数据处理接口
 * Created by JustKiddingBaby on 2016/7/22.
 */
public interface FirstDataSource extends BaseDataSource {


    interface StatusDataCallback {
        /**
         * 数据加载成功
         *
         * @param status
         */
        void onStatusDataLoaded(Status status);

        /**
         * 数据加载失败
         */
        void onDataNotAvailable();
    }

    /**
     * 获取到缓存的系统状态数据
     *
     * @param callback
     */
    void getStatusData(StatusDataCallback callback);

    /**
     * 缓存系统状态
     */
    void cacheStatus(String version,boolean isLogined,int userId, Date date);

    /**
     * 得到系统的当前版本号
     */
    String getCurrentVersion();
}
