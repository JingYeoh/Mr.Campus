package com.jkb.model.dataSource.function.special.create.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicPostEntity;
import com.jkb.api.entity.image.ImageUploadEntity;
import com.jkb.model.dataSource.function.special.create.SpecialCreateDataSource;

import okhttp3.MultipartBody;

/**
 * 创建专题的远程数据来源类
 * Created by JustKiddingBaby on 2016/11/21.
 */

public class SpecialCreateLocalDataSource implements SpecialCreateDataSource {

    private Context applicationContext;

    private SpecialCreateLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static SpecialCreateLocalDataSource INSTANCE = null;

    public static SpecialCreateLocalDataSource newInstance(Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new SpecialCreateLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void postDynamic(
            @NonNull String Authorization, int user_id, @NonNull String dynamic_type,
            @NonNull String title, @NonNull String dcontent, String tag, int schoolId,
            @NonNull ApiCallback<ApiResponse<DynamicPostEntity>> apiCallback) {
    }

    @Override
    public void uploadImage(
            @NonNull MultipartBody.Part image,
            @NonNull ApiCallback<ApiResponse<ImageUploadEntity>> apiCallback) {
    }
}
