package com.jkb.model.dataSource.entering.resetpassword;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.auth.ResetPasswordEntity;

import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;

/**
 * 重置密码的数据仓库类
 * Created by JustKiddingBaby on 2016/8/5.
 */

public class ResetPasswordResponsitory implements ResetPasswordDataSource {

    private ResetPasswordDataSource localDataSource;
    private ResetPasswordDataSource remoteDataSource;

    private static ResetPasswordResponsitory INSTANCE = null;

    private ResetPasswordResponsitory(@NonNull ResetPasswordDataSource localDataSource,
                                      @NonNull ResetPasswordDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public static ResetPasswordResponsitory getInstance(
            @NonNull ResetPasswordDataSource localDataSource, @NonNull ResetPasswordDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ResetPasswordResponsitory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void resetPasswordWithEmail(String email, String credential,
                                       String code, ApiCallback<ApiResponse<ResetPasswordEntity>> apiCallback) {
        remoteDataSource.resetPasswordWithEmail(email, credential, code, apiCallback);
    }

    @Override
    public void resetPasswordWithPhone(String phone, String credential,
                                       String code, ApiCallback<ApiResponse<ResetPasswordEntity>> apiCallback) {
        remoteDataSource.resetPasswordWithPhone(phone, credential, code, apiCallback);
    }

    @Override
    public void saveUsersToDb(Users users) {
        localDataSource.saveUsersToDb(users);
    }

    @Override
    public void saveUserAuthsToDb(UserAuths userAuths) {
        localDataSource.saveUserAuthsToDb(userAuths);
    }

}
