package com.jkb.model.entering.login.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.entity.auth.LoginEntity;
import com.jkb.model.entering.login.LoginDataSource;

/**
 * 登录的本地数据来源类
 * Created by JustKiddingBaby on 2016/8/3.
 */
public class LoginLocalDataSource implements LoginDataSource {

    private Context applicationContext;
    private static LoginLocalDataSource INSTANCE;

    public static LoginLocalDataSource getInstance(Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new LoginLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    private LoginLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
        this.applicationContext = applicationContext;
    }

    @Override
    public void loginByQQ(ThirdPlatformListener listener) {

    }

    @Override
    public void loginByWechat(ThirdPlatformListener listener) {

    }

    @Override
    public void loginByWeibo(ThirdPlatformListener listener) {

    }

    @Override
    public void loginByRenRen(ThirdPlatformListener listener) {

    }

    @Override
    public void loginByDouBan(ThirdPlatformListener listener) {

    }

    @Override
    public void loginByCount(String userName, String passWord, ThirdPlatformListener listener) {

    }

    @Override
    public void loginByThirdPlatform(
            @NonNull String nickname, @NonNull String identifier,
            @NonNull String identity_type, String sex, String avatar,
            String credential, String background, ApiCallback<LoginEntity> apiCallback) {

    }
}
