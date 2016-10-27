package com.jkb.model.dataSource.menuRight.rightMenu;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.user.UserInfoEntity;

/**
 * 右滑菜单的数据来源类
 * Created by JustKiddingBaby on 2016/9/5.
 */

public interface RightMenuDataSource {
    /**
     * 得到用户数据
     *
     * @param user_id     用户id
     * @param apiCallback 回调
     */
    void getUserInfo(@NonNull int user_id,
                     @NonNull ApiCallback<ApiResponse<UserInfoEntity>> apiCallback);
}
