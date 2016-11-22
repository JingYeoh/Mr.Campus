package com.jkb.model.dataSource.personCenter.original.myDynamic.circle;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.dynamic.DynamicCircleListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.user.UserActionCircleEntity;

/**
 * 我的普通动态的数据接口
 * Created by JustKiddingBaby on 2016/10/14.
 */

public interface MyDynamicCircleDataSource {

    /**
     * 得到我的圈子动态数据
     *
     * @param Authorization 头，可选
     * @param user_id       用户Id
     * @param circle_id     圈子id，全部传递0
     * @param page          分页
     * @param apiCallback   回调
     */
    void getMyDynamicCircle(
            String Authorization, @NonNull int user_id, @NonNull int circle_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicCircleListEntity>> apiCallback);

    /**
     * 删除动态
     *
     * @param Authorization 头，必选
     * @param dynamic_id    动态id
     * @param apiCallback   回调
     */
    void deleteDynamic(
            @NonNull String Authorization, @NonNull int dynamic_id, @NonNull int operator_id,
            @NonNull ApiCallback<ApiResponse<DynamicActionEntity>> apiCallback);

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
     * 得到订阅的圈子
     *
     * @param user_id     用户id
     * @param visitor_id  訪客id
     * @param page        页数
     * @param apiCallback 回调方法
     */
    void subscribeCircle(
            @NonNull int user_id, int visitor_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<UserActionCircleEntity>> apiCallback);
}
