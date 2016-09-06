package com.jkb.api.net.contacts;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.contacts.FriendListEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * 获取好友（相关关注的用户）
 * Created by JustKiddingBaby on 2016/9/6.
 */

public interface ContactsApi {
    /**
     * 获取相关关注的好友列表
     *
     * @param Authorization token
     * @param page          分页
     * @return Call
     */
    @GET(Config.URL_CONTACTS_FRIENDS)
    Call<ApiResponse<FriendListEntity>> getContactsUsers(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Query(Config.KEY_PAGE) int page);
}
