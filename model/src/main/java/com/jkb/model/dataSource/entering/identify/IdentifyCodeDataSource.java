package com.jkb.model.dataSource.entering.identify;


import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.auth.IdentifyCodeEntity;

import retrofit2.Callback;

/**
 * 请求验证码的数据获取接口
 * Created by JustKiddingBaby on 2016/7/31.
 */
public interface IdentifyCodeDataSource {

    /**
     * 发送验证码
     *
     * @param email
     * @param call
     */
    void sendEmail(String email, Callback<ApiResponse<IdentifyCodeEntity>> call);

    /**
     * 发送邮箱验证码
     *
     * @param email       邮箱地址
     * @param apiCallback 处理数据的中间层
     */
    void sendEmail(String email, ApiCallback<ApiResponse<IdentifyCodeEntity>> apiCallback);

    /**
     * 发送短信验证码
     *
     * @param phone       手机号
     * @param apiCallback 回调接口
     */
    void sendPhone(String phone, ApiCallback<ApiResponse<IdentifyCodeEntity>> apiCallback);
}
