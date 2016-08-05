package com.jkb.model.entering.identify;


import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.auth.IdentifyCodeEntity;

import retrofit2.Callback;

/**
 * 获取验证码页面的数据获取仓库类
 * Created by JustKiddingBaby on 2016/7/31.
 */
public class IdentifyCodeResponsitory implements IdentifyCodeDataSource {

    private static IdentifyCodeResponsitory INSTANCE = null;

    private IdentifyCodeDataSource localDataSource;//本地数据来源
    private IdentifyCodeDataSource remoteDataSource;//远程数据来源

    private IdentifyCodeResponsitory(@NonNull IdentifyCodeDataSource localDataSource,
                                     @NonNull IdentifyCodeDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public static IdentifyCodeResponsitory getInstance(@NonNull IdentifyCodeDataSource localDataSource,
                                                       @NonNull IdentifyCodeDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new IdentifyCodeResponsitory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void sendEmail(String email, Callback<ApiResponse<IdentifyCodeEntity>> call) {
        remoteDataSource.sendEmail(email, call);
    }

    @Override
    public void sendEmail(String email, ApiCallback<ApiResponse<IdentifyCodeEntity>> apiCallback) {
        remoteDataSource.sendEmail(email, apiCallback);
    }

    @Override
    public void sendPhone(String phone, ApiCallback<ApiResponse<IdentifyCodeEntity>> apiCallback) {
        remoteDataSource.sendPhone(phone, apiCallback);
    }

}
