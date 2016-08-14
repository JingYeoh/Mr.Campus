package com.jkb.model.dataSource.create$circle;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleCreateEntity;
import com.jkb.model.base.BaseDataSource;

import okhttp3.MultipartBody;

/**
 * 创建圈子的数据来源接口
 * Created by JustKiddingBaby on 2016/8/14.
 */

public interface CircleCreateDataSource extends BaseDataSource {

    /**
     * 创建圈子
     *
     * @param user_id       必选
     * @param school_id     必选
     * @param name          必选
     * @param introduction  必选
     * @param latiude       必选
     * @param longitude     必选
     * @param authorization 必选
     * @param image         可选
     * @param flag          可选
     */
    void createCircle(
            @NonNull int user_id, @NonNull int school_id, @NonNull String name,
            @NonNull String introduction, @NonNull double latiude, @NonNull double longitude,
            @NonNull String authorization, MultipartBody.Part image, String flag,
            ApiCallback<ApiResponse<CircleCreateEntity>> apiCallback);
}
