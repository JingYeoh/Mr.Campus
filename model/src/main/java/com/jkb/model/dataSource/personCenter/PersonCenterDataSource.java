package com.jkb.model.dataSource.personCenter;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.operation.OperationVerifyPayAttentionEntity;
import com.jkb.api.entity.user.UserActionCircleEntity;
import com.jkb.api.entity.user.UserInfoEntity;
import com.jkb.model.intfc.BitmapLoadedCallback;

/**
 * 个人中心的数据来源接口
 * Created by JustKiddingBaby on 2016/8/17.
 */

public interface PersonCenterDataSource {

    /**
     * 得到用户数据
     *
     * @param user_id     用户id
     * @param apiCallback 回调
     */
    void getUserInfo(@NonNull int user_id,
                     @NonNull ApiCallback<ApiResponse<UserInfoEntity>> apiCallback);

    /**
     * 通过网络加载头像
     */
    void loadHeadImgByUrl(@NonNull String url, @NonNull BitmapLoadedCallback callback);

    /**
     * 请求访客的接口
     *
     * @param authorization 头，包含token字段
     * @param user_id       用户id
     * @param target_id     目标id
     * @param apiCallback   回调接口
     */
    void visit(@NonNull String authorization, @NonNull int user_id, @NonNull int target_id,
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

    /**
     * 访客是否被关注的接口
     * 访客是否关注了用户
     *
     * @param user_id     用户id
     * @param visitor_id  访客id
     * @param apiCallback 回调
     */
    void verifyIfPayAttention(
            @NonNull int user_id, @NonNull int visitor_id,
            @NonNull ApiCallback<ApiResponse<OperationVerifyPayAttentionEntity>> apiCallback);

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
