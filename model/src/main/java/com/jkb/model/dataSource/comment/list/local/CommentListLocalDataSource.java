package com.jkb.model.dataSource.comment.list.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.comment.CommentListEntity;
import com.jkb.api.entity.comment.CommentReplyEntity;
import com.jkb.api.entity.comment.CommentSendEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.model.dataSource.comment.list.CommentListDataSource;

/**
 * 评论列表的远程数据来源类
 * Created by JustKiddingBaby on 2016/9/20.
 */

public class CommentListLocalDataSource implements CommentListDataSource {

    private Context applicationContext;

    private CommentListLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static CommentListLocalDataSource INSTANCE = null;

    public static CommentListLocalDataSource genInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new CommentListLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getComment$Apply(
            String Authorization, @NonNull int dynamicId, @NonNull int page,
            ApiCallback<ApiResponse<CommentListEntity>> apiCallback) {

    }

    @Override
    public void likeComment(
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
