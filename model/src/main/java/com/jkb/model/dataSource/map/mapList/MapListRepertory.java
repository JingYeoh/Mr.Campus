package com.jkb.model.dataSource.map.mapList;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleListInSchoolEntity;
import com.jkb.api.entity.user.UserListInfoEntity;

/**
 * 地图列表附近的人的数据仓库类
 * Created by JustKiddingBaby on 2016/11/13.
 */

public class MapListRepertory implements MapListDataSource {

    private MapListDataSource localDataSource;
    private MapListDataSource remoteDataSource;

    private MapListRepertory(MapListDataSource localDataSource,
                             MapListDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    private static MapListRepertory INSTANCE = null;

    public static MapListRepertory newInstance(MapListDataSource localDataSource,
                                               MapListDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new MapListRepertory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getUserListInfo(
            String authorization, @NonNull String idArr,
            @NonNull ApiCallback<ApiResponse<UserListInfoEntity>> callback) {
        remoteDataSource.getUserListInfo(authorization, idArr, callback);
    }

    @Override
    public void getCircleListInSchool(
            String authorization, @NonNull int schoolId, int page,
            @NonNull ApiCallback<ApiResponse<CircleListInSchoolEntity>> callback) {
        remoteDataSource.getCircleListInSchool(authorization, schoolId, page, callback);
    }
}
