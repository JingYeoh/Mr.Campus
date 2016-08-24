package com.jkb.model.dataSource.personSetting.remote;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.user.UserInfoEntity;
import com.jkb.api.entity.user.UserUpdateEntity;
import com.jkb.api.net.user.UserInfoApi;
import com.jkb.api.net.user.UserUpdateApi;
import com.jkb.model.dataSource.personSetting.PersonSettingDataSource;
import com.jkb.model.intfc.BitmapLoadedCallback;
import com.jkb.model.net.ImageLoaderFactory;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.lang.reflect.Type;

import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;
import okhttp3.MultipartBody;
import retrofit2.Call;

/**
 * 个人设置的远程数据来源
 * Created by JustKiddingBaby on 2016/8/24.
 */

public class PersonSettingRemoteDataSource implements PersonSettingDataSource {


    private PersonSettingRemoteDataSource() {
    }

    public static PersonSettingRemoteDataSource INSTANCE = null;

    public static PersonSettingRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PersonSettingRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void saveUsersToDb(Users users) {
    }

    @Override
    public void saveUserAuthsToDb(UserAuths userAuths) {

    }

    @Override
    public void getUserInfo(@NonNull int user_id,
                            @NonNull ApiCallback<ApiResponse<UserInfoEntity>> apiCallback) {
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

    @Override
    public void loadBitmapByUrl(
            @NonNull final String url, @NonNull final BitmapLoadedCallback callback) {
        ImageLoaderFactory.getInstance().loadImage(url, null, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
//                callback.onDataNotAvailable(url);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                callback.onDataNotAvailable(url);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                callback.onBitmapDataLoaded(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                callback.onDataNotAvailable(url);
            }
        });
    }

    @Override
    public void uploadHeadImg(
            @NonNull String Authorization, @NonNull int id,
            @NonNull MultipartBody.Part image,
            @NonNull ApiCallback<ApiResponse<UserUpdateEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.filePostClient());
        factory.initRetrofit();
        UserUpdateApi userUpdateApi = factory.createApi(UserUpdateApi.class);
        Call<ApiResponse<UserUpdateEntity>> call;
        call = userUpdateApi.updateImage(Authorization, id, image, Config.FLAG_AVATAR);
        Type type = new TypeToken<ApiResponse<UserUpdateEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<UserUpdateEntity>>(apiCallback, call, type);
    }

    @Override
    public void uploadBackGround(
            @NonNull String Authorization, @NonNull int id,
            @NonNull MultipartBody.Part image,
            @NonNull ApiCallback<ApiResponse<UserUpdateEntity>> apiCallback) {
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.filePostClient());
        factory.initRetrofit();
        UserUpdateApi userUpdateApi = factory.createApi(UserUpdateApi.class);
        Call<ApiResponse<UserUpdateEntity>> call;
        call = userUpdateApi.updateImage(Authorization, id, image, Config.FLAG_BACKGROUND);
        Type type = new TypeToken<ApiResponse<UserUpdateEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<UserUpdateEntity>>(apiCallback, call, type);
    }

}
