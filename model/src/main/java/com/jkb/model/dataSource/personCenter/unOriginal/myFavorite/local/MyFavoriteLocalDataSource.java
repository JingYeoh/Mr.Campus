package com.jkb.model.dataSource.personCenter.unOriginal.myFavorite.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.dynamic.DynamicMyFavoriteEntity;
import com.jkb.api.entity.operation.OperationActionEntity;

/**
 * 我的普通数据的数据仓库类
 * Created by JustKiddingBaby on 2016/10/14.
 */

public class MyFavoriteLocalDataSource implements com.jkb.model.dataSource.personCenter.unOriginal.myFavorite.MyFavoriteDataSource {

    private Context applicationContext;

    private MyFavoriteLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static MyFavoriteLocalDataSource INSTANCE = null;

    public static MyFavoriteLocalDataSource getInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new MyFavoriteLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getMyFavoriteAllDynamic(
            String Authorization, int user_id, @NonNull int ownerId, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicMyFavoriteEntity>> apiCallback) {

    }

    @Override
    public void getMyFavoriteNormalDynamic(
            String Authorization, int user_id, @NonNull int ownerId, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicMyFavoriteEntity>> apiCallback) {

    }

    @Override
    public void getMyFavoriteTopicDynamic(
            String Authorization, int user_id, @NonNull int ownerId, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicMyFavoriteEntity>> apiCallback) {

    }

    @Override
    public void getMyFavoriteArticleDynamic(
            String Authorization, int user_id, @NonNull int ownerId, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicMyFavoriteEntity>> apiCallback) {

    }

    @Override
    public void deleteDynamic(
            @NonNull String Authorization, @NonNull int dynamic_id, @NonNull int operator_id,
            @NonNull ApiCallback<ApiResponse<DynamicActionEntity>> apiCallback) {

    }

    @Override
    public void favorite(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {

    }
}
