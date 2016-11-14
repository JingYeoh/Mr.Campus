package com.jkb.model.dataSource.map.mapList.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleListInSchoolEntity;
import com.jkb.api.entity.user.UserListInfoEntity;
import com.jkb.model.dataSource.map.mapList.MapListDataSource;

/**
 * 附近的用户的远程数据来源类
 * Created by JustKiddingBaby on 2016/11/13.
 */

public class MapListLocalDataSource implements MapListDataSource {

    private Context applicationContext;

    private MapListLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private static MapListLocalDataSource INSTANCE = null;

    public static MapListLocalDataSource newInstance(@NonNull Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new MapListLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void getUserListInfo(
            String authorization, @NonNull String idArr,
            @NonNull ApiCallback<ApiResponse<UserListInfoEntity>> callback) {
    }

    @Override
    public void getCircleListInSchool(
            String authorization, @NonNull int schoolId, int page,
            @NonNull ApiCallback<ApiResponse<CircleListInSchoolEntity>> callback) {
    }
}
