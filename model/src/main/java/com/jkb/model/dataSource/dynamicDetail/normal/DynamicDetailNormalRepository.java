package com.jkb.model.dataSource.dynamicDetail.normal;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.comment.CommentListEntity;
import com.jkb.api.entity.comment.CommentSendEntity;
import com.jkb.api.entity.dynamic.DynamicNormalEntity;
import com.jkb.api.entity.operation.OperationActionEntity;

/**
 * 获取普通动态详情的本地数据来源类
 * Created by JustKiddingBaby on 2016/9/17.
 */

public class DynamicDetailNormalRepository implements DynamicDetailNormalDataSource {


    private DynamicDetailNormalDataSource localDataSource;
    private DynamicDetailNormalDataSource remoteDataSource;

    private DynamicDetailNormalRepository(DynamicDetailNormalDataSource localDataSource,
                                          DynamicDetailNormalDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static DynamicDetailNormalRepository INSTANCE = null;

    public static DynamicDetailNormalRepository getInstance(
            @NonNull DynamicDetailNormalDataSource localDataSource,
            @NonNull DynamicDetailNormalDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new DynamicDetailNormalRepository(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getNormalDynamic(
            @NonNull int user_id, @NonNull int dynamic_id,
            @NonNull ApiCallback<ApiResponse<DynamicNormalEntity>> apiCallback) {
        remoteDataSource.getNormalDynamic(user_id, dynamic_id, apiCallback);
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
}
