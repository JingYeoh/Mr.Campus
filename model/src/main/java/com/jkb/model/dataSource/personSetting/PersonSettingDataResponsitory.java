package com.jkb.model.dataSource.personSetting;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.user.UserInfoEntity;
import com.jkb.api.entity.user.UserUpdateEntity;
import com.jkb.model.intfc.BitmapLoadedCallback;

import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;
import okhttp3.MultipartBody;

/**
 * 个人设置的数据仓库类
 * Created by JustKiddingBaby on 2016/8/24.
 */

public class PersonSettingDataResponsitory implements PersonSettingDataSource {

    private PersonSettingDataSource localDataSource;
    private PersonSettingDataSource remoteDataSource;


    public PersonSettingDataResponsitory(PersonSettingDataSource localDataSource,
                                         PersonSettingDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public static PersonSettingDataResponsitory INSTANCE = null;

    public static PersonSettingDataResponsitory getInstance(
            @NonNull PersonSettingDataSource localDataSource,
            @NonNull PersonSettingDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new PersonSettingDataResponsitory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void saveUsersToDb(Users users) {
        localDataSource.saveUsersToDb(users);
    }

    @Override
    public void saveUserAuthsToDb(UserAuths userAuths) {
        localDataSource.saveUserAuthsToDb(userAuths);
    }

    @Override
    public void getUserInfo(@NonNull int user_id,
                            @NonNull ApiCallback<ApiResponse<UserInfoEntity>> apiCallback) {
        remoteDataSource.getUserInfo(user_id, apiCallback);
    }

    @Override
    public void loadBitmapByUrl(@NonNull String url, @NonNull BitmapLoadedCallback callback) {
        remoteDataSource.loadBitmapByUrl(url, callback);
    }

    @Override
    public void uploadHeadImg(
            @NonNull String Authorization, @NonNull int id, @NonNull MultipartBody.Part image,
            @NonNull ApiCallback<ApiResponse<UserUpdateEntity>> apiCallback) {
        remoteDataSource.uploadHeadImg(Authorization, id, image, apiCallback);
    }

    @Override
    public void uploadBackGround(
            @NonNull String Authorization, @NonNull int id, @NonNull MultipartBody.Part image,
            @NonNull ApiCallback<ApiResponse<UserUpdateEntity>> apiCallback) {
        remoteDataSource.uploadBackGround(Authorization, id, image, apiCallback);
    }

    @Override
    public void updateUserInfo(
            @NonNull String Authorization, @NonNull int id, @NonNull String column,
            @NonNull String value, @NonNull ApiCallback<ApiResponse<UserUpdateEntity>> apiCallback) {
        remoteDataSource.updateUserInfo(Authorization, id, column, value, apiCallback);
    }

}
