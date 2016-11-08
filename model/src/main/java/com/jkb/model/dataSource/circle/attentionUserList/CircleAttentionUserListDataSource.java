package com.jkb.model.dataSource.circle.attentionUserList;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleActionEntity;
import com.jkb.api.entity.circle.CircleAttentionUserListEntity;

/**
 * 圈子成员的数据来源类
 * Created by JustKiddingBaby on 2016/11/3.
 */

public interface CircleAttentionUserListDataSource {

    /**
     * 得到圈子成员的接口
     *
     * @param Authorization 头，可选
     * @param circleId      圈子id
     * @param page          分页
     * @param apiCallback   回调
     */
    void getUsersInCircle(String Authorization, @NonNull int circleId, @NonNull int page,
                          ApiCallback<ApiResponse<CircleAttentionUserListEntity>> apiCallback);

    /**
     * 得到圈子黑名单成员接口
     *
     * @param Authorization 头，可选
     * @param circleId      圈子id
     * @param page          分页
     * @param apiCallback   回调
     */
    void getUsersInCircleInBlackList(
            String Authorization, @NonNull int circleId, @NonNull int page,
            ApiCallback<ApiResponse<CircleAttentionUserListEntity>> apiCallback);

    /**
     * 拉黑圈子成员
     *
     * @param Authorization 头
     * @param id            圈子id
     * @param user_id       用户id
     * @param operator_id   操作者Id
     * @param apiCallback   回调
     */
    void putUserInBlackList(@NonNull String Authorization, @NonNull int id,
                            @NonNull int user_id, @NonNull int operator_id,
                            @NonNull ApiCallback<ApiResponse<CircleActionEntity>> apiCallback);

    /**
     * 解除拉黑圈子成员
     *
     * @param Authorization 头
     * @param id            圈子id
     * @param user_id       用户id
     * @param operator_id   操作者Id
     * @param apiCallback   回调
     */
    void pullUserOutBlackList(@NonNull String Authorization, @NonNull int id,
                              @NonNull int user_id, @NonNull int operator_id,
                              @NonNull ApiCallback<ApiResponse<CircleActionEntity>> apiCallback);
}
