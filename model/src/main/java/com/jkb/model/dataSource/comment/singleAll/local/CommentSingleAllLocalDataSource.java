package com.jkb.model.dataSource.comment.singleAll.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.comment.Comment$ReplyEntity;
import com.jkb.api.entity.comment.CommentListEntity;
import com.jkb.api.entity.comment.CommentSendEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.model.dataSource.comment.singleAll.CommentSingleAllDataSource;

/**
 * 评论列表的远程数据来源类
 * Created by JustKiddingBaby on 2016/9/20.
 */

public class CommentSingleAllLocalDataSource implements CommentSingleAllDataSource {

    private Context applicationContext;

    private CommentSingleAllLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static CommentSingleAllLocalDataSource INSTANCE = null;

    public static CommentSingleAllLocalDataSource genInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new CommentSingleAllLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getSingleComment$Apply(
            String Authorization, @NonNull int commentId, @NonNull int page,
            ApiCallback<ApiResponse<Comment$ReplyEntity>> apiCallback) {

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
}
