package com.jkb.model.dataSource.circleList.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.user.UserActionCircleEntity;
import com.jkb.model.dataSource.circleList.CircleListDataSouce;

/**
 * 圈子列表的本地数据来源类
 * Created by JustKiddingBaby on 2016/9/2.
 */

public class CircleListLocalDatasource implements CircleListDataSouce {

    private Context applicationContext;

    private CircleListLocalDatasource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static CircleListLocalDatasource INSTANCE = null;

    public static CircleListLocalDatasource getInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new CircleListLocalDatasource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void subscribeCircle(
            @NonNull int user_id, int visitor_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<UserActionCircleEntity>> apiCallback) {

    }
}
