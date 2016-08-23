package com.jkb.model.dataSource.usersList.visitor;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.operation.OperationUserEntity;
import com.jkb.api.entity.operation.OperationVisitorEntity;
import com.jkb.api.entity.user.UserActionUserEntity;
import com.jkb.api.entity.user.UserActionVisitorEntity;

/**
 * 访客的数据来源仓库类
 * Created by JustKiddingBaby on 2016/8/19.
 */

public class VisitorDataResponsitory implements VisitorDataSource {

    private VisitorDataSource localDataSource;
    private VisitorDataSource remoteDataSource;

    public VisitorDataResponsitory(VisitorDataSource localDataSource,
                                   VisitorDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static VisitorDataResponsitory INSTANCE = null;

    public static VisitorDataResponsitory getInstance(
            @NonNull VisitorDataSource localDataSource,
            @NonNull VisitorDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new VisitorDataResponsitory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }


    @Override
    public void visitorMe(
            @NonNull int user_id, int visitor_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<OperationVisitorEntity>> apiCallback) {
        remoteDataSource.visitorMe(user_id, visitor_id, page, apiCallback);
    }

    @Override
    public void payAttentionOrCancle(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
        remoteDataSource.payAttentionOrCancle(Authorization, user_id, target_id, apiCallback);
    }
}
