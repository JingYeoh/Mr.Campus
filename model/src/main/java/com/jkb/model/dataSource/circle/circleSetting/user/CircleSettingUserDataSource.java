package com.jkb.model.dataSource.circle.circleSetting.user;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleActionEntity;
import com.jkb.api.entity.circle.CircleInfoEntity;

import okhttp3.MultipartBody;

/**
 * 用户的圈子设置的数据来源接口
 * Created by JustKiddingBaby on 2016/10/30.
 */

public interface CircleSettingUserDataSource {

    /**
     * 得到圈子数据
     *
     * @param userId      用户id
     * @param id          圈子id
     * @param apiCallback 回调
     */
    void getCircleInfo(
            @NonNull int userId, @NonNull int id,
            @NonNull ApiCallback<ApiResponse<CircleInfoEntity>> apiCallback);

    /**
     * 更新圈子数据
     *
     * @param Authorization 头
     * @param id            圈子id
     * @param column        圈子所修改信息的类别
     *                      允许值: "name", "introduction", "school_id"
     * @param value         类别对应的值.
     * @param user_id       操作者的用户id
     * @param apiCallback   回调
     */
    void updateCircleInfo(
            @NonNull String Authorization, @NonNull int id, @NonNull String column,
            @NonNull String value, @NonNull int user_id,
            @NonNull ApiCallback<ApiResponse<CircleActionEntity>> apiCallback);

    /**
     * 更新圈子图片
     *
     * @param Authorization 头
     * @param id            圈子id
     * @param image         图片文件
     * @param user_id       用户id
     * @param apiCallback   回调
     */
    void updateCircleImage(
            @NonNull String Authorization, @NonNull int id,
            @NonNull MultipartBody.Part image, @NonNull int user_id,
            @NonNull ApiCallback<ApiResponse<CircleActionEntity>> apiCallback);
}
