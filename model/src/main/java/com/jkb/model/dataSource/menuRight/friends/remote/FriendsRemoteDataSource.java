package com.jkb.model.dataSource.menuRight.friends.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.contacts.FriendListEntity;
import com.jkb.api.entity.dynamic.DynamicListEntity;
import com.jkb.api.net.contacts.ContactsApi;
import com.jkb.model.dataSource.menuRight.friends.FriendsDataSource;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 好友列表的远程数据来源类
 * Created by JustKiddingBaby on 2016/9/6.
 */

public class FriendsRemoteDataSource implements FriendsDataSource {


    private FriendsRemoteDataSource() {
    }

    private static FriendsRemoteDataSource INSTANCE = null;

    public static FriendsRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FriendsRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getFriendsList(
            @NonNull String Authorization, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<FriendListEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        ContactsApi contactsApi = factory.createApi(ContactsApi.class);
        Call<ApiResponse<FriendListEntity>> call;
        call = contactsApi.getContactsUsers(Authorization, page);
        Type type = new TypeToken<ApiResponse<FriendListEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<FriendListEntity>>(apiCallback, call, type);
    }
}
