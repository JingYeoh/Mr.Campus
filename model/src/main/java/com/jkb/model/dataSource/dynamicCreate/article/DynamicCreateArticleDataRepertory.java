package com.jkb.model.dataSource.dynamicCreate.article;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.category.CategoryTypeEntity;
import com.jkb.api.entity.dynamic.DynamicPostEntity;
import com.jkb.api.entity.image.ImageUploadEntity;

import okhttp3.MultipartBody;

/**
 * 创建文章动态的数据来源仓库类
 * Created by JustKiddingBaby on 2016/9/26.
 */

public class DynamicCreateArticleDataRepertory implements DynamicCreateArticleDataSource {

    private DynamicCreateArticleDataSource localDataSource;
    private DynamicCreateArticleDataSource remoteDataSource;

    private DynamicCreateArticleDataRepertory(
            @NonNull DynamicCreateArticleDataSource localDataSource,
            @NonNull DynamicCreateArticleDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static DynamicCreateArticleDataRepertory INSTANCE = null;

    public static DynamicCreateArticleDataRepertory getInstance(
            @NonNull DynamicCreateArticleDataSource localDataSource,
            @NonNull DynamicCreateArticleDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new DynamicCreateArticleDataRepertory(localDataSource, remoteDataSource);
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
