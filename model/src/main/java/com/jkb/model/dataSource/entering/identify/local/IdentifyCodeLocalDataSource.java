package com.jkb.model.dataSource.entering.identify.local;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.model.dataSource.entering.identify.IdentifyCodeDataSource;
import com.jkb.api.entity.auth.IdentifyCodeEntity;

import retrofit2.Callback;

/**
 * 获取验证码的本地数据获取类
 * Created by JustKiddingBaby on 2016/7/31.
 */
public class IdentifyCodeLocalDataSource implements IdentifyCodeDataSource {

    private static IdentifyCodeLocalDataSource INSTANCE;

    /**
     * 获取WelcomeRemoteDataSource的单例类对象
     *
     * @return
     */
    public static IdentifyCodeLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IdentifyCodeLocalDataSource();
        }
        return INSTANCE;
    }

    private IdentifyCodeLocalDataSource() {
    }

    @Override
    public void sendEmail(String email, Callback<ApiResponse<IdentifyCodeEntity>> call) {

    }

    @Override
    public void sendEmail(String email, ApiCallback<ApiResponse<IdentifyCodeEntity>> apiCallback) {

    }

    @Override
    public void sendPhone(String phone, ApiCallback<ApiResponse<IdentifyCodeEntity>> apiCallback) {

    }
}
