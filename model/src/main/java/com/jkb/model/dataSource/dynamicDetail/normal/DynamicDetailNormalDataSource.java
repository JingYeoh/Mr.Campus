package com.jkb.model.dataSource.dynamicDetail.normal;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.comment.CommentListEntity;
import com.jkb.api.entity.comment.CommentSendEntity;
import com.jkb.api.entity.dynamic.DynamicNormalEntity;
import com.jkb.api.entity.operation.OperationActionEntity;

/**
 * 动态详情：普通的数据来源接口
 * Created by JustKiddingBaby on 2016/9/17.
 */

public interface DynamicDetailNormalDataSource {
    /**
     * 得到普通动态
     *
     * @param user_id     用户id
     * @param dynamic_id  动态id
     * @param apiCallback 回调
     */
    void getNormalDynamic(
            @NonNull int user_id, @NonNull int dynamic_id,
            @NonNull ApiCallback<ApiResponse<DynamicNormalEntity>> apiCallback);

    /**
     * 获取评论和回复
     *
     * @param Authorization 头，可为空
     * @param dynamicId     动态id
     * @param page          分页
     * @param apiCallback   回调
     */
    void getComment$Apply(
            String Authorization, @NonNull int dynamicId, @NonNull int page,
            ApiCallback<ApiResponse<CommentListEntity>> apiCallback);

    /**
     * 点赞/取消点赞评论
     *
     * @param Authorization 头
     * @param user_id       用户id
     * @param target_id     目标id
     * @param apiCallback   回调
     */
    void likeComment(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback);

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
     * 发送评论
     *
     * @param Authorization token
     * @param user_id       用户id
     * @param dynamic_id    动态id
     * @param comment       评论内容
     * @param apiCallback   回调
     */
    void sendComment(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int dynamic_id,
            @NonNull String comment,
            @NonNull ApiCallback<ApiResponse<CommentSendEntity>> apiCallback);
}
