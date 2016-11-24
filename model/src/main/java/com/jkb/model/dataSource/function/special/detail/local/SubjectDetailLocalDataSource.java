package com.jkb.model.dataSource.function.special.detail.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.comment.CommentListEntity;
import com.jkb.api.entity.comment.CommentReplyEntity;
import com.jkb.api.entity.comment.CommentSendEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.subject.SubjectEntity;
import com.jkb.model.dataSource.function.special.detail.SubjectDetailDataSource;

/**
 * 专题详情的数据仓库类
 * Created by JustKiddingBaby on 2016/11/22.
 */

public class SubjectDetailLocalDataSource implements SubjectDetailDataSource {

    private Context applicationContext;

    private SubjectDetailLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static SubjectDetailLocalDataSource INSTANCE = null;

    public static SubjectDetailLocalDataSource newInstance(Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new SubjectDetailLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getSubjectDynamic(
            @NonNull int user_id, @NonNull int dynamic_id,
            @NonNull ApiCallback<ApiResponse<SubjectEntity>> apiCallback) {
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
            @NonNull String Authorization, @NonNull int target_user_id, @NonNull int comment_id,
            @NonNull int dynamic_id, @NonNull String comment,
            @NonNull ApiCallback<ApiResponse<CommentReplyEntity>> apiCallback) {
    }
}
