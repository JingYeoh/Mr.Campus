package com.jkb.model.dataSource.dynamicCreate.normal.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.category.CategoryTypeEntity;
import com.jkb.api.entity.dynamic.DynamicPostEntity;
import com.jkb.api.entity.image.ImageUploadEntity;
import com.jkb.model.dataSource.dynamicCreate.normal.DynamicCreateNormalDataSource;

import okhttp3.MultipartBody;

/**
 * 创建话题动态的数据本地数据来源类
 * Created by JustKiddingBaby on 2016/9/26.
 */

public class DynamicCreateNormalLocalDataSource implements DynamicCreateNormalDataSource {


    private Context applicationContext;

    public DynamicCreateNormalLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static DynamicCreateNormalLocalDataSource INSTANCE = null;

    public static DynamicCreateNormalLocalDataSource getInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new DynamicCreateNormalLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void postDynamic(
            @NonNull String Authorization, @NonNull int user_id, @NonNull String dynamic_type,
            String title, @NonNull String dcontent, String tag,
            @NonNull ApiCallback<ApiResponse<DynamicPostEntity>> apiCallback) {

    }

    @Override
    public void uploadImage(
            @NonNull MultipartBody.Part image,
            @NonNull ApiCallback<ApiResponse<ImageUploadEntity>> apiCallback) {

    }

    @Override
    public void getAllTag(
            @NonNull String type,
            @NonNull ApiCallback<ApiResponse<CategoryTypeEntity>> apiCallback) {
    }
}
