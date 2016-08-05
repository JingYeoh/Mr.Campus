package com.jkb.api.net.auth;

import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.auth.ResetPasswordEntity;

import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 重置密码的API接口
 * Created by JustKiddingBaby on 2016/8/5.
 */

public interface ResetPasswordApi {

    /**
     * 邮箱号重置密码页面
     *
     * @param email       邮箱号
     * @param credential  新密码
     * @param code        验证码
     * @return
     */
    @Multipart
    @POST(Config.URL_RESET_PASSWORD_WITH_EMAIL)
    Call<ApiResponse<ResetPasswordEntity>> resetPasswordWithEmail(
            @Part(Config.KEY_IDENTIFIER) String email,
            @Part(Config.KEY_CREDENTIAL) String credential,
            @Part(Config.KEY_CODE) String code);

    /**
     * 手机号重置密码页面
     *
     * @param phone       手机号
     * @param credential  新密码
     * @param code        验证码
     * @return
     */
    @Multipart
    @POST(Config.URL_RESET_PASSWORD_WITH_PHONE)
    Call<ApiResponse<ResetPasswordEntity>> resetPasswordWithPhone(
            @Part(Config.KEY_IDENTIFIER) String phone,
            @Part(Config.KEY_CREDENTIAL) String credential,
            @Part(Config.KEY_CODE) String code);
}
