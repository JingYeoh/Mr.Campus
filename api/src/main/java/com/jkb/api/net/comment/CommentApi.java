package com.jkb.api.net.comment;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.comment.Comment$ReplyEntity;
import com.jkb.api.entity.comment.CommentListEntity;
import com.jkb.api.entity.comment.CommentReplyEntity;
import com.jkb.api.entity.comment.CommentSendEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 评论的请求Api类接口
 * Created by JustKiddingBaby on 2016/9/18.
 */

public interface CommentApi {
    /**
     * 获取评论和回复的接口
     *
     * @param Authorization 头Token，可选
     * @param dynamicId     动态id
     * @param page          分页
     * @return Call
     */
    @GET(Config.URL_COMMENT_LIST)
    Call<ApiResponse<CommentListEntity>> getComment$Reply(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Path(Config.KEY_DYNAMICID) int dynamicId,
            @Query(Config.KEY_PAGE) int page);

    /**
     * 发表评论
     *
     * @param Authorization 头
     * @param user_id       用户id
     * @param dynamic_id    动态id
     * @param comment       评论内容
     * @return Call
     */
    @Multipart
    @POST(Config.URL_COMMENT_SEND)
    Call<ApiResponse<CommentSendEntity>> sendComment(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Part(Config.KEY_USER_ID) int user_id,
            @Part(Config.KEY_DYNAMIC_ID) int dynamic_id,
            @Part(Config.KEY_COMMENT) String comment);

    /**
     * 获取评论和回复的接口
     *
     * @param Authorization 头Token，可选
     * @param commentId     评论id
     * @return Call
     */
    @GET(Config.URL_COMMENT_SINGLE)
    Call<ApiResponse<Comment$ReplyEntity>> getSingleComment$Reply(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Path(Config.KEY_COMMENTID) int commentId);

    /**
     * 回复评论
     *
     * @param Authorization  头
     * @param target_user_id 用户id
     * @param comment_id     评论id
     * @param dynamic_id     动态id
     * @param comment        评论内容
     * @return Call
     */
    @Multipart
    @POST(Config.URL_COMMENT_REPLY_SEND)
    Call<ApiResponse<CommentReplyEntity>> sendReplyComment(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Part(Config.KEY_TARGET_USER_ID) int target_user_id,
            @Part(Config.KEY_COMMENT_ID) int comment_id,
            @Part(Config.KEY_DYNAMIC_ID) int dynamic_id,
            @Part(Config.KEY_COMMENT) String comment);
}
