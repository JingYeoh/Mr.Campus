package com.jkb.model.dataSource.circle.circleIndex;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleInfoEntity;
import com.jkb.model.intfc.BitmapLoadedCallback;

/**
 * 圈子首页的远程数据来源类
 * Created by JustKiddingBaby on 2016/8/29.
 */

public class CircleIndexDataResponsitiry implements CircleIndexDataSource {

    private static CircleIndexDataResponsitiry INSTANCE = null;
    private CircleIndexDataSource localDataSource;
    private CircleIndexDataSource remoteDataSource;


    private CircleIndexDataResponsitiry(
            CircleIndexDataSource localDataSource,
            CircleIndexDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public static CircleIndexDataResponsitiry getInstance(
            @NonNull CircleIndexDataSource localDataSource, @NonNull CircleIndexDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new CircleIndexDataResponsitiry(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getCircleInfo(
            @NonNull int userId, @NonNull int id,
            @NonNull ApiCallback<ApiResponse<CircleInfoEntity>> apiCallback) {
        remoteDataSource.getCircleInfo(userId, id, apiCallback);
    }

    @Override
    public void loadBitmapByUrl(@NonNull String url, @NonNull BitmapLoadedCallback callback) {
        remoteDataSource.loadBitmapByUrl(url, callback);
    }
}
