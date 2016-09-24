package com.jkb.model.dataSource.dynamicDetail.topic.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.comment.CommentListEntity;
import com.jkb.api.entity.comment.CommentReplyEntity;
import com.jkb.api.entity.comment.CommentSendEntity;
import com.jkb.api.entity.dynamic.DynamicNormalEntity;
import com.jkb.api.entity.dynamic.DynamicTopicEntity;
import com.jkb.api.entity.operation.OperationActionEntity;

/**
 * 获取话题动态详情的本地数据来源类
 * Created by JustKiddingBaby on 2016/9/17.
 */

public class DynamicDetailTopicLocalDataSource implements com.jkb.model.dataSource.dynamicDetail.topic.DynamicDetailTopicDataSource {

    private Context applicationContext;

    private DynamicDetailTopicLocalDataSource(@NonNull Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static DynamicDetailTopicLocalDataSource INSTANCE = null;

    public static DynamicDetailTopicLocalDataSource getInstance(
            @NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new DynamicDetailTopicLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getTopicDynamic(
            @NonNull int user_id, @NonNull int dynamic_id,
            @NonNull ApiCallback<ApiResponse<DynamicTopicEntity>> apiCallback) {

    }

    @Override
    public void getComment$Apply(
            String Authorization, @NonNull int dynamicId, @NonNull int page,
            String order,
            ApiCallback<ApiResponse<CommentListEntity>> apiCallback) {

    }

    @Override
    public void likeComment(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {

    }

    @Override
    public void favorite(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {

    }

    @Override
    public void sendComment(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int dynamic_id,
            @NonNull String comment,
            @NonNull ApiCallback<ApiResponse<CommentSendEntity>> apiCallback) {

    }

    @Override
    public void sendReply(
            @NonNull String Authorization, @NonNull int target_user_id,
            @NonNull int comment_id, @NonNull int dynamic_id, @NonNull String comment,
            @NonNull ApiCallback<ApiResponse<CommentReplyEntity>> apiCallback) {

    }
}
