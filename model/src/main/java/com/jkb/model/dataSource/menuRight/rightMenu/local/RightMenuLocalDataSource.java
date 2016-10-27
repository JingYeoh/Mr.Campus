package com.jkb.model.dataSource.menuRight.rightMenu.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.user.UserInfoEntity;
import com.jkb.model.dataSource.menuRight.rightMenu.RightMenuDataSource;

/**
 * 右滑菜单的本地数据来源类
 * Created by JustKiddingBaby on 2016/9/5.
 */

public class RightMenuLocalDataSource implements RightMenuDataSource {

    private Context applicationContext;

    private RightMenuLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static RightMenuLocalDataSource INSTANCE = null;

    public static RightMenuLocalDataSource getInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new RightMenuLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getUserInfo(
            @NonNull int user_id, @NonNull ApiCallback<ApiResponse<UserInfoEntity>> apiCallback) {
    }
}
