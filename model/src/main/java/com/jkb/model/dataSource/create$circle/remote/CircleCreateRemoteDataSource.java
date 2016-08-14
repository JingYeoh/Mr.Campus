package com.jkb.model.dataSource.create$circle.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleCreateEntity;
import com.jkb.api.net.circle.CircleCreateApi;
import com.jkb.model.dataSource.create$circle.CircleCreateDataSource;

import java.lang.reflect.Type;

import okhttp3.MultipartBody;
import retrofit2.Call;

/**
 * 创建圈子的远程数据来源类
 * Created by JustKiddingBaby on 2016/8/14.
 */

public class CircleCreateRemoteDataSource implements CircleCreateDataSource {

    private static CircleCreateRemoteDataSource INSTANCE = null;

    private CircleCreateRemoteDataSource() {
    }

    public static CircleCreateRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CircleCreateRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void createCircle(
            @NonNull int user_id, @NonNull int school_id, @NonNull String name,
            @NonNull String introduction, double latiude, double longitude,
            @NonNull String authorization, MultipartBody.Part image, String flag,
            ApiCallback<ApiResponse<CircleCreateEntity>> apiCallback) {
        //请求创建圈子接口
        ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
        apiFactory.setHttpClient(apiFactory.filePostClient());
        apiFactory.initRetrofit();
        CircleCreateApi circleCreateApi = apiFactory.createApi(CircleCreateApi.class);
        Call<ApiResponse<CircleCreateEntity>> call = null;
        call = circleCreateApi.createCircle(
                authorization, user_id, school_id, name, introduction, longitude, latiude, image, flag
        );
        Type type = new TypeToken<ApiResponse<CircleCreateEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<CircleCreateEntity>>(apiCallback, call, type);
    }
}
