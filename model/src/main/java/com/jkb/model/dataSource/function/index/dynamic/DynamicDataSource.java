package com.jkb.model.dataSource.function.index.dynamic;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;

/**
 * 首页——动态：数据来源接口类
 * Created by JustKiddingBaby on 2016/9/3.
 */

public interface DynamicDataSource {


    /**
     * 获取全部动态信息
     *
     * @param Authorization token
     * @param page          分页数据
     * @param apiCallback   回调
     */
    void getAllDynamic(
            @NonNull String Authorization, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicListEntity>> apiCallback);

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
}
