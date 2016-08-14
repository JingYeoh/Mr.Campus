package com.jkb.model.dataSource.create$circle;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleCreateEntity;

import okhttp3.MultipartBody;

/**
 * 创建圈子的数据仓库类
 * Created by JustKiddingBaby on 2016/8/14.
 */

public class CircleCreateDataResponsitory implements CircleCreateDataSource {


    private static CircleCreateDataResponsitory INSTANCE = null;

    private CircleCreateDataResponsitory(
            @NonNull CircleCreateDataSource localDataSource, @NonNull CircleCreateDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public static CircleCreateDataResponsitory getInstance(
            @NonNull CircleCreateDataSource localDataSource, @NonNull CircleCreateDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new CircleCreateDataResponsitory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    private CircleCreateDataSource localDataSource = null;
    private CircleCreateDataSource remoteDataSource = null;

    @Override
    public void createCircle(@NonNull int user_id, @NonNull int school_id, @NonNull String name, @NonNull String introduction, @NonNull double latiude, @NonNull double longitude, @NonNull String authorization, MultipartBody.Part image, String flag, ApiCallback<ApiResponse<CircleCreateEntity>> apiCallback) {
        remoteDataSource.createCircle(user_id, school_id, name, introduction, latiude, longitude, authorization, image, flag, apiCallback);
    }
}
