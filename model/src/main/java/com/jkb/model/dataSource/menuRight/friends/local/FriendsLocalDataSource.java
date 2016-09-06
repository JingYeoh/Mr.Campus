package com.jkb.model.dataSource.menuRight.friends.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.contacts.FriendListEntity;
import com.jkb.model.dataSource.menuRight.friends.FriendsDataSource;

/**
 * 好友列表的本地数据来源类
 * Created by JustKiddingBaby on 2016/9/6.
 */

public class FriendsLocalDataSource implements FriendsDataSource {

    private Context applictionContext;

    private FriendsLocalDataSource(Context applictionContext) {
        this.applictionContext = applictionContext;
    }

    private static FriendsLocalDataSource INSTANCE = null;

    public static FriendsLocalDataSource getInstance(@NonNull Context applictionContext) {
        if (INSTANCE == null) {
            INSTANCE = new FriendsLocalDataSource(applictionContext);
        }
        return INSTANCE;
    }

    @Override
    public void getFriendsList(
            @NonNull String Authorization, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<FriendListEntity>> apiCallback) {

    }
}
