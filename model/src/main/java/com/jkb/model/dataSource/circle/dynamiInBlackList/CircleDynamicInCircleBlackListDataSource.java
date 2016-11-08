package com.jkb.model.dataSource.circle.dynamiInBlackList;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleActionEntity;
import com.jkb.api.entity.circle.CircleDynamicInBlackListEntity;

/**
 * 圈子动态黑名单的数据来源类
 * Created by JustKiddingBaby on 2016/11/5.
 */

public interface CircleDynamicInCircleBlackListDataSource {


    /**
     * 得到所有动态黑名单
     *
     * @param Authorization 头
     * @param circle_id     圈子id
     * @param page          分页
     * @param apiCallback   回调
     */
    void getAllDynamicsInBlackList(
            @NonNull String Authorization, @NonNull int circle_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<CircleDynamicInBlackListEntity>> apiCallback);

    /**
     * 取消拉黑圈子动态
     *
     * @param Authorization 头
     * @param dynamic_id    动态id
     * @param user_id       用户id
     * @param apiCallback   回调
     */
    void pullDynamicOutBlackList(
            @NonNull String Authorization, @NonNull int dynamic_id, @NonNull int user_id,
            @NonNull ApiCallback<ApiResponse<CircleActionEntity>> apiCallback);
}
