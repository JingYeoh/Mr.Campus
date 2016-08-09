package com.jkb.model.entering.login.local;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.auth.LoginEntity;
import com.jkb.model.entering.login.LoginDataSource;
import com.jkb.model.utils.BitmapUtils;
import com.jkb.model.utils.StringUtils;
import com.jkb.model.utils.SystemUtils;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import jkb.mrcampus.db.MrCampusDB;
import jkb.mrcampus.db.dao.DaoSession;
import jkb.mrcampus.db.dao.UserAuthsDao;
import jkb.mrcampus.db.entity.Status;
import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 登录的本地数据来源类
 * Created by JustKiddingBaby on 2016/8/3.
 */
public class LoginLocalDataSource implements LoginDataSource {

    private Context applicationContext;
    private static LoginLocalDataSource INSTANCE;

    //数据库
    private MrCampusDB mrCampusDB;
    private DaoSession daoSession;

    public static LoginLocalDataSource getInstance(Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new LoginLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    private LoginLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;

        mrCampusDB = MrCampusDB.getInstance();
        daoSession = mrCampusDB.getDaoSession();
    }

    @Override
    public void loginByQQ(ThirdPlatformListener listener) {

    }

    @Override
    public void loginByWechat(ThirdPlatformListener listener) {

    }

    @Override
    public void loginByWeibo(ThirdPlatformListener listener) {

    }

    @Override
    public void loginByRenRen(ThirdPlatformListener listener) {

    }

    @Override
    public void loginByDouBan(ThirdPlatformListener listener) {

    }

    @Override
    public void loginWithPhone(String phone, String passWord, ApiCallback<ApiResponse<LoginEntity>> apiCallback) {

    }

    @Override
    public void loginWithEmail(String email, String passWord, ApiCallback<ApiResponse<LoginEntity>> apiCallback) {

    }


    @Override
    public void loginByThirdPlatform(
            @NonNull String nickname, @NonNull String identifier,
            @NonNull String identity_type, String sex, String avatar,
            String credential, String background, ApiCallback<ApiResponse<LoginEntity>> apiCallback) {

    }

    @Override
    public void saveUserToDb(Users users) {
        daoSession.insertOrReplace(users);
    }

    @Override
    public void saveUserAuthsToDb(UserAuths userAuths) {
        daoSession.insertOrReplace(userAuths);
    }

    @Override
    public void saveStatusToDb(Status status) {
        daoSession.insertOrReplace(status);
    }

    @Override
    public void getUserAuthsDataFromDb(UserAuthsDataCallback callback) {
        UserAuthsDao userAuthsDao = daoSession.getUserAuthsDao();
        QueryBuilder qb = userAuthsDao.queryBuilder();
        qb.whereOr(UserAuthsDao.Properties.Identity_type.eq(Config.KEY_PHONE),
                UserAuthsDao.Properties.Identity_type.eq(Config.KEY_EMAIL));
        List<UserAuths> userAuthses = qb.list();
        if (userAuthses == null || userAuthses.size() == 0) {
            callback.onDataNotAvailable();
        } else {
            callback.onUserDataLoaded(userAuthses);
        }
    }

    @Override
    public String getCurrentVersion() {
        try {
            String currentVersion = SystemUtils.getCurrentVersion(applicationContext);
            return currentVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void getBitmapFromUrl(String url, BitmapDataCallback callback) {

    }

    @Override
    public void cacheBitmapToFile(String path, String name, final Bitmap bitmap, final BitmapToFileDataCallback callback) {
        if (bitmap == null) {
            callback.onDataNotAvailable(bitmap);
            return;
        }
        if (StringUtils.isEmpty(path)) {
            path = Config.PATH_ROOT_IMAGE;
        }
        if (StringUtils.isEmpty(name)) {
            name = BitmapUtils.getSystemTime();
        }
        //缓存图片
        final String finalName = name;
        final String finalPath = path;
        Observable.just(bitmap)
                .map(new Func1<Bitmap, String>() {
                    @Override
                    public String call(Bitmap bitmap) {
                        String pathUrl = BitmapUtils.saveBitmapWithName(finalPath, finalName, bitmap);
                        return pathUrl;
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String pathUrl) {
                        if (StringUtils.isEmpty(pathUrl)) {
                            callback.onDataNotAvailable(bitmap);
                        } else {
                            callback.onBitmapDataLoaded(pathUrl);
                        }
                    }
                });
    }
}
