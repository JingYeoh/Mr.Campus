package com.jkb.model.dataSource.personCenter.personCenter.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.operation.OperationVerifyPayAttentionEntity;
import com.jkb.api.entity.user.UserActionCircleEntity;
import com.jkb.api.entity.user.UserInfoEntity;
import com.jkb.model.dataSource.personCenter.personCenter.PersonCenterDataSource;
import com.jkb.model.intfc.BitmapLoadedCallback;

/**
 * 个人中心的本地数据来源类
 * Created by JustKiddingBaby on 2016/8/17.
 */

public class PersonCenterLocalDataSource implements PersonCenterDataSource {


    private Context applicationContext;
    private static PersonCenterLocalDataSource INSTANCE;

    private PersonCenterLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static PersonCenterLocalDataSource getInstance(Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new PersonCenterLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getUserInfo(
            @NonNull int user_id,
            @NonNull ApiCallback<ApiResponse<UserInfoEntity>> apiCallback) {

    }

    @Override
    public void loadHeadImgByUrl(@NonNull String url, @NonNull BitmapLoadedCallback callback) {

    }

    @Override
    public void visit(
            @NonNull String authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {

    }

    @Override
    public void subscribeCircle(
            @NonNull int user_id, int visitor_id, @NonNull int page,
            @NonNull ApiCallback<ApiResponse<UserActionCircleEntity>> apiCallback) {

    }

    @Override
    public void verifyIfPayAttention(
            @NonNull int user_id, @NonNull int visitor_id,
            @NonNull ApiCallback<ApiResponse<OperationVerifyPayAttentionEntity>> apiCallback) {

    }

    @Override
    public void payAttentionOrCancle(
            @NonNull String Authorization, @NonNull int user_id, @NonNull int target_id,
            @NonNull ApiCallback<ApiResponse<OperationActionEntity>> apiCallback) {

    }
}
