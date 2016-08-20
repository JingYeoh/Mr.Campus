package com.jkb.model.dataSource.usersList.visitor;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.user.UserActionUserEntity;
import com.jkb.api.entity.user.UserActionVisitorEntity;

/**
 * 访客的数据来源仓库类
 * Created by JustKiddingBaby on 2016/8/19.
 */

public interface VisitorDataSource {

    /**
     * 获取访客用户
     *
     * @param page   请求页数
     * @param userId 用户id
     */
    void visit(@NonNull int page, @NonNull int userId,
               @NonNull ApiCallback<ApiResponse<UserActionVisitorEntity>> apiCallback);

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
