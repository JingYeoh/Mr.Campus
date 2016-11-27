package com.jkb.model.dataSource.search.detail;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.search.SearchEntity;

/**
 * 搜索详情的数据来源接口
 * Created by JustKiddingBaby on 2016/11/26.
 */

public interface SearchDetailDataSource {

    /**
     * 根据关键字搜索
     *
     * @param Authorization 可选
     * @param type          类型
     * @param keyWords      关键字
     * @param page          分页
     * @param apiCallback   回调
     */
    void searchByKeyWords(
            String Authorization, @NonNull String type, @NonNull String keyWords, int page,
            @NonNull ApiCallback<ApiResponse<SearchEntity>> apiCallback);

    /**
     * 关注或者取消关注用户接口
     *
     * @param Authorization 包含token的头部
     * @param user_id       用户id
     * @param target_id     目标id
     */
    void payAttentionOrCancel(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback);
}
