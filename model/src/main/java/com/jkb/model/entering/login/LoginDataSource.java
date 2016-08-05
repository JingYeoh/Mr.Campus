package com.jkb.model.entering.login;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.entity.auth.LoginEntity;

/**
 * 登录的数据仓库接口
 * Created by JustKiddingBaby on 2016/8/3.
 */
public interface LoginDataSource {

    /**
     * 第三方登录的回调接口
     */
    interface ThirdPlatformListener {
        /**
         * 成功的回调
         *
         * @param thirdPlatformData
         */
        void onSuccess(ThirdPlatformData thirdPlatformData);

        /**
         * 失败的回调
         *
         * @param value
         */
        void onFail(String value);

        /**
         * 取消登录的回调
         */
        void onCancle();
    }

    /**
     * 通过QQ登录
     */
    void loginByQQ(ThirdPlatformListener listener);

    /**
     * 通过微信登录
     */
    void loginByWechat(ThirdPlatformListener listener);

    /**
     * 通过微博登录
     */
    void loginByWeibo(ThirdPlatformListener listener);

    /**
     * 通过人人登录
     */
    void loginByRenRen(ThirdPlatformListener listener);

    /**
     * 通过豆瓣登录
     */
    void loginByDouBan(ThirdPlatformListener listener);

    /**
     * 通过帐号密码登录
     *
     * @param userName
     * @param passWord
     * @param listener
     */
    void loginByCount(String userName, String passWord, ThirdPlatformListener listener);


    /**
     * 第三方登录
     *
     * @param nickname      昵称
     * @param identifier    第三方登录唯一标识
     * @param identity_type 登录类型
     * @param sex           性别
     * @param avatar        头像
     * @param credential    第三方登录Token
     * @param background    背景
     * @param apiCallback   回调接口
     */
    void loginByThirdPlatform(
            @NonNull String nickname,
            @NonNull String identifier, @NonNull String identity_type,
            String sex, String avatar, String credential, String background,
            ApiCallback<LoginEntity> apiCallback);
}
