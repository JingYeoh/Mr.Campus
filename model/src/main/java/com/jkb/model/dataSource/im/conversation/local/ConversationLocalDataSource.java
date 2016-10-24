package com.jkb.model.dataSource.im.conversation.local;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleInfoEntity;
import com.jkb.api.entity.user.UserInfoEntity;
import com.jkb.model.dataSource.im.conversation.ConversationDataSource;

/**
 * 会话的本地数据来源类
 * Created by JustKiddingBaby on 2016/10/20.
 */

public class ConversationLocalDataSource implements ConversationDataSource {

    private Context applicationContext;

    private ConversationLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static ConversationLocalDataSource INSTANCE = null;

    public static ConversationLocalDataSource newInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new ConversationLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getUserInfo(
            @NonNull int user_id, @NonNull ApiCallback<ApiResponse<UserInfoEntity>> apiCallback) {

    }

    @Override
    public void getCircleInfo(
            @NonNull int userId, @NonNull int id,
            @NonNull ApiCallback<ApiResponse<CircleInfoEntity>> apiCallback) {

    }
}
