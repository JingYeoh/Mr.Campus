package com.jkb.model.dataSource.im.conversation;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleInfoEntity;
import com.jkb.api.entity.user.UserInfoEntity;

/**
 * 会话的数据来源类
 * Created by JustKiddingBaby on 2016/10/20.
 */

public interface ConversationDataSource {
    /**
     * 得到用户数据
     *
     * @param user_id     用户id
     * @param apiCallback 回调
     */
    void getUserInfo(@NonNull int user_id,
                     @NonNull ApiCallback<ApiResponse<UserInfoEntity>> apiCallback);

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
}
