package com.jkb.model.dataSource.entering.personmessage.remote;

import android.graphics.Bitmap;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.auth.RegisterEntity;
import com.jkb.api.config.Config;
import com.jkb.api.net.auth.RegisterApi;
import com.jkb.model.dataSource.entering.personmessage.PersonMessageDataSource;

import java.lang.reflect.Type;
import java.util.Date;

import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;
import okhttp3.MultipartBody;
import retrofit2.Call;

/**
 * 上传个人数据的远程数据获取层
 * Created by JustKiddingBaby on 2016/8/1.
 */
public class PersonMessageRemoteDataSource implements PersonMessageDataSource {

    private static PersonMessageRemoteDataSource INSTANCE;

    public static PersonMessageRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PersonMessageRemoteDataSource();
        }
        return INSTANCE;
    }

    private PersonMessageRemoteDataSource() {
    }

    @Override
    public void registerWithEmail(String nickName, String code, String credential, String identity_type,
                                  String identifier, MultipartBody.Part image, ApiCallback<ApiResponse<RegisterEntity>> apiCallback) {
        //请求注册接口
        ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
        apiFactory.setHttpClient(apiFactory.filePostClient());
        apiFactory.initRetrofit();
        RegisterApi registerApi = apiFactory.createApi(RegisterApi.class);
        Call<ApiResponse<RegisterEntity>> call;
        //判断是否有需要上传的头像
        if (image == null) {
            call = registerApi.registerWithEmail(nickName, code, credential, identity_type, identifier, null, null);
        } else {
            call = registerApi.registerWithEmail(nickName, code, credential, identity_type, identifier
                    , image, Config.KEY_AVATAR);
        }
        Type type = new TypeToken<ApiResponse<RegisterEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<RegisterEntity>>(apiCallback, call, type);
    }

    @Override
    public void registerWithPhone(String nickName, String code, String credential,
                                  String identity_type, String identifier, MultipartBody.Part image,
                                  ApiCallback<ApiResponse<RegisterEntity>> apiCallback) {
        //请求注册接口
        ApiFactoryImpl apiFactory = ApiFactoryImpl.newInstance();
        apiFactory.setHttpClient(apiFactory.filePostClient());
        apiFactory.initRetrofit();
        RegisterApi registerApi = apiFactory.createApi(RegisterApi.class);
        Call<ApiResponse<RegisterEntity>> call = null;
        //判断是否有需要上传的头像
        if (image == null) {
            call = registerApi.registerWithPhone(nickName, code, credential, identity_type,
                    identifier, null, null);
        } else {
            call = registerApi.registerWithPhone(nickName, code, credential, identity_type,
                    identifier
                    , image, Config.KEY_AVATAR);
        }
        Type type = new TypeToken<ApiResponse<RegisterEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<RegisterEntity>>(apiCallback, call, type);
    }

    @Override
    public void saveUserToDb(Users users) {

    }

    @Override
    public void saveUserAuthToDb(UserAuths userAuths) {

    }


    @Override
    public void saveStatusToDb(int userId, String version, boolean isLogin, boolean isSelectedSchool,
                               int schoolId, Date date) {

    }

    @Override
    public String saveBitmapToFile(Bitmap bitmap, String path, String fileName) {
        return null;
    }

    @Override
    public Bitmap getBitmapFromFile(String urlPath) {
        return null;
    }

    @Override
    public String getCurrentVersion() {
        return null;
    }
}
