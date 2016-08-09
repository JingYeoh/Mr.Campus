package com.jkb.api.net.auth;

import com.jkb.api.ApiResponse;
import com.jkb.api.entity.auth.LoginEntity;
import com.jkb.api.config.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 登录的API接口
 * Created by JustKiddingBaby on 2016/8/3.
 */
public interface LoginApi {


    /**
     * 通过第三方登录的接口
     *
     * @param nickName      昵称
     * @param identifier    第三方登录的唯一标识
     * @param identity_type 登录类型
     * @param sex           性别
     * @param avatar        头像
     * @param credential    第三方登录Token
     * @param background    背景
     * @@param cb 回调
     */
    @POST(Config.URL_LOGIN_WITH_THIRDPLATFORM)
    void loginThirdPlatform(
            @Query(Config.KEY_NICK_NAME) String nickName,
            @Query(Config.KEY_IDENTIFIER) String identifier,
            @Query(Config.KEY_IDENTITY_TYPE) String identity_type,
            @Query(Config.KEY_SEX) String sex,
            @Query(Config.KEY_AVATAR) String avatar,
            @Query(Config.KEY_CREDENTIAL) String credential,
            @Query(Config.KEY_BACKGROUND) String background,
            Callback<ApiResponse<LoginEntity>> cb);


    /**
     * 通过第三方登录的接口
     *
     * @param nickName      昵称
     * @param identifier    第三方登录的唯一标识
     * @param identity_type 登录类型
     * @param sex           性别
     * @param avatar        头像
     * @param credential    第三方登录Token
     * @param background    背景
     * @return Call
     */
    @POST(Config.URL_LOGIN_WITH_THIRDPLATFORM)
    Call<ApiResponse<LoginEntity>> loginThirdPlatform(
            @Query(Config.KEY_NICK_NAME) String nickName,
            @Query(Config.KEY_IDENTIFIER) String identifier,
            @Query(Config.KEY_IDENTITY_TYPE) String identity_type,
            @Query(Config.KEY_SEX) String sex,
            @Query(Config.KEY_AVATAR) String avatar,
            @Query(Config.KEY_CREDENTIAL) String credential,
            @Query(Config.KEY_BACKGROUND) String background
    );

    /**
     * 手机号登录接口
     *
     * @param phone      手机号
     * @param credential 密码
     * @return Call
     */
    @POST(Config.URL_LOGIN_WITH_PHONE)
    Call<ApiResponse<LoginEntity>> loginWithPhone(
            @Query(Config.KEY_IDENTIFIER) String phone,
            @Query(Config.KEY_CREDENTIAL) String credential
    );

    /**
     * 邮箱号登录接口
     *
     * @param email      邮箱号
     * @param credential 密码
     * @return Call
     */
    @POST(Config.URL_LOGIN_WITH_EMAIL)
    Call<ApiResponse<LoginEntity>> loginWithEmail(
            @Query(Config.KEY_IDENTIFIER) String email,
            @Query(Config.KEY_CREDENTIAL) String credential
    );
}
