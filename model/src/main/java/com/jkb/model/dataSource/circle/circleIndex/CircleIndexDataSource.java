package com.jkb.model.dataSource.circle.circleIndex;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleInfoEntity;
import com.jkb.api.entity.circle.DynamicInCircleListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.model.intfc.BitmapLoadedCallback;

/**
 * 圈子首页的数据来源接口
 * Created by JustKiddingBaby on 2016/8/29.
 */

public interface CircleIndexDataSource {

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
     * 通过网络加载图片
     */
    void loadBitmapByUrl(@NonNull String url, @NonNull BitmapLoadedCallback callback);

    /**
     * 订阅/取消订阅圈子
     *
     * @param user_id       用户id
     * @param target_id     圈子Id
     * @param Authorization 头
     * @param apiCallback   回调
     */
    void circleSubscribeOrNot(
            @NonNull int user_id, @NonNull int target_id,
            @NonNull String Authorization,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback);

    /**
     * 得到所有圈子内动态数据
     *
     * @param Authorization 头.可选
     * @param circleId      圈子id
     * @param page          页码
     * @param apiCallback   回调
     */
    void getAllDynamicInCircle(
            String Authorization, @NonNull int circleId, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicInCircleListEntity>> apiCallback);
}
