package com.jkb.model.dataSource.usersList.attention;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.user.UserActionUserEntity;

/**
 * 关注的数据来源接口
 * Created by JustKiddingBaby on 2016/8/17.
 */

public interface AttentionDataSource {

    /**
     * 获取用户关注的用户
     *
     * @param userId 用户id
     */
    void payAttention(@NonNull int userId, @NonNull ApiCallback<ApiResponse<UserActionUserEntity>> apiCallback);
}
