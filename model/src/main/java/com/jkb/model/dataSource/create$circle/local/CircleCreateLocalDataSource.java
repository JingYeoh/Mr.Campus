package com.jkb.model.dataSource.create$circle.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleCreateEntity;
import com.jkb.model.dataSource.create$circle.CircleCreateDataSource;

import okhttp3.MultipartBody;

/**
 * 创建圈子的本地数据来源类
 * Created by JustKiddingBaby on 2016/8/14.
 */

public class CircleCreateLocalDataSource implements CircleCreateDataSource {

    private static CircleCreateLocalDataSource INSTANCE = null;
    private Context applicationContext;

    private CircleCreateLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static CircleCreateLocalDataSource getInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new CircleCreateLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void createCircle(@NonNull int user_id, @NonNull int school_id, @NonNull String name, @NonNull String introduction, @NonNull double latiude, @NonNull double longitude, @NonNull String authorization, MultipartBody.Part image, String flag, ApiCallback<ApiResponse<CircleCreateEntity>> apiCallback) {

    }
}
