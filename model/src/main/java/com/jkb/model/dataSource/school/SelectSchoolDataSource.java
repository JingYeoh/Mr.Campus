package com.jkb.model.dataSource.school;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.school.SchoolListEntity;

import jkb.mrcampus.db.entity.Schools;
import jkb.mrcampus.db.entity.Status;

/**
 * 选择学校的数据来源接口
 * Created by JustKiddingBaby on 2016/9/29.
 */

public interface SelectSchoolDataSource {

    /**
     * 得到所有学校数据
     *
     * @param page        分页
     * @param apiCallback 回调
     */
    void getAllSchool(@NonNull int page, ApiCallback<ApiResponse<SchoolListEntity>> apiCallback);

    /**
     * 存储学校的数据
     *
     * @param schools 学校
     */
    void saveSchoolToDb(Schools schools);

    /**
     * 存储状态表
     *
     * @param status 状态表的对象
     */
    void saveStatusToDb(Status status);

    /**
     * 得到当前的版本好
     */
    String getCurrentVersion();
}
