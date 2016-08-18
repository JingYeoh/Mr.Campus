package com.jkb.model.dataSource.personCenter;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.user.UserInfoEntity;
import com.jkb.model.intfc.BitmapLoadedCallback;

/**
 * 个人中心的数据来源接口
 * Created by JustKiddingBaby on 2016/8/17.
 */

public interface PersonCenterDataSource {

    /**
     * 得到用户数据
     *
     * @param authorization 头，包含token字段
     * @param user_id       用户id
     * @param apiCallback   回调
     */
    void getUserInfo(@NonNull String authorization, @NonNull int user_id,
                     @NonNull ApiCallback<ApiResponse<UserInfoEntity>> apiCallback);

    /**
     * 通过网络加载头像
     */
    void loadHeadImgByUrl(@NonNull String url, @NonNull BitmapLoadedCallback callback);
}
