package com.jkb.model.entering.identify.remote;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.net.auth.IdentifyCodeApi;
import com.jkb.model.entering.identify.IdentifyCodeDataSource;
import com.jkb.api.entity.auth.IdentifyCodeEntity;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * 发送验证码页面的远程数据获取类
 * Created by JustKiddingBaby on 2016/7/31.
 */
public class IdentifyCodeRemoteDataSource implements IdentifyCodeDataSource {

    private static IdentifyCodeRemoteDataSource INSTANCE;

    /**
     * 获取WelcomeRemoteDataSource的单例类对象
     *
     * @return
     */
    public static IdentifyCodeRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IdentifyCodeRemoteDataSource();
        }
        return INSTANCE;
    }

    private IdentifyCodeRemoteDataSource() {
    }

    @Override
    public void sendEmail(String email, Callback<ApiResponse<IdentifyCodeEntity>> cb) {
        IdentifyCodeApi sendEmailApi = ApiFactoryImpl.newInstance().createApi(IdentifyCodeApi.class);
        Call<ApiResponse<IdentifyCodeEntity>> call = sendEmailApi.sendEmail(email);
        call.enqueue(cb);
    }

    @Override
    public void sendEmail(String email, ApiCallback<ApiResponse<IdentifyCodeEntity>> apiCallback) {
        IdentifyCodeApi sendEmailApi = ApiFactoryImpl.newInstance().createApi(IdentifyCodeApi.class);
        Call<ApiResponse<IdentifyCodeEntity>> call = sendEmailApi.sendEmail(email);
        Type type = new TypeToken<ApiResponse<IdentifyCodeEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<IdentifyCodeEntity>>(apiCallback, call, type);
    }

    @Override
    public void sendPhone(String phone, ApiCallback<ApiResponse<IdentifyCodeEntity>> apiCallback) {
        IdentifyCodeApi identifyCodeApi = ApiFactoryImpl.newInstance().createApi(IdentifyCodeApi.class);
        Call<ApiResponse<IdentifyCodeEntity>> call = identifyCodeApi.sendPhone(phone);
        Type type = new TypeToken<ApiResponse<IdentifyCodeEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<IdentifyCodeEntity>>(apiCallback, call, type);
    }
}
