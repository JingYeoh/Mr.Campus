package com.jkb.api.net.image;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.image.ImageUploadEntity;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 上传图片的Api接口
 * Created by JustKiddingBaby on 2016/9/26.
 */

public interface ImageApi {

    /**
     * 上传图片的接口
     *
     * @param image 图片File
     * @param flag  图片标志（dynamic）.
     *              允许值: "dynamic"
     * @return
     */
    @Multipart
    @POST(Config.URL_IMAGE_UPLOAD)
    Call<ApiResponse<ImageUploadEntity>> uploadImage(
            @Part() MultipartBody.Part image,
            @Part(Config.KEY_FLAG) String flag);
}
