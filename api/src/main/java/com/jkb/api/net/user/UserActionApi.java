package com.jkb.api.net.user;

import com.jkb.api.ApiEngine;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.user.UserActionCircleEntity;
import com.jkb.api.entity.user.UserActionDynamicEntity;
import com.jkb.api.entity.user.UserActionUserEntity;
import com.jkb.api.entity.user.UserActionVisitorEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 获取用户关注、喜欢等 API接口
 * Created by JustKiddingBaby on 2016/8/17.
 */

public interface UserActionApi {

    /**
     * * 获取用户关注的圈子
     *
     * @param action 操作类型.
     *               允许值: "subscribe", "inCommonUse", "favorite", "payAttention", "visitor"
     * @param userId 用户ID
     * @return Call
     */
    @GET(Config.URL_USER_ACTION)
    Call<ApiResponse<UserActionCircleEntity>> subscribe(
            @Path(Config.KEY_ACTION) String action,
            @Path(Config.KEY_USERID) int userId);

    /**
     * * 获取用户常用的圈子
     *
     * @param action 操作类型.
     *               允许值: "subscribe", "inCommonUse", "favorite", "payAttention", "visitor"
     * @param userId 用户ID
     * @return Call
     */
    @GET(Config.URL_USER_ACTION)
    Call<ApiResponse<UserActionCircleEntity>> inCommonUse(
            @Path(Config.KEY_ACTION) String action,
            @Path(Config.KEY_USERID) int userId);

    /**
     * * 获取用户关注的用户
     *
     * @param action 操作类型.
     *               允许值: "subscribe", "inCommonUse", "favorite", "payAttention", "visitor"
     * @param userId 用户ID
     * @return Call
     */
    @GET(Config.URL_USER_ACTION)
    Call<ApiResponse<UserActionUserEntity>> payAttention(
            @Path(Config.KEY_ACTION) String action,
            @Path(Config.KEY_USERID) int userId);

    /**
     * * 获取用户访客
     *
     * @param action 操作类型.
     *               允许值: "subscribe", "inCommonUse", "favorite", "payAttention", "visitor"
     * @param userId 用户ID
     * @return Call
     */
    @GET(Config.URL_USER_ACTION)
    Call<ApiResponse<UserActionVisitorEntity>> visitor(
            @Path(Config.KEY_ACTION) String action,
            @Path(Config.KEY_USERID) int userId);

    /**
     * * 获取用户喜欢的动态
     *
     * @param action 操作类型.
     *               允许值: "subscribe", "inCommonUse", "favorite", "payAttention", "visitor"
     * @param userId 用户ID
     * @return Call
     */
    @GET(Config.URL_USER_ACTION)
    Call<ApiResponse<UserActionDynamicEntity>> favorite(
            @Path(Config.KEY_ACTION) String action,
            @Path(Config.KEY_USERID) int userId);
}
