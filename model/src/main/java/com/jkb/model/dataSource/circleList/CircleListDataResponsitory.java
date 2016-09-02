package com.jkb.model.dataSource.circleList;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.user.UserActionCircleEntity;

/**
 * 圈子列表的数据仓库类
 * Created by JustKiddingBaby on 2016/9/2.
 */

public class CircleListDataResponsitory implements CircleListDataSouce {


    private CircleListDataSouce localDataSouce;
    private CircleListDataSouce remoteDataSouce;

    private CircleListDataResponsitory(CircleListDataSouce localDataSouce,
                                       CircleListDataSouce remoteDataSouce) {
        this.localDataSouce = localDataSouce;
        this.remoteDataSouce = remoteDataSouce;
    }

    private static CircleListDataResponsitory INSTANCE = null;

    public static CircleListDataResponsitory getInstance(
            @NonNull CircleListDataSouce localDataSouce,
            @NonNull CircleListDataSouce remoteDataSouce) {
        if (INSTANCE == null) {
            INSTANCE = new CircleListDataResponsitory(localDataSouce, remoteDataSouce);
        }
        return INSTANCE;
    }

    @Override
    public void subscribeCircle(
            @NonNull int user_id, int visitor_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<UserActionCircleEntity>> apiCallback) {
        remoteDataSouce.subscribeCircle(user_id, visitor_id, page, apiCallback);
    }
}
