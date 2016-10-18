package com.jkb.model.dataSource.myDynamic.circleSelector.local;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.user.UserActionCircleEntity;
import com.jkb.model.dataSource.myDynamic.circleSelector.CircleSelectorDataSource;

/**
 * 圈子选择的本地数据来源类
 * Created by JustKiddingBaby on 2016/10/18.
 */

public class CircleSelectorLocalDataSource implements CircleSelectorDataSource {

    private Context applicationContext;

    private CircleSelectorLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static CircleSelectorLocalDataSource INSTANCE = null;

    public static CircleSelectorLocalDataSource newInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new CircleSelectorLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void subscribeCircle(
            @NonNull int user_id, int visitor_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<UserActionCircleEntity>> apiCallback) {

    }
}
