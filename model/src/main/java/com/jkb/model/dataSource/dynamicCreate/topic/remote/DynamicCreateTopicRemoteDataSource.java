package com.jkb.model.dataSource.dynamicCreate.topic.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.category.CategoryTypeEntity;
import com.jkb.api.entity.circle.CircleCreateEntity;
import com.jkb.api.entity.dynamic.DynamicPostEntity;
import com.jkb.api.entity.image.ImageUploadEntity;
import com.jkb.api.net.category.CategoryApi;
import com.jkb.api.net.circle.CircleCreateApi;
import com.jkb.api.net.dynamic.DynamicApi;
import com.jkb.api.net.image.ImageApi;
import com.jkb.model.dataSource.dynamicCreate.topic.DynamicCreateTopicDataSource;

import java.lang.reflect.Type;

import okhttp3.MultipartBody;
import retrofit2.Call;

/**
 * 创建话题动态的数据远程数据来源类
 * Created by JustKiddingBaby on 2016/9/26.
 */

public class DynamicCreateTopicRemoteDataSource implements DynamicCreateTopicDataSource {

    private DynamicCreateTopicRemoteDataSource() {
    }

    private static DynamicCreateTopicRemoteDataSource INSTANCE = null;

    public static DynamicCreateTopicRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DynamicCreateTopicRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void postDynamic(
            @NonNull String Authorization, @NonNull int user_id, @NonNull String dynamic_type,
            @NonNull String title, @NonNull String dcontent, String tag,
            @NonNull ApiCallback<ApiResponse<DynamicPostEntity>> apiCallback) {
        ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
        apiFactory.setHttpClient(apiFactory.genericClient());
        apiFactory.initRetrofit();
        DynamicApi dynamicApi = apiFactory.createApi(DynamicApi.class);
        Call<ApiResponse<DynamicPostEntity>> call;
        call = dynamicApi.postDynamic(Authorization, user_id, dynamic_type, title, dcontent, tag);
        Type type = new TypeToken<ApiResponse<DynamicPostEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<DynamicPostEntity>>(apiCallback, call, type);
    }

    @Override
    public void postDynamic(
            @NonNull String Authorization, @NonNull int user_id, @NonNull String dynamic_type,
            @NonNull String title, @NonNull String dcontent, String tag, @NonNull int circle_id,
            @NonNull ApiCallback<ApiResponse<DynamicPostEntity>> apiCallback) {
        ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
        apiFactory.setHttpClient(apiFactory.genericClient());
        apiFactory.initRetrofit();
        DynamicApi dynamicApi = apiFactory.createApi(DynamicApi.class);
        Call<ApiResponse<DynamicPostEntity>> call;
        call = dynamicApi.postDynamic(Authorization, user_id, dynamic_type, title,
                dcontent, tag, circle_id);
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

    @Override
    public void getAllTag(
            @NonNull String type,
            @NonNull ApiCallback<ApiResponse<CategoryTypeEntity>> apiCallback) {
        ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
        apiFactory.setHttpClient(apiFactory.genericClient());
        apiFactory.initRetrofit();
        CategoryApi categoryApi = apiFactory.createApi(CategoryApi.class);
        Call<ApiResponse<CategoryTypeEntity>> call;
        call = categoryApi.getCategoryType(type);
        Type apiType = new TypeToken<ApiResponse<CategoryTypeEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<CategoryTypeEntity>>(apiCallback, call, apiType);
    }
}
