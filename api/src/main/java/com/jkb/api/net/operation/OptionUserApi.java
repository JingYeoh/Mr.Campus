package com.jkb.api.net.operation;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.operation.OperationUserEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 获取涉及操作的用户请求接口
 * Created by JustKiddingBaby on 2016/8/21.
 */

public interface OptionUserApi {

    /**
     * 获取特定圈子所有订阅的用户：action=subscribe&&targetId=圈子id;
     *
     * @param Authorization 头
     * @param action        动作
     * @param target_id     目标id
     * @return Call
     */
    @GET(Config.URL_OPERATION_USER)
    Call<ApiResponse<OperationUserEntity>> subscribe(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Path(Config.KEY_ACTION) String action,
            @Path(Config.KEY_TARGETID) int target_id,
            @Query(Config.KEY_PAGE) int page
    );

    /**
     * 获取特定圈子所有设为常用圈子的用户：action=inCommonUse&&targetId=圈子id
     *
     * @param Authorization 头
     * @param action        动作
     * @param target_id     目标id
     * @return Call
     */
    @GET(Config.URL_OPERATION_USER)
    Call<ApiResponse<OperationUserEntity>> inCommonUse(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Path(Config.KEY_ACTION) String action,
            @Path(Config.KEY_TARGETID) int target_id,
            @Query(Config.KEY_PAGE) int page
    );

    /**
     * 获取所有喜欢特定动态的用户：action=favorite&&targetId=动态id
     *
     * @param Authorization 头
     * @param action        动作
     * @param target_id     目标id
     * @return Call
     */
    @GET(Config.URL_OPERATION_USER)
    Call<ApiResponse<OperationUserEntity>> favorite(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Path(Config.KEY_ACTION) String action,
            @Path(Config.KEY_TARGETID) int target_id,
            @Query(Config.KEY_PAGE) int page
    );

    /**
     * 获取关注我的人：action=payAttention&&targetId=用户id
     *
     * @param action    动作
     * @param user_id   用户id
     * @param target_id 目标id
     * @return Call
     */
    @Multipart
    @POST(Config.URL_OPERATION_USER)
    Call<ApiResponse<OperationUserEntity>> payAttention(
            @Part(Config.KEY_ACTION) String action,
            @Part(Config.KEY_USER_ID) int user_id,
            @Part(Config.KEY_TARGET_ID) int target_id,
            @Query(Config.KEY_PAGE) int page
    );

    /**
     * 获取点赞了特定评论的用户：action=like&&targetId=评论id
     *
     * @param Authorization 头
     * @param action        动作
     * @param target_id     目标id
     * @return Call
     */
    @GET(Config.URL_OPERATION_USER)
    Call<ApiResponse<OperationUserEntity>> like(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Path(Config.KEY_ACTION) String action,
            @Path(Config.KEY_TARGETID) int target_id,
            @Query(Config.KEY_PAGE) int page
    );

    /**
     * 获取访问我的人：action=visit&&target_id=用户id
     * 过期：该接口已被OperationVisitorApi.visitorMe()方法替换
     *
     * @param Authorization 头
     * @param action        动作
     * @param target_id     目标id
     * @return Call
     */
    @Deprecated
    @GET(Config.URL_OPERATION_USER)
    Call<ApiResponse<OperationUserEntity>> visit(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Path(Config.KEY_ACTION) String action,
            @Path(Config.KEY_TARGETID) int target_id,
            @Query(Config.KEY_PAGE) int page
    );
}
