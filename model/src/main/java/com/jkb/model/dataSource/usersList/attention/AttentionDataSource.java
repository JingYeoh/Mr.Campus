package com.jkb.model.dataSource.usersList.attention;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.user.UserActionUserEntity;

/**
 * 关注的数据来源接口
 * Created by JustKiddingBaby on 2016/8/17.
 */

public interface AttentionDataSource {

    /**
     * 获取用户关注的用户
     *
     * @param page       请求页数
     * @param userId     用户id
     * @param visitor_id 访客id,可选
     */
    void payAttention(@NonNull int page, @NonNull int userId, int visitor_id,
                      @NonNull ApiCallback<ApiResponse<UserActionUserEntity>> apiCallback);

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
