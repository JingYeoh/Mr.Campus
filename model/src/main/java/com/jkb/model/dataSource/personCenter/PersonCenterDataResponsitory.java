package com.jkb.model.dataSource.personCenter;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.user.UserInfoEntity;
import com.jkb.model.intfc.BitmapLoadedCallback;

/**
 * 个人中心的数据仓库类
 * Created by JustKiddingBaby on 2016/8/17.
 */

public class PersonCenterDataResponsitory implements PersonCenterDataSource {

    private PersonCenterDataSource localDataSource;
    private PersonCenterDataSource remoteDataSource;

    public PersonCenterDataResponsitory(PersonCenterDataSource localDataSource, PersonCenterDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static PersonCenterDataResponsitory INSTANCE = null;

    public static PersonCenterDataResponsitory getInstance(
            @NonNull PersonCenterDataSource localDataSource, @NonNull PersonCenterDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new PersonCenterDataResponsitory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getUserInfo(String authorization, int user_id, ApiCallback<ApiResponse<UserInfoEntity>> apiCallback) {
        remoteDataSource.getUserInfo(authorization, user_id, apiCallback);
    }

    @Override
    public void loadHeadImgByUrl(String url, BitmapLoadedCallback callback) {
        remoteDataSource.loadHeadImgByUrl(url, callback);
    }

    @Override
    public void visit(@NonNull String authorization, @NonNull int user_id, @NonNull int target_id, @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {
        remoteDataSource.visit(authorization, user_id, target_id, apiCallback);
    }
}
