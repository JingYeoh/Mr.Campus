package com.jkb.model.dataSource.usersList.fans.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.operation.OperationUserEntity;
import com.jkb.model.dataSource.usersList.fans.FansDataSource;

/**
 * 获取粉丝列表的本地数据仓库来源类
 * Created by JustKiddingBaby on 2016/8/21.
 */

public class FansDataLocalSource implements FansDataSource {

    private Context applicationContext;

    private FansDataLocalSource(@NonNull Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static FansDataLocalSource INSTANCE = null;

    public static FansDataLocalSource getInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new FansDataLocalSource(applicationContext);
        }
        return INSTANCE;
    }


    @Override
    public void fans(
            int user_id,
            @NonNull int page, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationUserEntity>> apiCallback) {

    }

    @Override
    public void payAttentionOrCancle(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {

    }
}
