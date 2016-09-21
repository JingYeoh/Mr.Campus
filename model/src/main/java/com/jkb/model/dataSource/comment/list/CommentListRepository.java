package com.jkb.model.dataSource.comment.list;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.comment.CommentListEntity;
import com.jkb.api.entity.comment.CommentSendEntity;
import com.jkb.api.entity.operation.OperationActionEntity;

/**
 * 评论列表的数据仓库类
 * Created by JustKiddingBaby on 2016/9/20.
 */

public class CommentListRepository implements CommentListDataSource {

    private CommentListDataSource localDataSource;
    private CommentListDataSource remoteDataSource;

    private CommentListRepository(CommentListDataSource localDataSource,
                                  CommentListDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static CommentListRepository INSTANCE = null;

    public static CommentListRepository getInstance(
            @NonNull CommentListDataSource localDataSource,
            @NonNull CommentListDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new CommentListRepository(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }


    @Override
    public void getComment$Apply(
            String Authorization, @NonNull int dynamicId, @NonNull int page,
            ApiCallback<ApiResponse<CommentListEntity>> apiCallback) {
        remoteDataSource.getComment$Apply(Authorization, dynamicId, page, apiCallback);
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
}
