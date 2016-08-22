package com.jkb.api.net.user;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.user.UserInfoEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * 获取用户数据的接口
 * Created by JustKiddingBaby on 2016/8/17.
 */

public interface UserInfoApi {

    /**
     * 获取用户信息的接口
     *
     * @param id 用户id
     * @return Call
     */
    @GET(Config.URL_USER_INFO)
    Call<ApiResponse<UserInfoEntity>> getUserInfo(
            @Path(Config.KEY_ID) int id);
}
