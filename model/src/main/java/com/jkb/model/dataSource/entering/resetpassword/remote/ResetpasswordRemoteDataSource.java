package com.jkb.model.dataSource.entering.resetpassword.remote;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.auth.ResetPasswordEntity;
import com.jkb.api.net.auth.ResetPasswordApi;
import com.jkb.model.dataSource.entering.resetpassword.ResetPasswordDataSource;

import java.lang.reflect.Type;

import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;
import retrofit2.Call;

/**
 * 重置密码的远程数据仓库类
 * Created by JustKiddingBaby on 2016/8/5.
 */
public class ResetpasswordRemoteDataSource implements ResetPasswordDataSource {

    private static ResetpasswordRemoteDataSource INSTANCE = null;

    private ResetpasswordRemoteDataSource() {
    }

    public static ResetpasswordRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ResetpasswordRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void resetPasswordWithEmail(String email, String credential, String code, ApiCallback<ApiResponse<ResetPasswordEntity>> apiCallback) {
        ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
        apiFactory.setHttpClient(apiFactory.genericClient());
        apiFactory.initRetrofit();
        ResetPasswordApi resetPasswordApi = apiFactory.createApi(ResetPasswordApi.class);
        Call<ApiResponse<ResetPasswordEntity>> call = resetPasswordApi.resetPasswordWithEmail(email, credential, code);
        Type type = new TypeToken<ApiResponse<ResetPasswordEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<ResetPasswordEntity>>(apiCallback, call, type);
    }

    @Override
    public void resetPasswordWithPhone(String phone, String credential, String code, ApiCallback<ApiResponse<ResetPasswordEntity>> apiCallback) {
        ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
        apiFactory.setHttpClient(apiFactory.genericClient());
        apiFactory.initRetrofit();
        ResetPasswordApi resetPasswordApi = apiFactory.createApi(ResetPasswordApi.class);
        Call<ApiResponse<ResetPasswordEntity>> call = resetPasswordApi.resetPasswordWithPhone(phone, credential, code);
        Type type = new TypeToken<ApiResponse<ResetPasswordEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<ResetPasswordEntity>>(apiCallback, call, type);
    }

    @Override
    public void saveUsersToDb(Users users) {

    }

    @Override
    public void saveUserAuthsToDb(UserAuths userAuths) {

    }
}
