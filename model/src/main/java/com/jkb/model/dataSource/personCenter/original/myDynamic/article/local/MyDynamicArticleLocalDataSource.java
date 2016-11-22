package com.jkb.model.dataSource.personCenter.original.myDynamic.article.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.dynamic.DynamicArticleListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.model.dataSource.personCenter.original.myDynamic.article.MyDynamicArticleDataSource;

/**
 * 我的普通数据的数据仓库类
 * Created by JustKiddingBaby on 2016/10/14.
 */

public class MyDynamicArticleLocalDataSource implements MyDynamicArticleDataSource {

    private Context applicationContext;

    private MyDynamicArticleLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static MyDynamicArticleLocalDataSource INSTANCE = null;

    public static MyDynamicArticleLocalDataSource getInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new MyDynamicArticleLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getMyDynamicArticle(
            String Authorization, @NonNull int user_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<DynamicArticleListEntity>> apiCallback) {

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
