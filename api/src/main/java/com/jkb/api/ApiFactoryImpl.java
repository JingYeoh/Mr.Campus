package com.jkb.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.config.Config;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * API层的工厂实现类
 * Created by JustKiddingBaby on 2016/7/31.
 */
public class ApiFactoryImpl implements ApiFactory {

    private static final String TAG = "ApiFactoryImpl";

    private Retrofit retrofit;
    private OkHttpClient httpClient;

    private ApiFactoryImpl() {
        genericClient();
        //初始化Retrofit
        initRetrofit();
    }

    public static ApiFactoryImpl newInstance() {
        return new ApiFactoryImpl();
    }


    @Override
    public <T> T createApi(@NonNull Class<T> clz) {
        T api = null;
        try {
            Class<T> clazz = (Class<T>) Class.forName(clz.getName());
            //添加Retrofit的转换依赖
            api = retrofit.create(clazz);
        } catch (Exception e) {
            Log.e(TAG, "类转换失败");
            e.printStackTrace();
        }
        return api;
    }

    /**
     * 返回Retrofit实例
     *
     * @return
     */
    public Retrofit initRetrofit() {
        retrofit = new Retrofit.Builder()
                //添加的地址url
                .baseUrl(Config.API_HOST)
                //添加返回值为Gson的支持
                .addConverterFactory(GsonConverterFactory.create())
                // 增加返回值为Oservable<T>的支持
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(httpClient)
                .build();
        return retrofit;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * 设置OKHttp拦截器
     *
     * @return
     */
    public OkHttpClient genericClient() {
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                .addHeader("Accept", "application/json")
                                .build();
                        return chain.proceed(request);
                    }

                })
                .build();

        return httpClient;
    }

    /**
     * 设置OKHttp拦截器
     *
     * @return
     */
    public OkHttpClient filePostClient() {
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/form-data")
                                .addHeader("Accept", "application/json")
                                .build();
                        return chain.proceed(request);
                    }

                })
                .build();

        return httpClient;
    }
}