package com.jkb.model.entering.login;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.entity.auth.LoginEntity;

/**
 * 登录的数据仓库类
 * Created by JustKiddingBaby on 2016/8/3.
 */
public class LoginResponsitory implements LoginDataSource {

    private static LoginResponsitory INSTANCE;
    private LoginDataSource localDataSource;
    private LoginDataSource remoteDataSource;

    public static LoginResponsitory getInstance(
            @NonNull LoginDataSource localDataSource, @NonNull LoginDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new LoginResponsitory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    private LoginResponsitory(@NonNull LoginDataSource localDataSource,
                              @NonNull LoginDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }


    @Override
    public void loginByQQ(ThirdPlatformListener listener) {
        remoteDataSource.loginByQQ(listener);
    }

    @Override
    public void loginByWechat(ThirdPlatformListener listener) {
        remoteDataSource.loginByWechat(listener);
    }

    @Override
    public void loginByWeibo(ThirdPlatformListener listener) {
        remoteDataSource.loginByWeibo(listener);
    }

    @Override
    public void loginByRenRen(ThirdPlatformListener listener) {
        remoteDataSource.loginByRenRen(listener);
    }

    @Override
    public void loginByDouBan(ThirdPlatformListener listener) {
        remoteDataSource.loginByDouBan(listener);
    }

    @Override
    public void loginByCount(String userName, String passWord, ThirdPlatformListener listener) {
        remoteDataSource.loginByCount(userName, passWord, listener);
    }

    @Override
    public void loginByThirdPlatform(
            @NonNull String nickname, @NonNull String identifier,
            @NonNull String identity_type, String sex, String avatar,
            String credential, String background, ApiCallback<LoginEntity> apiCallback) {
        remoteDataSource.loginByThirdPlatform(nickname, identifier,
                identity_type, sex, avatar, credential, background, apiCallback);
    }
}
