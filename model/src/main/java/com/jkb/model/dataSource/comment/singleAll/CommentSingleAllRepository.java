package com.jkb.model.dataSource.comment.singleAll;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.comment.Comment$ReplyEntity;
import com.jkb.api.entity.comment.CommentListEntity;
import com.jkb.api.entity.comment.CommentReplyEntity;
import com.jkb.api.entity.comment.CommentSendEntity;
import com.jkb.api.entity.operation.OperationActionEntity;

/**
 * 评论列表的数据仓库类
 * Created by JustKiddingBaby on 2016/9/20.
 */

public class CommentSingleAllRepository implements CommentSingleAllDataSource {

    private CommentSingleAllDataSource localDataSource;
    private CommentSingleAllDataSource remoteDataSource;

    private CommentSingleAllRepository(CommentSingleAllDataSource localDataSource,
                                       CommentSingleAllDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static CommentSingleAllRepository INSTANCE = null;

    public static CommentSingleAllRepository getInstance(
            @NonNull CommentSingleAllDataSource localDataSource,
            @NonNull CommentSingleAllDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new CommentSingleAllRepository(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }


    @Override
    public void getSingleComment$Apply(
            String Authorization, @NonNull int commentId,
            ApiCallback<ApiResponse<Comment$ReplyEntity>> apiCallback) {
        remoteDataSource.getSingleComment$Apply(Authorization, commentId, apiCallback);
    }

    @Override
    public void likeComment(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
        remoteDataSource.likeComment(Authorization, user_id, target_id, apiCallback);
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
