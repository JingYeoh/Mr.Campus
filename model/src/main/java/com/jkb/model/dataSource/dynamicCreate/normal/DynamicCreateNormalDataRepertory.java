package com.jkb.model.dataSource.dynamicCreate.normal;

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

public class DynamicCreateNormalDataRepertory implements DynamicCreateNormalDataSource {

    private DynamicCreateNormalDataSource localDataSource;
    private DynamicCreateNormalDataSource remoteDataSource;

    private DynamicCreateNormalDataRepertory(
            @NonNull DynamicCreateNormalDataSource localDataSource,
            @NonNull DynamicCreateNormalDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static DynamicCreateNormalDataRepertory INSTANCE = null;

    public static DynamicCreateNormalDataRepertory getInstance(
            @NonNull DynamicCreateNormalDataSource localDataSource,
            @NonNull DynamicCreateNormalDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new DynamicCreateNormalDataRepertory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void postDynamic(
            @NonNull String Authorization, @NonNull int user_id, @NonNull String dynamic_type,
            String title, @NonNull String dcontent, String tag,
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
