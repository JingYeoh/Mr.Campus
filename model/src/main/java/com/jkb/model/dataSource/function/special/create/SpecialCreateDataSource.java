package com.jkb.model.dataSource.function.special.create;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicPostEntity;
import com.jkb.api.entity.image.ImageUploadEntity;

import okhttp3.MultipartBody;

/**
 * 专题创建的数据来源接口
 * Created by JustKiddingBaby on 2016/11/21.
 */

public interface SpecialCreateDataSource {

    /**
     * 发送动态
     *
     * @param Authorization 头
     * @param user_id       用户id
     * @param dynamic_type  动态标签类型
     *                      允许值: "topic", "normal", "article"
     * @param title         动态标题.
     * @param dcontent      动态内容.
     * @param tag           主题标签（仅发表话题动态时需要此项）.
     * @param apiCallback   回调
     */
    void postDynamic(
            @NonNull String Authorization, int user_id,
            @NonNull String dynamic_type, @NonNull String title,
            @NonNull String dcontent, String tag, int schoolId,
            @NonNull ApiCallback<ApiResponse<DynamicPostEntity>> apiCallback);

    /**
     * 上传图片
     *
     * @param image       图片
     * @param apiCallback 回调
     */
    void uploadImage(@NonNull MultipartBody.Part image,
                     @NonNull ApiCallback<ApiResponse<ImageUploadEntity>> apiCallback);
}
