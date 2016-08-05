package com.jkb.api.net.auth;

import com.jkb.api.ApiResponse;
import com.jkb.api.entity.auth.RegisterEntity;
import com.jkb.api.config.Config;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * 通过邮箱注册API接口
 * Created by JustKiddingBaby on 2016/8/1.
 */
public interface RegisterApi {
    /**
     * 请求注册的接口
     * 使用邮箱注册
     *
     * @param nickName      昵称
     * @param code          验证码
     * @param credential    帐号密码
     * @param identity_type 认证类型
     * @param identifier    认证帐号手机/邮箱
     * @param image         头像文件
     * @param flag          标识
     * @return Call对象
     */
    @Multipart
    @POST(Config.URL_REGISTER_WITH_EMAIL)
    Call<ApiResponse<RegisterEntity>> registerWithEmail(
            @Query(Config.KEY_NICK_NAME) String nickName,
            @Query(Config.KEY_CODE) String code,
            @Query(Config.KEY_CREDENTIAL) String credential,
            @Query(Config.KEY_IDENTITY_TYPE) String identity_type,
            @Query(Config.KEY_IDENTIFIER) String identifier,
            @Part() MultipartBody.Part image,
            @Part(Config.KEY_FLAG) String flag);

    /**
     * 请求注册的接口
     * 使用手机号注册
     *
     * @param nickName      昵称
     * @param code          验证码
     * @param credential    帐号密码
     * @param identity_type 认证类型
     * @param identifier    认证帐号手机/邮箱
     * @param image         头像文件
     * @param flag          标识
     * @return Call对象
     */
    @Multipart
    @POST(Config.URL_REGISTER_WITH_PHONE)
    Call<ApiResponse<RegisterEntity>> registerWithPhone(
            @Query(Config.KEY_NICK_NAME) String nickName,
            @Query(Config.KEY_CODE) String code,
            @Query(Config.KEY_CREDENTIAL) String credential,
            @Query(Config.KEY_IDENTITY_TYPE) String identity_type,
            @Query(Config.KEY_IDENTIFIER) String identifier,
            @Part() MultipartBody.Part image,
            @Part(Config.KEY_FLAG) String flag);
}
