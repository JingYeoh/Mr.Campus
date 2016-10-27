package com.jkb.model.dataSource.menuRight.rightMenu.remote;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.user.UserInfoEntity;
import com.jkb.api.net.user.UserInfoApi;
import com.jkb.model.dataSource.menuRight.rightMenu.RightMenuDataSource;

import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * 右滑菜单的本地数据来源类
 * Created by JustKiddingBaby on 2016/9/5.
 */

public class RightMenuRemoteDataSource implements RightMenuDataSource {


    private RightMenuRemoteDataSource() {
    }

    private static RightMenuRemoteDataSource INSTANCE = null;

    public static RightMenuRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RightMenuRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getUserInfo(int user_id, ApiCallback<ApiResponse<UserInfoEntity>> apiCallback) {
        //获取网络数据
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        UserInfoApi userInfoApi = factory.createApi(UserInfoApi.class);
        Call<ApiResponse<UserInfoEntity>> call;
        call = userInfoApi.getUserInfo(user_id);
        Type type = new TypeToken<ApiResponse<UserInfoEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<UserInfoEntity>>(apiCallback, call, type);
    }
}
