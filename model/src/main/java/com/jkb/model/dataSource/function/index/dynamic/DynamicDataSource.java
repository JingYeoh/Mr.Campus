package com.jkb.model.dataSource.function.index.dynamic;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicListEntity;

/**
 * 首页——动态：数据来源接口类
 * Created by JustKiddingBaby on 2016/9/3.
 */

public interface DynamicDataSource {


    /**
     * 获取全部动态信息
     *
     * @param Authorization token
     * @param page          分页数据
     * @param apiCallback   回调
     */
    void getAllDynamic(
            @NonNull String Authorization, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicListEntity>> apiCallback);
}
