package com.jkb.model.dataSource.dynamicDetail.article;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.comment.CommentListEntity;
import com.jkb.api.entity.comment.CommentReplyEntity;
import com.jkb.api.entity.comment.CommentSendEntity;
import com.jkb.api.entity.dynamic.DynamicArticleEntity;
import com.jkb.api.entity.dynamic.DynamicTopicEntity;
import com.jkb.api.entity.operation.OperationActionEntity;

/**
 * 获取话题动态详情的本地数据来源类
 * Created by JustKiddingBaby on 2016/9/17.
 */

public class DynamicDetailArticleRepository implements DynamicDetailArticleDataSource {


    private DynamicDetailArticleDataSource localDataSource;
    private DynamicDetailArticleDataSource remoteDataSource;

    private DynamicDetailArticleRepository(DynamicDetailArticleDataSource localDataSource,
                                           DynamicDetailArticleDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static DynamicDetailArticleRepository INSTANCE = null;

    public static DynamicDetailArticleRepository getInstance(
            @NonNull DynamicDetailArticleDataSource localDataSource,
            @NonNull DynamicDetailArticleDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new DynamicDetailArticleRepository(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getArticleDynamic(
            @NonNull int user_id, @NonNull int dynamic_id,
            @NonNull ApiCallback<ApiResponse<DynamicArticleEntity>> apiCallback) {
        remoteDataSource.getArticleDynamic(user_id, dynamic_id, apiCallback);
    }

    @Override
    public void getComment$Apply(
            String Authorization, @NonNull int dynamicId, @NonNull int page,
            String order,
            ApiCallback<ApiResponse<CommentListEntity>> apiCallback) {
        remoteDataSource.getComment$Apply(Authorization, dynamicId, page, order, apiCallback);
    }

    @Override
    public void likeComment(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
        remoteDataSource.likeComment(Authorization, user_id, target_id, apiCallback);
    }

    @Override
    public void favorite(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
        remoteDataSource.favorite(Authorization, user_id, target_id, apiCallback);
    }

    @Override
    public void sendComment(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int dynamic_id,
            @NonNull String comment,
            @NonNull ApiCallback<ApiResponse<CommentSendEntity>> apiCallback) {
        remoteDataSource.sendComment(Authorization, user_id, dynamic_id, comment, apiCallback);
    }

    @Override
    public void sendReply(
            @NonNull String Authorization, @NonNull int target_user_id, @NonNull int comment_id,
            @NonNull int dynamic_id, @NonNull String comment,
            @NonNull ApiCallback<ApiResponse<CommentReplyEntity>> apiCallback) {
        remoteDataSource.sendReply(Authorization, target_user_id,
                comment_id, dynamic_id, comment, apiCallback);
    }
}
