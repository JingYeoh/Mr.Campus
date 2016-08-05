package com.jkb.api;

import retrofit2.Response;

/**
 * API层执行请求完毕后的回调方法
 * Created by JustKiddingBaby on 2016/7/31.
 */
public interface ApiCallback<T> {

    /**
     * 返回成功的方法
     *
     * @param response
     */
    void onSuccess(Response<T> response);

    /**
     * 调用错误的方法
     *
     * @param response
     * @Param error
     * @Param apiResponse
     */
    void onError(Response<T> response, String error, T apiResponse);

    /**
     * 请求失败的方法
     */
    void onFail();
}
