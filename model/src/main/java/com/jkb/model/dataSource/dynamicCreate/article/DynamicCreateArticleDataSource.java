package com.jkb.model.dataSource.dynamicCreate.article;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.category.CategoryTypeEntity;
import com.jkb.api.entity.dynamic.DynamicPostEntity;
import com.jkb.api.entity.image.ImageUploadEntity;

import okhttp3.MultipartBody;

/**
 * 创建动态：文章的数据来源类
 * Created by JustKiddingBaby on 2016/9/26.
 */

public interface DynamicCreateArticleDataSource {

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
            @NonNull String Authorization, @NonNull int user_id,
            @NonNull String dynamic_type, @NonNull String title,
            @NonNull String dcontent, String tag,
            @NonNull ApiCallback<ApiResponse<DynamicPostEntity>> apiCallback);

    /**
     * 上传图片
     *
     * @param image       图片
     * @param apiCallback 回调
     */
    void uploadImage(@NonNull MultipartBody.Part image,
                     @NonNull ApiCallback<ApiResponse<ImageUploadEntity>> apiCallback);

    /**
     * 获取所有标签
     *
     * @param type        类型
     *                    允许值: "topic", "article"
     * @param apiCallback 回调
     */
    void getAllTag(@NonNull String type,
                   @NonNull ApiCallback<ApiResponse<CategoryTypeEntity>> apiCallback);
}
