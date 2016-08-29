package com.jkb.api.net.user;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.user.UserActionCircleEntity;
import com.jkb.api.entity.user.UserActionDynamicEntity;
import com.jkb.api.entity.user.UserActionUserEntity;
import com.jkb.api.entity.user.UserActionVisitorEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 获取用户关注、喜欢等 API接口
 * Created by JustKiddingBaby on 2016/8/17.
 */

public interface UserActionApi {

    /**
     * * 获取用户关注的圈子
     *
     * @param action     操作类型.
     *                   允许值: "subscribe", "inCommonUse", "favorite", "payAttention", "visitor"
     * @param userId     用户ID
     * @param visitor_id 访客id
     * @param page       请求的页数字段
     * @return Call
     */
    @Multipart
    @POST(Config.URL_USER_ACTION)
    Call<ApiResponse<UserActionCircleEntity>> subscribe(
            @Part(Config.KEY_ACTION) String action,
            @Part(Config.KEY_USER_ID) int userId,
            @Part(Config.KEY_VISITOR_ID) int visitor_id,
            @Query(Config.KEY_PAGE) int page);

    /**
     * * 获取用户常用的圈子
     *
     * @param action 操作类型.
     *               允许值: "subscribe", "inCommonUse", "favorite", "payAttention", "visitor"
     * @param userId 用户ID
     * @param page   请求的页数字段
     * @return Call
     */
    @GET(Config.URL_USER_ACTION)
    Call<ApiResponse<UserActionCircleEntity>> inCommonUse(
            @Path(Config.KEY_ACTION) String action,
            @Path(Config.KEY_USERID) int userId,
            @Query(Config.KEY_PAGE) int page);

    /**
     * * 获取用户关注的用户
     *
     * @param action     操作类型.
     *                   允许值: "subscribe", "inCommonUse", "favorite", "payAttention", "visitor"
     * @param user_id    用户ID
     * @param visitor_id 访客id，可空
     * @param page       请求的页数字段
     * @return Call
     */
    @Multipart
    @POST(Config.URL_USER_ACTION)
    Call<ApiResponse<UserActionUserEntity>> payAttention(
            @Part(Config.KEY_ACTION) String action,
            @Part(Config.KEY_USER_ID) int user_id,
            @Part(Config.KEY_VISITOR_ID) int visitor_id,
            @Query(Config.KEY_PAGE) int page);

    /**
     * * 获取用户访客
     *
     * @param action 操作类型.
     *               允许值: "subscribe", "inCommonUse", "favorite", "payAttention", "visitor"
     * @param userId 用户ID
     * @param page   请求的页数字段
     * @return Call
     */
    @GET(Config.URL_USER_ACTION)
    Call<ApiResponse<UserActionVisitorEntity>> visitor(
            @Path(Config.KEY_ACTION) String action,
            @Path(Config.KEY_USERID) int userId,
            @Query(Config.KEY_PAGE) int page);

    /**
     * * 获取用户喜欢的动态
     *
     * @param action 操作类型.
     *               允许值: "subscribe", "inCommonUse", "favorite", "payAttention", "visitor"
     * @param userId 用户ID
     * @param page   请求的页数字段
     * @return Call
     */
    @GET(Config.URL_USER_ACTION)
    Call<ApiResponse<UserActionDynamicEntity>> favorite(
            @Path(Config.KEY_ACTION) String action,
            @Path(Config.KEY_USERID) int userId,
            @Query(Config.KEY_PAGE) int page);
}
