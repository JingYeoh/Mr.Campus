package com.jkb.model.dataSource.im.conversation;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.user.UserInfoEntity;

/**
 * 会话页面的数据仓库类
 * Created by JustKiddingBaby on 2016/10/20.
 */

public class ConversationRepertory implements ConversationDataSource {

    private ConversationDataSource localDataSource;
    private ConversationDataSource remoteDataSource;

    private ConversationRepertory(
            ConversationDataSource localDataSource, ConversationDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static ConversationRepertory INSTANCE = null;

    public static ConversationRepertory newInstance(
            @NonNull ConversationDataSource localDataSource,
            @NonNull ConversationDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ConversationRepertory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getUserInfo(
            @NonNull int user_id, @NonNull ApiCallback<ApiResponse<UserInfoEntity>> apiCallback) {
        remoteDataSource.getUserInfo(user_id, apiCallback);
    }
}
