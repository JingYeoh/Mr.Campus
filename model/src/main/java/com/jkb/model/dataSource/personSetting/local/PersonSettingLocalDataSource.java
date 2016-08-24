package com.jkb.model.dataSource.personSetting.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.user.UserInfoEntity;
import com.jkb.api.entity.user.UserUpdateEntity;
import com.jkb.model.dataSource.personSetting.PersonSettingDataSource;
import com.jkb.model.intfc.BitmapLoadedCallback;

import jkb.mrcampus.db.MrCampusDB;
import jkb.mrcampus.db.dao.DaoSession;
import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;
import okhttp3.MultipartBody;

/**
 * 个人设置的本地数据来源
 * Created by JustKiddingBaby on 2016/8/24.
 */

public class PersonSettingLocalDataSource implements PersonSettingDataSource {

    private Context applicationContext;
    //数据库
    private MrCampusDB mrCampusDB;
    private DaoSession daoSession;

    private PersonSettingLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;

        mrCampusDB = MrCampusDB.getInstance();
        daoSession = mrCampusDB.getDaoSession();
    }

    public static PersonSettingLocalDataSource INSTANCE = null;

    public static PersonSettingLocalDataSource getInstance(Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new PersonSettingLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void saveUsersToDb(Users users) {
        daoSession.insertOrReplace(users);
    }

    @Override
    public void saveUserAuthsToDb(UserAuths userAuths) {
        daoSession.insertOrReplace(userAuths);
    }

    @Override
    public void getUserInfo(@NonNull int user_id,
                            @NonNull ApiCallback<ApiResponse<UserInfoEntity>> apiCallback) {

    }

    @Override
    public void loadBitmapByUrl(@NonNull String url, @NonNull BitmapLoadedCallback callback) {

    }

    @Override
    public void uploadHeadImg(
            @NonNull String Authorization, @NonNull int id, @NonNull MultipartBody.Part image,
            @NonNull ApiCallback<ApiResponse<UserUpdateEntity>> apiCallback) {

    }

    @Override
    public void uploadBackGround(
            @NonNull String Authorization, @NonNull int id, @NonNull MultipartBody.Part image,
            @NonNull ApiCallback<ApiResponse<UserUpdateEntity>> apiCallback) {

    }
}
