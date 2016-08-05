package com.jkb.api.net.auth;

import com.jkb.api.ApiResponse;
import com.jkb.api.entity.auth.IdentifyCodeEntity;
import com.jkb.api.config.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 发送邮箱验证码的API
 * Created by JustKiddingBaby on 2016/7/31.
 */
public interface IdentifyCodeApi {

    /**
     * 发送邮箱
     *
     * @param email 邮箱地址
     * @param cb    回调接口
     */
    @Multipart
    @POST(Config.URL_SEND_EMAIL)
    void sendEmail(@Part(Config.KEY_EMAIL) String email, Callback<ApiResponse<IdentifyCodeEntity>> cb);

    /**
     * 发送邮箱接口
     *
     * @param email 邮箱地址
     * @return
     */
    @Multipart
    @POST(Config.URL_SEND_EMAIL)
    Call<ApiResponse<IdentifyCodeEntity>> sendEmail(@Part(Config.KEY_EMAIL) String email);

    /**
     * 发送短信接口
     *
     * @param phone 手机号
     * @return
     */
    @Multipart
    @POST(Config.URL_SEND_PHONE)
    Call<ApiResponse<IdentifyCodeEntity>> sendPhone(@Part(Config.KEY_PHONE) String phone);
}
