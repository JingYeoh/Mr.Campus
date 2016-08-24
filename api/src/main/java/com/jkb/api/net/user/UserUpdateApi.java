package com.jkb.api.net.user;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.user.UserUpdateEntity;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
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
}
