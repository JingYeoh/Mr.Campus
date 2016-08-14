package com.jkb.api.net.circle;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.circle.CircleCreateEntity;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 创建圈子的API接口
 * Created by JustKiddingBaby on 2016/8/14.
 */

public interface CircleCreateApi {

    /**
     * 创建圈子接口
     *
     * @param token        header内容,用户的Token
     * @param user_id      用户id.
     * @param school_id    学校id.
     * @param name         圈子名称.
     * @param introduction 圈子简介.
     * @param longitude    经度.
     * @param latitude     纬度.
     * @param image        图片文件.
     * @param flag         图片标识（circle）.
     *                     允许值: "circle"
     * @return Call
     */
    @Multipart
    @POST(Config.URL_CIRCLE_CREATE)
    Call<ApiResponse<CircleCreateEntity>> createCircle(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String authorization,
            @Part(Config.KEY_USER_ID) int user_id,
            @Part(Config.KEY_SCHOOL_ID) int school_id,
            @Part(Config.KEY_NAME) String name,
            @Part(Config.KEY_INTRODUCTION) String introduction,
            @Part(Config.KEY_LONGITUDE) double longitude,
            @Part(Config.KEY_LATITUDE) double latitude,
            @Part() MultipartBody.Part image,
            @Part(Config.KEY_FLAG) String flag
    );
}
