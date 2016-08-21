package com.jkb.api.net.operation;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.operation.OperationActionEntity;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Operation的请求API接口
 * Created by JustKiddingBaby on 2016/8/20.
 */

public interface OperationApi {

    /**
     * 订阅/取消订阅圈子(action=subscribe)
     *
     * @param Authorization 头部信息
     * @param action        操作类型.
     * @param user_id       操作者用户id.
     * @param target_id     目标id。action=subscribe或inCommonUse时，目标id为圈子id;action=favorite时
     *                      ，目标id为动态id;action=payAttention时，目标id为用户id;action=like时，
     *                      目标id为评论id;action=visit时，目标id为用户id.
     * @return Call
     */
    @POST(Config.URL_OPERATION)
    @Multipart
    Call<ApiResponse<OperationActionEntity>> subscribe(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Part(Config.KEY_ACTION) String action,
            @Part(Config.KEY_USER_ID) int user_id,
            @Part(Config.KEY_TARGET_ID) int target_id);

    /**
     * 设为常用圈子/取消设为常用圈子(action=inCommonUse)
     *
     * @param Authorization 头部信息
     * @param action        操作类型.
     * @param user_id       操作者用户id.
     * @param target_id     目标id。action=subscribe或inCommonUse时，目标id为圈子id;action=favorite时
     *                      ，目标id为动态id;action=payAttention时，目标id为用户id;action=like时，
     *                      目标id为评论id;action=visit时，目标id为用户id.
     * @return Call
     */
    @POST(Config.URL_OPERATION)
    @Multipart
    Call<ApiResponse<OperationActionEntity>> inCommonUse(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Part(Config.KEY_ACTION) String action,
            @Part(Config.KEY_USER_ID) int user_id,
            @Part(Config.KEY_TARGET_ID) int target_id);

    /**
     * 关注/取消关注用户(action=payAttention)
     *
     * @param Authorization 头部信息
     * @param action        操作类型.
     * @param user_id       操作者用户id.
     * @param target_id     目标id。action=subscribe或inCommonUse时，目标id为圈子id;action=favorite时
     *                      ，目标id为动态id;action=payAttention时，目标id为用户id;action=like时，
     *                      目标id为评论id;action=visit时，目标id为用户id.
     * @return Call
     */
    @POST(Config.URL_OPERATION)
    @Multipart
    Call<ApiResponse<OperationActionEntity>> payAttention(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Part(Config.KEY_ACTION) String action,
            @Part(Config.KEY_USER_ID) int user_id,
            @Part(Config.KEY_TARGET_ID) int target_id);

    /**
     * 点赞/取消点赞评论（action=like）
     *
     * @param Authorization 头部信息
     * @param action        操作类型.
     * @param user_id       操作者用户id.
     * @param target_id     目标id。action=subscribe或inCommonUse时，目标id为圈子id;action=favorite时
     *                      ，目标id为动态id;action=payAttention时，目标id为用户id;action=like时，
     *                      目标id为评论id;action=visit时，目标id为用户id.
     * @return Call
     */
    @POST(Config.URL_OPERATION)
    @Multipart
    Call<ApiResponse<OperationActionEntity>> like(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Part(Config.KEY_ACTION) String action,
            @Part(Config.KEY_USER_ID) int user_id,
            @Part(Config.KEY_TARGET_ID) int target_id);

    /**
     * 记录访客（action=visit）
     *
     * @param Authorization 头部信息
     * @param action        操作类型.
     * @param user_id       操作者用户id.
     * @param target_id     目标id。action=subscribe或inCommonUse时，目标id为圈子id;action=favorite时
     *                      ，目标id为动态id;action=payAttention时，目标id为用户id;action=like时，
     *                      目标id为评论id;action=visit时，目标id为用户id.
     * @return Call
     */
    @POST(Config.URL_OPERATION)
    @Multipart
    Call<ApiResponse<OperationActionEntity>> visit(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Part(Config.KEY_ACTION) String action,
            @Part(Config.KEY_USER_ID) int user_id,
            @Part(Config.KEY_TARGET_ID) int target_id);
}
