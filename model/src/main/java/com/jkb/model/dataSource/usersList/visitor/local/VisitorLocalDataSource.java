package com.jkb.model.dataSource.usersList.visitor.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.operation.OperationUserEntity;
import com.jkb.api.entity.operation.OperationVisitorEntity;
import com.jkb.api.entity.user.UserActionUserEntity;
import com.jkb.api.entity.user.UserActionVisitorEntity;
import com.jkb.model.dataSource.usersList.visitor.VisitorDataSource;

/**
 * 获取访客的本地数据仓库
 * Created by JustKiddingBaby on 2016/8/19.
 */

public class VisitorLocalDataSource implements VisitorDataSource {

    private Context applicationContext;
    private static VisitorLocalDataSource INSTANCE;

    public VisitorLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static VisitorLocalDataSource getInstance(Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new VisitorLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }


    @Override
    public void visitorMe(
            @NonNull int user_id, int visitor_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<OperationVisitorEntity>> apiCallback) {

    }

    @Override
    public void payAttentionOrCancle(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {

    }
}
