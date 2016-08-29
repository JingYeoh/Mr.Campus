package com.jkb.api.net.user;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.user.UserUpdateEntity;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * 修改用户头像和背景的Api网络接口
 * Created by JustKiddingBaby on 2016/8/24.
 */

public interface UserUpdateApi {

    /**
     * 修改用户头像和背景
     *
     * @param Authorization Token
     * @param id            用户id
     * @param image         图片文件
     * @param flag          图片标识.
     *                      允许值: "avatar", "background"
     * @return Call
     */
    @Multipart
    @POST(Config.URL_USER_UPDATE_IMAGE)
    Call<ApiResponse<UserUpdateEntity>> updateImage(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Path(Config.KEY_ID) int id,
            @Part() MultipartBody.Part image,
            @Part(Config.KEY_FLAG) String flag);

    /**
     * 修改用户资料
     *
     * @param Authorization Token
     * @param id            用户id
     * @param column        所修改用户信息的类型.
     *                      允许值: "nickname", "sex", "name", "bref_introduction"
     * @param value         所修改用户信息的类型对应的值
     * @return Call
     */
    @FormUrlEncoded
    @PUT(Config.URL_USER_UPDATE_INFO)
    Call<ApiResponse<UserUpdateEntity>> updateUserInfo(
            @Header(Config.HEADER_KEY_AUTHORIZATION) String Authorization,
            @Path(Config.KEY_ID) int id,
            @Field(Config.KEY_COLUMN) String column,
            @Field(Config.KEY_VALUE) String value
    );
}
