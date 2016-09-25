package com.jkb.model.dataSource.dynamicDetail.article.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.comment.CommentListEntity;
import com.jkb.api.entity.comment.CommentReplyEntity;
import com.jkb.api.entity.comment.CommentSendEntity;
import com.jkb.api.entity.dynamic.DynamicArticleEntity;
import com.jkb.api.entity.dynamic.DynamicTopicEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.model.dataSource.dynamicDetail.article.DynamicDetailArticleDataSource;

/**
 * 获取话题动态详情的本地数据来源类
 * Created by JustKiddingBaby on 2016/9/17.
 */

public class DynamicDetailArticleLocalDataSource implements DynamicDetailArticleDataSource {

    private Context applicationContext;

    private DynamicDetailArticleLocalDataSource(@NonNull Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static DynamicDetailArticleLocalDataSource INSTANCE = null;

    public static DynamicDetailArticleLocalDataSource getInstance(
            @NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new DynamicDetailArticleLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getArticleDynamic(
            @NonNull int user_id, @NonNull int dynamic_id,
            @NonNull ApiCallback<ApiResponse<DynamicArticleEntity>> apiCallback) {

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
