package com.jkb.model.dataSource.circle.circleIndex;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleInfoEntity;
import com.jkb.model.intfc.BitmapLoadedCallback;

/**
 * 圈子首页的数据来源接口
 * Created by JustKiddingBaby on 2016/8/29.
 */

public interface CircleIndexDataSource {

    /**
     * 得到圈子数据
     *
     * @param userId      用户id
     * @param id          圈子id
     * @param apiCallback 回调
     */
    void getCircleInfo(
            @NonNull int userId, @NonNull int id,
            @NonNull ApiCallback<ApiResponse<CircleInfoEntity>> apiCallback);

    /**
     * 通过网络加载图片
     */
    void loadBitmapByUrl(@NonNull String url, @NonNull BitmapLoadedCallback callback);
}
