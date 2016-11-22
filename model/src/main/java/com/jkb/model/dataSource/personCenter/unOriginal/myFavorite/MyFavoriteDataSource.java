package com.jkb.model.dataSource.personCenter.unOriginal.myFavorite;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.dynamic.DynamicMyFavoriteEntity;
import com.jkb.api.entity.operation.OperationActionEntity;

/**
 * 我的普通动态的数据接口
 * Created by JustKiddingBaby on 2016/10/14.
 */

public interface MyFavoriteDataSource {

    /**
     * 得到我喜欢的所有的动态
     *
     * @param Authorization 头，可选
     * @param user_id       访客id，可选
     * @param ownerId       用户id，必选
     * @param page          分页数据
     * @param apiCallback   回调
     */
    void getMyFavoriteAllDynamic(
            String Authorization, int user_id, @NonNull int ownerId, @NonNull int page,
                @NonNull ApiCallback<ApiResponse<DynamicMyFavoriteEntity>> apiCallback);

    /**
     * 得到我喜欢的普通的动态
     *
     * @param Authorization 头，可选
     * @param user_id       访客id，可选
     * @param ownerId       用户id，必选
     * @param page          分页数据
     * @param apiCallback   回调
     */
    void getMyFavoriteNormalDynamic(
            String Authorization, int user_id, @NonNull int ownerId, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicMyFavoriteEntity>> apiCallback);

    /**
     * 得到我喜欢的话题动态
     *
     * @param Authorization 头，可选
     * @param user_id       访客id，可选
     * @param ownerId       用户id，必选
     * @param page          分页数据
     * @param apiCallback   回调
     */
    void getMyFavoriteTopicDynamic(
            String Authorization, int user_id, @NonNull int ownerId, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicMyFavoriteEntity>> apiCallback);

    /**
     * 得到我喜欢的文章动态
     *
     * @param Authorization 头，可选
     * @param user_id       访客id，可选
     * @param ownerId       用户id，必选
     * @param page          分页数据
     * @param apiCallback   回调
     */
    void getMyFavoriteArticleDynamic(
            String Authorization, int user_id, @NonNull int ownerId, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicMyFavoriteEntity>> apiCallback);

    /**
     * 删除动态
     *
     * @param Authorization 头，必选
     * @param dynamic_id    动态id
     * @param operator_id   操作者id
     * @param apiCallback   回调
     */
    void deleteDynamic(@NonNull String Authorization, @NonNull int dynamic_id, @NonNull int operator_id,
                       @NonNull ApiCallback<ApiResponse<DynamicActionEntity>> apiCallback);

    /**
     * 喜欢/点赞动态
     *
     * @param Authorization token
     * @param user_id       用户id
     * @param target_id     目标id
     * @param apiCallback   回调
     */
    void favorite(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback);
}
