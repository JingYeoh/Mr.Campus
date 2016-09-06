package com.jkb.model.dataSource.menuRight.friends;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.contacts.FriendListEntity;

/**
 * 好友列表的数据仓库类
 * Created by JustKiddingBaby on 2016/9/6.
 */

public class FriendsRepertory implements FriendsDataSource {

    private FriendsDataSource localDataSource;
    private FriendsDataSource remoteDataSource;

    private FriendsRepertory(FriendsDataSource localDataSource, FriendsDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static FriendsRepertory INSTANCE = null;

    public static FriendsRepertory getInstance(
            @NonNull FriendsDataSource localDataSource,
            @NonNull FriendsDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new FriendsRepertory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getFriendsList(
            @NonNull String Authorization, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<FriendListEntity>> apiCallback) {
        remoteDataSource.getFriendsList(Authorization, page, apiCallback);
    }
}
