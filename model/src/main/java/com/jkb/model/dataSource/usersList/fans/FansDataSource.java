package com.jkb.model.dataSource.usersList.fans;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.operation.OperationUserEntity;

/**
 * 粉丝的数据来源接口类
 * Created by JustKiddingBaby on 2016/8/19.
 */

public interface FansDataSource {
    /**
     * 获取用户关注的用户
     *
     * @param Authorization 包含token的头部
     * @param page          请求页数
     * @param target_id     用户id
     */
    void fans(@NonNull String Authorization,
              @NonNull int page, @NonNull int target_id,
              @NonNull ApiCallback<ApiResponse<OperationUserEntity>> apiCallback);

    /**
     * 关注或者取消关注用户接口
     *
     * @param Authorization 包含token的头部
     * @param user_id       用户id
     * @param target_id     目标id
     */
    void payAttentionOrCancle(@NonNull String Authorization,
                              @NonNull int user_id, @NonNull int target_id,
                              @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback);
}
