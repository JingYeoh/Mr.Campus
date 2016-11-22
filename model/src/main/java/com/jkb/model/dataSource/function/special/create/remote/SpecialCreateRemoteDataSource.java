package com.jkb.model.dataSource.function.special.create.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicPostEntity;
import com.jkb.api.entity.image.ImageUploadEntity;
import com.jkb.api.net.dynamic.DynamicApi;
import com.jkb.api.net.image.ImageApi;
import com.jkb.model.dataSource.function.special.create.SpecialCreateDataSource;

import java.lang.reflect.Type;

import okhttp3.MultipartBody;
import retrofit2.Call;

/**
 * 创建专题的远程数据来源类
 * Created by JustKiddingBaby on 2016/11/21.
 */

public class SpecialCreateRemoteDataSource implements SpecialCreateDataSource {

    private SpecialCreateRemoteDataSource() {
    }

    private static SpecialCreateRemoteDataSource INSTANCE = null;

    public static SpecialCreateRemoteDataSource newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SpecialCreateRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void postDynamic(
            @NonNull String Authorization, int user_id, @NonNull String dynamic_type,
            @NonNull String title, @NonNull String dcontent, String tag, int schoolId,
            @NonNull ApiCallback<ApiResponse<DynamicPostEntity>> apiCallback) {
        ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
        apiFactory.setHttpClient(apiFactory.genericClient());
        apiFactory.initRetrofit();
        DynamicApi dynamicApi = apiFactory.createApi(DynamicApi.class);
        Call<ApiResponse<DynamicPostEntity>> call;
        call = dynamicApi.postSubjectDynamic(Authorization, user_id, dynamic_type, title,
                dcontent, tag, schoolId);
        Type type = new TypeToken<ApiResponse<DynamicPostEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<DynamicPostEntity>>(apiCallback, call, type);
    }

    @Override
    public void uploadImage(
            @NonNull MultipartBody.Part image,
            @NonNull ApiCallback<ApiResponse<ImageUploadEntity>> apiCallback) {
        ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
        apiFactory.setHttpClient(apiFactory.filePostClient());
        apiFactory.initRetrofit();
        ImageApi imageApi = apiFactory.createApi(ImageApi.class);
        Call<ApiResponse<ImageUploadEntity>> call;
        call = imageApi.uploadImage(image, Config.KEY_DYNAMIC);
        Type type = new TypeToken<ApiResponse<ImageUploadEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<ImageUploadEntity>>(apiCallback, call, type);
    }
}
