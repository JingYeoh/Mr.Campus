package com.jkb.model.dataSource.dynamicCreate.topic;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.category.CategoryTypeEntity;
import com.jkb.api.entity.dynamic.DynamicPostEntity;
import com.jkb.api.entity.image.ImageUploadEntity;

import okhttp3.MultipartBody;

/**
 * 创建话题动态的数据来源仓库类
 * Created by JustKiddingBaby on 2016/9/26.
 */

public class DynamicCreateTopicDataRepertory implements DynamicCreateTopicDataSource {

    private DynamicCreateTopicDataSource localDataSource;
    private DynamicCreateTopicDataSource remoteDataSource;

    private DynamicCreateTopicDataRepertory(
            @NonNull DynamicCreateTopicDataSource localDataSource,
            @NonNull DynamicCreateTopicDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static DynamicCreateTopicDataRepertory INSTANCE = null;

    public static DynamicCreateTopicDataRepertory getInstance(
            @NonNull DynamicCreateTopicDataSource localDataSource,
            @NonNull DynamicCreateTopicDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new DynamicCreateTopicDataRepertory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void postDynamic(
            @NonNull String Authorization, @NonNull int user_id, @NonNull String dynamic_type,
            @NonNull String title, @NonNull String dcontent, String tag,
            @NonNull ApiCallback<ApiResponse<DynamicPostEntity>> apiCallback) {
        remoteDataSource.postDynamic(Authorization, user_id, dynamic_type, title,
                dcontent, tag, apiCallback);
    }

    @Override
    public void uploadImage(
            @NonNull MultipartBody.Part image,
            @NonNull ApiCallback<ApiResponse<ImageUploadEntity>> apiCallback) {
        remoteDataSource.uploadImage(image, apiCallback);
    }

    @Override
    public void getAllTag(
            @NonNull String type,
            @NonNull ApiCallback<ApiResponse<CategoryTypeEntity>> apiCallback) {
        remoteDataSource.getAllTag(type, apiCallback);
    }
}
