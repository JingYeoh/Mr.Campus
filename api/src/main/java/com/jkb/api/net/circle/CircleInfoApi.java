package com.jkb.api.net.circle;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.circle.CircleActionEntity;
import com.jkb.api.entity.circle.CircleAttentionUserListEntity;
import com.jkb.api.entity.circle.CircleDynamicInBlackListEntity;
import com.jkb.api.entity.circle.CircleInfoEntity;
import com.jkb.api.entity.circle.DynamicInCircleListEntity;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 圈子信息的Api接口
 * Created by JustKiddingBaby on 2016/8/29.
 */

public interface CircleInfoApi {

    @GET(Config.URL_CIRCLE_INFO)
    Call<ApiResponse<CircleInfoEntity>> circleInfo(
            @Path(Config.KEY_USERID) int userId,
            @Path(Config.KEY_ID) int id
    );

    /**
     * 得到所有圈子中动态
     *
     * @param Authorization 头可选
     * @param circleId      圈子id
     * @param page          分页
     * @return Call
     */
    @GET(Config.URL_CIRCLE_ALLDYNAMIC)
    Call<ApiResponse<DynamicInCircleListEntity>> getAllDynamicInCircle(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Path(Config.KEY_CIRCLEID) int circleId,
            @Query(Config.KEY_PAGE) int page
    );

    /**
     * 修改圈子信息
     *
     * @param Authorization 头，必选
     * @param id            圈子id
     * @param column        圈子所修改信息的类别.
     *                      允许值: "name", "introduction", "school_id"
     * @param value         类别对应的值.
     * @param user_id       操作者的用户id.
     * @return Call
     */
    @FormUrlEncoded()
    @PUT(Config.URL_CIRCLE_UPDATE_INFO)
    Call<ApiResponse<CircleActionEntity>> updateCircleInfo(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Path(Config.KEY_ID) int id,
            @Field(Config.KEY_COLUMN) String column,
            @Field(Config.KEY_VALUE) String value,
            @Field(Config.KEY_USER_ID) int user_id
    );

    /**
     * 修改圈子图片
     *
     * @param Authorization 头，必选
     * @param id            圈子id
     * @param image         图片文件
     * @param flag          标识，允许值“circle”
     * @param user_id       用户id
     * @return Call
     */
    @Multipart
    @POST(Config.URL_CIRCLE_UPDATE_IMAGE)
    Call<ApiResponse<CircleActionEntity>> updateCirclePicture(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Path(Config.KEY_ID) int id,
            @Part() MultipartBody.Part image,
            @Part(Config.KEY_FLAG) String flag,
            @Part(Config.KEY_USER_ID) int user_id
    );

    /**
     * 得到关注圈子的用户列表
     *
     * @param Authorization 头，可选
     * @param circleId      圈子id
     * @param page          分页
     * @return Call
     */
    @GET(Config.URL_CIRCLE_USERS_IN_CIRCLE)
    Call<ApiResponse<CircleAttentionUserListEntity>> getUsersInCircle(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Path(Config.KEY_CIRCLEID) int circleId,
            @Query(Config.KEY_PAGE) int page
    );

    /**
     * 得到圈子黑名单
     *
     * @param Authorization 头，可选
     * @param id            圈子id
     * @param page          分页
     * @return Call
     */
    @GET(Config.URL_CIRCLE_USERS_IN_CIRCLE_BLACKLIST)
    Call<ApiResponse<CircleAttentionUserListEntity>> getUsersInCircleBlackList(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Path(Config.KEY_ID) int id,
            @Query(Config.KEY_PAGE) int page
    );

    /**
     * 得到圈子黑名单动态
     *
     * @param Authorization 头，可选
     * @param id            圈子id
     * @param page          分页
     * @return Call
     */
    @GET(Config.URL_CIRCLE_DYNAMICS_IN_CIRCLE_BLACKLIST)
    Call<ApiResponse<CircleDynamicInBlackListEntity>> getDynamicsInCircleBlackList(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Path(Config.KEY_ID) int id,
            @Query(Config.KEY_PAGE) int page
    );

    /**
     * 拉黑用户
     *
     * @param Authorization 头，必选
     * @param circle_id     圈子id
     * @param user_id       用户id
     * @param operator_id   操作者id
     * @return Call
     */
    @Multipart
    @POST(Config.URL_CIRCLE_PUT_USER_IN_BLACKLIST)
    Call<ApiResponse<CircleActionEntity>> putUserToBlackList(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Part(Config.KEY_CIRCLE_ID) int circle_id,
            @Part(Config.KEY_USER_ID) int user_id,
            @Part(Config.KEY_OPERATOR_ID) int operator_id
    );

    /**
     * 解除拉黑用户
     *
     * @param Authorization 头
     * @param id            圈子id
     * @param user_id       被拉黑的用户id
     * @param operator_id   操作者id
     * @return Call
     */
    @DELETE(Config.URL_CIRCLE_PULL_USER_OUT_BLACKLIST)
    Call<ApiResponse<CircleActionEntity>> pullUserOutBlackList(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Path(Config.KEY_ID) int id,
            @Query(Config.KEY_USER_ID) int user_id,
            @Query(Config.KEY_OPERATOR_ID) int operator_id
    );

    /**
     * 拉黑动态
     *
     * @param Authorization 头，必选
     * @param dynamic_id    动态id
     * @param user_id       用户id
     * @return Call
     */
    @Multipart
    @POST(Config.URL_CIRCLE_PUT_DYNAMIC_IN_BLACKLIST)
    Call<ApiResponse<CircleActionEntity>> putDynamicInBlackList(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Part(Config.KEY_DYNAMIC_ID) int dynamic_id,
            @Part(Config.KEY_USER_ID) int user_id
    );

    /**
     * 解除拉黑动态
     *
     * @param Authorization 头，必选
     * @param id            动态id
     * @param user_id       用户id
     * @return Call
     */
    @DELETE(Config.URL_CIRCLE_PULL_DYNAMIC_OUT_DTNAMIC)
    Call<ApiResponse<CircleActionEntity>> pullDynamicOutBlackList(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Path(Config.KEY_ID) int id,
            @Query(Config.KEY_USER_ID) int user_id
    );
}
