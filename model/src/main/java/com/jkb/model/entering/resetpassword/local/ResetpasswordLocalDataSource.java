package com.jkb.model.entering.resetpassword.local;

import android.content.Context;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.auth.ResetPasswordEntity;
import com.jkb.model.entering.resetpassword.ResetPasswordDataSource;

import jkb.mrcampus.db.MrCampusDB;
import jkb.mrcampus.db.dao.DaoSession;
import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;

/**
 * 重置密码的本地数据来源类
 * Created by JustKiddingBaby on 2016/8/5.
 */

public class ResetpasswordLocalDataSource implements ResetPasswordDataSource {

    private static final String TAG = "重置密码本地仓库";

    private static ResetpasswordLocalDataSource INSTANCE = null;
    private Context context;

    //数据库相关
    private MrCampusDB mrCampusDB;
    private DaoSession daoSession;

    private ResetpasswordLocalDataSource(Context applicationContext) {
        context = applicationContext;
        //初始化数据库
        mrCampusDB = MrCampusDB.getInstance();
        daoSession = mrCampusDB.getDaoSession();
    }

    public static ResetpasswordLocalDataSource getInstance(
            Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new ResetpasswordLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void resetPasswordWithEmail(String email, String credential, String code, ApiCallback<ApiResponse<ResetPasswordEntity>> apiCallback) {

    }

    @Override
    public void resetPasswordWithPhone(String phone, String credential, String code, ApiCallback<ApiResponse<ResetPasswordEntity>> apiCallback) {

    }

    @Override
    public void saveUsersToDb(Users users) {
        daoSession.insertOrReplace(users);
    }

    @Override
    public void saveUserAuthsToDb(UserAuths userAuths) {
        daoSession.insertOrReplace(userAuths);
    }

}
