package com.jkb.model.dataSource.function.index.hot;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicPopularListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;

/**
 * 首页——动态：数据来源接口类
 * Created by JustKiddingBaby on 2016/9/3.
 */

public interface DynamicHotDataSource {


    /**
     * 获取全部动态信息
     *
     * @param Authorization token
     * @param schoolId      学校id
     * @param page          分页数据
     * @param apiCallback   回调
     */
    void getAllHotDynamic(
            String Authorization, @NonNull int schoolId, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicPopularListEntity>> apiCallback);

    /**
     * 喜欢/点赞动态
     *
     * @param Authorization token
     * @param user_id       用户id
     * @param target_id     目标id
     * @param apiCallback   回调
     */
    void favorite(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback);

    /**
     * 订阅圈子
     *
     * @param Authorization 头
     * @param user_id       用户id
     * @param target_id     目标Id
     * @param apiCallback   回调
     */
    void subscribeCircle(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback);

    /**
     * 关注用户
     *
     * @param Authorization 头
     * @param user_id       用户id
     * @param target_id     目标Id
     * @param apiCallback   回调
     */
    void payAttentionUser(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback);
}
