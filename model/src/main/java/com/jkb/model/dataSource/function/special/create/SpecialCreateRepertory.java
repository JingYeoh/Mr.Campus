package com.jkb.model.dataSource.function.special.create;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicPostEntity;
import com.jkb.api.entity.image.ImageUploadEntity;

import okhttp3.MultipartBody;

/**
 * 创建专题的数据仓库类
 * Created by JustKiddingBaby on 2016/11/21.
 */

public class SpecialCreateRepertory implements SpecialCreateDataSource {

    private SpecialCreateDataSource localDataSource;
    private SpecialCreateDataSource remoteDataSource;

    private SpecialCreateRepertory(SpecialCreateDataSource localDataSource,
                                   SpecialCreateDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static SpecialCreateRepertory INSTANCE = null;

    public static SpecialCreateRepertory newInstance(SpecialCreateDataSource localDataSource,
                                                     SpecialCreateDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new SpecialCreateRepertory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void postDynamic(
            @NonNull String Authorization, int user_id, @NonNull String dynamic_type,
            @NonNull String title, @NonNull String dcontent, String tag, int schoolId,
            @NonNull ApiCallback<ApiResponse<DynamicPostEntity>> apiCallback) {
        remoteDataSource.postDynamic(Authorization, user_id, dynamic_type,
                title, dcontent, tag, schoolId, apiCallback);
    }

    @Override
    public void uploadImage(
            @NonNull MultipartBody.Part image,
            @NonNull ApiCallback<ApiResponse<ImageUploadEntity>> apiCallback) {
        remoteDataSource.uploadImage(image, apiCallback);
    }
}
