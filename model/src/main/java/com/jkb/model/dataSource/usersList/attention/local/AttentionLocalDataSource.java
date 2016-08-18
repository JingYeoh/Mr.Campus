package com.jkb.model.dataSource.usersList.attention.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.user.UserActionUserEntity;
import com.jkb.model.dataSource.usersList.attention.AttentionDataSource;

/**
 * 关注用户列表的本地仓库数据来源
 * Created by JustKiddingBaby on 2016/8/17.
 */

public class AttentionLocalDataSource implements AttentionDataSource {

    private Context applicationContext;

    private AttentionLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static AttentionLocalDataSource INSTANCE = null;

    public static AttentionLocalDataSource getInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new AttentionLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void payAttention(int page,@NonNull int userId, @NonNull ApiCallback<ApiResponse<UserActionUserEntity>> apiCallback) {

    }
}
