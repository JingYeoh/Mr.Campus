package com.jkb.api.net.circle;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.circle.CircleActionEntity;
import com.jkb.api.entity.circle.CircleInfoEntity;
import com.jkb.api.entity.circle.DynamicInCircleListEntity;

import okhttp3.MultipartBody;
import retrofit2.Call;
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
}
