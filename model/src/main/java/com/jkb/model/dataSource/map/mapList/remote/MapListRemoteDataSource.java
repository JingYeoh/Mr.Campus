package com.jkb.model.dataSource.map.mapList.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleListInSchoolEntity;
import com.jkb.api.entity.user.UserListInfoEntity;
import com.jkb.api.net.circle.CircleInfoApi;
import com.jkb.api.net.user.UserInfoApi;
import com.jkb.model.dataSource.map.mapList.MapListDataSource;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 附近的用户的远程数据来源类
 * Created by JustKiddingBaby on 2016/11/13.
 */

public class MapListRemoteDataSource implements MapListDataSource {

    private static MapListRemoteDataSource INSTANCE = null;

    private MapListRemoteDataSource() {
    }

    public static MapListRemoteDataSource newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MapListRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getUserListInfo(
            String authorization, @NonNull String idArr,
            @NonNull ApiCallback<ApiResponse<UserListInfoEntity>> callback) {
        //获取网络数据
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        UserInfoApi userInfoApi = factory.createApi(UserInfoApi.class);
        Call<ApiResponse<UserListInfoEntity>> call;
        call = userInfoApi.getUserListInfo(idArr);
        Type type = new TypeToken<ApiResponse<UserListInfoEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<UserListInfoEntity>>(callback, call, type);
    }

    @Override
    public void getCircleListInSchool(
            String authorization, @NonNull int schoolId, int page,
            @NonNull ApiCallback<ApiResponse<CircleListInSchoolEntity>> callback) {
        //获取网络数据
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        CircleInfoApi circleInfoApi = factory.createApi(CircleInfoApi.class);
        Call<ApiResponse<CircleListInSchoolEntity>> call;
        call = circleInfoApi.getCircleListInSchool(authorization, schoolId, page);
        Type type = new TypeToken<ApiResponse<CircleListInSchoolEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<CircleListInSchoolEntity>>(callback, call, type);
    }
}
