package com.jkb.model.dataSource.function.setting;

import jkb.mrcampus.db.entity.Status;

/**
 * 功能设置的数据来源类
 * Created by JustKiddingBaby on 2016/10/8.
 */

public interface FunctionSettingDataSource {

    /**
     * 存储状态表到数据库中
     *
     * @param status 状态表的对象
     */
    void saveStatusToDb(Status status);

    /**
     * 得到系统数据库的版本号
     */
    String getSystemCurrentVision();
}
