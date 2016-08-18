package com.jkb.model.dataSource.usersList.attention;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.user.UserActionUserEntity;

/**
 * 关注用户列表的数据仓库类
 * Created by JustKiddingBaby on 2016/8/17.
 */

public class AttentionDataResponsitory implements AttentionDataSource {

    private AttentionDataSource localDataSource;
    private AttentionDataSource remoteDataSource;

    private static AttentionDataResponsitory INSTANCE = null;

    private AttentionDataResponsitory(AttentionDataSource localDataSource, AttentionDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public static AttentionDataResponsitory getInstance(
            @NonNull AttentionDataSource localDataSource, @NonNull AttentionDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new AttentionDataResponsitory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void payAttention(int page,@NonNull int userId, @NonNull ApiCallback<ApiResponse<UserActionUserEntity>> apiCallback) {
        remoteDataSource.payAttention(page,userId, apiCallback);
    }
}
