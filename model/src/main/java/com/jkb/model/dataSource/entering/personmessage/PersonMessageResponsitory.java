package com.jkb.model.dataSource.entering.personmessage;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.auth.RegisterEntity;

import java.util.Date;

import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;
import okhttp3.MultipartBody;

/**
 * 个人数据获取的仓库类
 * Created by JustKiddingBaby on 2016/8/1.
 */
public class PersonMessageResponsitory implements PersonMessageDataSource {

    private PersonMessageDataSource localDataSource;//本地数据仓库
    private PersonMessageDataSource remoteDataSource;//远程数据仓库

    private static PersonMessageResponsitory INSTANCE;

    public static PersonMessageResponsitory getInstance(@NonNull PersonMessageDataSource localDataSource,
                                                        @NonNull PersonMessageDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new PersonMessageResponsitory(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    /**
     * 实例化个人数据的获取仓库类
     *
     * @param localDataSource  本地数据来源
     * @param remoteDataSource 远程数据来源
     */
    private PersonMessageResponsitory(PersonMessageDataSource localDataSource, PersonMessageDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void registerWithEmail(String nickName, String code, String credential, String identity_type, String identifier, MultipartBody.Part image, ApiCallback<ApiResponse<RegisterEntity>> apiCallback) {
        remoteDataSource.registerWithEmail(nickName, code, credential, identity_type, identifier, image, apiCallback);
    }

    @Override
    public void registerWithPhone(String nickName, String code, String credential, String identity_type, String identifier, MultipartBody.Part image, ApiCallback<ApiResponse<RegisterEntity>> apiCallback) {
        remoteDataSource.registerWithPhone(nickName, code, credential, identity_type, identifier, image, apiCallback);
    }

    @Override
    public void saveUserToDb(Users users) {
        localDataSource.saveUserToDb(users);
    }

    @Override
    public void saveUserAuthToDb(UserAuths userAuths) {
        localDataSource.saveUserAuthToDb(userAuths);
    }


    @Override
    public void saveStatusToDb(int userId, String version, boolean isLogin, Date date) {
        localDataSource.saveStatusToDb(userId, version, isLogin, date);
    }

    @Override
    public String saveBitmapToFile(final Bitmap bitmap, final String path, String fileName) {
        return localDataSource.saveBitmapToFile(bitmap, path, fileName);
    }

    @Override
    public Bitmap getBitmapFromFile(String urlPath) {
        return localDataSource.getBitmapFromFile(urlPath);
    }

    @Override
    public String getCurrentVersion() {
        return localDataSource.getCurrentVersion();
    }
}
