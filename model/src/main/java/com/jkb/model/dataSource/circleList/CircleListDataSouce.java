package com.jkb.model.dataSource.circleList;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.user.UserActionCircleEntity;

/**
 * 圈子列表数据来源接口
 * Created by JustKiddingBaby on 2016/9/2.
 */

public interface CircleListDataSouce {

    /**
     * 得到订阅的圈子
     *
     * @param user_id     用户id
     * @param visitor_id  訪客id
     * @param page        页数
     * @param apiCallback 回调方法
     */
    void subscribeCircle(
            @NonNull int user_id, int visitor_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<UserActionCircleEntity>> apiCallback);
}
