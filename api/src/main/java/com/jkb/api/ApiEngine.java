package com.jkb.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * API层的引擎类
 * Created by JustKiddingBaby on 2016/7/31.
 */
public class ApiEngine<T> {

    private static final String TAG = "ApiEngine";
    private ApiCallback<T> apiCallback;
    private Call<T> call;
    private Type mType;

    public ApiEngine(@NonNull ApiCallback apiCallback, Call call, @NonNull Type mType) {
        Log.d(TAG, "ApiEngine");
        this.apiCallback = apiCallback;
        this.call = call;
        this.mType = mType;
        if (apiCallback == null) {
            return;
        }
        //开始请求
        start();
    }

    /**
     * 執行
     */
    private void start() {
        Log.d(TAG, "start");
        Observable.just(call)
                .map(new Func1<Call<T>, Response<T>>() {
                    @Override
                    public Response<T> call(Call<T> tCall) {
                        try {
                            return call.execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Response<T>>() {
                    @Override
                    public void call(Response<T> response) {
                        try {
                            handleResponse(response);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 处理数据
     */
    private void handleResponse(Response<T> response) throws IOException {
        if (response == null) {
            apiCallback.onFail();
            return;
        }
//        Headers headers = response.headers();
//        //打印头信息
//        if (headers != null) {
//            for (int i = 0; i < headers.size(); i++) {
//                Log.i(TAG, headers.name(i) + "---->" + headers.value(i));
//            }
//        }
        int code = response.code();
        Log.d(TAG, code + "");
        switch (code) {
            case 200://成功
                Gson gson = new Gson();
                Logger.json(gson.toJson(response.body()));
                apiCallback.onSuccess(response);
                break;
            case 404:
            case 422:
            default:
                String error = response.errorBody().string();
                //这一层主要为了放置返回的不是json数据
                try {
                    JSONObject object = new JSONObject(error);
                    Logger.json(error);
                    String errorMsg = object.getString("error");
                    if (errorMsg == null || errorMsg.isEmpty()) {
                        errorMsg = "请求错误，请重试";
                    }
                    T obj = new Gson().fromJson(error, mType);
                    apiCallback.onError(response, errorMsg, obj);
                } catch (JSONException e) {
                    apiCallback.onError(response, "服务器异常", null);
                    Log.e(TAG, "error=" + error);
                    e.printStackTrace();
                }
                break;
        }
    }
}
