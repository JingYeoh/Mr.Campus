package com.jkb.model.dataSource.entering.login;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.auth.LoginEntity;

import jkb.mrcampus.db.entity.Status;
import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;

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
    public void loginWithPhone(String phone, String passWord, ApiCallback<ApiResponse<LoginEntity>> apiCallback) {
        remoteDataSource.loginWithPhone(phone, passWord, apiCallback);
    }

    @Override
    public void loginWithEmail(String email, String passWord, ApiCallback<ApiResponse<LoginEntity>> apiCallback) {
        remoteDataSource.loginWithEmail(email, passWord, apiCallback);
    }


    @Override
    public void loginByThirdPlatform(
            @NonNull String nickname, @NonNull String identifier,
            @NonNull String identity_type, String sex, String avatar,
            String credential, String background, ApiCallback<ApiResponse<LoginEntity>> apiCallback) {
        remoteDataSource.loginByThirdPlatform(nickname, identifier,
                identity_type, sex, avatar, credential, background, apiCallback);
    }

    @Override
    public void saveUserToDb(Users users) {
        localDataSource.saveUserToDb(users);
    }

    @Override
    public void saveUserAuthsToDb(UserAuths userAuths) {
        localDataSource.saveUserAuthsToDb(userAuths);
    }

    @Override
    public void saveStatusToDb(Status status) {
        localDataSource.saveStatusToDb(status);
    }

    @Override
    public void getUserAuthsDataFromDb(UserAuthsDataCallback callback) {
        localDataSource.getUserAuthsDataFromDb(callback);
    }

    @Override
    public String getCurrentVersion() {
        return localDataSource.getCurrentVersion();
    }

    @Override
    public void getBitmapFromUrl(String url, BitmapDataCallback callback) {
        remoteDataSource.getBitmapFromUrl(url, callback);
    }

    @Override
    public void cacheBitmapToFile(String path, String name, Bitmap bitmap, @NonNull BitmapToFileDataCallback callback) {
        localDataSource.cacheBitmapToFile(path, name, bitmap, callback);
    }

}
