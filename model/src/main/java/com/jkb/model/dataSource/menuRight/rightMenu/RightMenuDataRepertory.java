package com.jkb.model.dataSource.menuRight.rightMenu;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.user.UserInfoEntity;

/**
 * 右滑菜单的数据来源仓库类
 * Created by JustKiddingBaby on 2016/9/5.
 */

public class RightMenuDataRepertory implements RightMenuDataSource {

    private RightMenuDataSource localDataSource;
    private RightMenuDataSource remoteDataSource;

    private RightMenuDataRepertory(RightMenuDataSource localDataSource,
                                   RightMenuDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static RightMenuDataRepertory INSTANCE = null;

    public static RightMenuDataRepertory getInstance(
            @NonNull RightMenuDataSource localDataSource,
            @NonNull RightMenuDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new RightMenuDataRepertory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getUserInfo(
            @NonNull int user_id, @NonNull ApiCallback<ApiResponse<UserInfoEntity>> apiCallback) {
        remoteDataSource.getUserInfo(user_id, apiCallback);
    }
}