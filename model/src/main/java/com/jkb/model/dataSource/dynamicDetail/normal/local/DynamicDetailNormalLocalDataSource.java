package com.jkb.model.dataSource.dynamicDetail.normal.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.comment.CommentListEntity;
import com.jkb.api.entity.comment.CommentSendEntity;
import com.jkb.api.entity.dynamic.DynamicNormalEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.model.dataSource.dynamicDetail.normal.DynamicDetailNormalDataSource;

/**
 * 获取普通动态详情的本地数据来源类
 * Created by JustKiddingBaby on 2016/9/17.
 */

public class DynamicDetailNormalLocalDataSource implements DynamicDetailNormalDataSource {

    private Context applicationContext;

    private DynamicDetailNormalLocalDataSource(@NonNull Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static DynamicDetailNormalLocalDataSource INSTANCE = null;

    public static DynamicDetailNormalLocalDataSource getInstance(
            @NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new DynamicDetailNormalLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getNormalDynamic(
            @NonNull int user_id, @NonNull int dynamic_id,
            @NonNull ApiCallback<ApiResponse<DynamicNormalEntity>> apiCallback) {

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
}
