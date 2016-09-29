package com.jkb.model.dataSource.entering.personmessage.local;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.auth.RegisterEntity;
import com.jkb.model.dataSource.entering.personmessage.PersonMessageDataSource;
import com.jkb.model.utils.BitmapUtils;
import com.jkb.model.utils.SystemUtils;

import java.util.Date;

import jkb.mrcampus.db.MrCampusDB;
import jkb.mrcampus.db.dao.DaoSession;
import jkb.mrcampus.db.entity.Status;
import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;
import okhttp3.MultipartBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 获取个人数据的本地数据获取类
 * Created by JustKiddingBaby on 2016/8/1.
 */
public class PersonMessageLocalDataSource implements PersonMessageDataSource {


    private static PersonMessageLocalDataSource INSTANCE;
    //数据库相关
    private MrCampusDB mrCampusDB;

    private DaoSession daoSession;
    private Context context;

    public static PersonMessageLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PersonMessageLocalDataSource(context);
        }
        return INSTANCE;
    }

    private PersonMessageLocalDataSource(Context context) {
        this.context = context;
        //初始化数据库
        mrCampusDB = MrCampusDB.getInstance();
        daoSession = mrCampusDB.getDaoSession();
    }

    @Override
    public void registerWithEmail(String nickName, String code, String credential,
                                  String identity_type, String identifier, MultipartBody.Part image,
                                  ApiCallback<ApiResponse<RegisterEntity>> apiCallback) {

    }

    @Override
    public void registerWithPhone(
            String nickName, String code, String credential,
            String identity_type, String identifier, MultipartBody.Part image,
            ApiCallback<ApiResponse<RegisterEntity>> apiCallback) {
    }

    @Override
    public void saveUserToDb(Users user) {
        daoSession.insertOrReplace(user);
    }

    @Override
    public void saveUserAuthToDb(UserAuths userAuths) {
        daoSession.insertOrReplace(userAuths);
    }

    @Override
    public void saveStatusToDb(int userId, String version, boolean isLogin,
                               boolean isSelectedSchool, int schoolId, Date date) {
        Status status = new Status();
        status.setUser_id(userId);
        status.setVersion(version);
        status.setFlag_login(isLogin);
        status.setFlag_select_school(isSelectedSchool);
        status.setSchool_id(schoolId);
        status.setCreated_at(date);
        daoSession.insertOrReplace(status);
    }

    @Override
    public String saveBitmapToFile(Bitmap bitmap, String path, String fileName) {
        final String[] url = {null};
        if (fileName == null) {
            fileName = BitmapUtils.getSystemTime();
        }
        if (path == null) {
            path = Config.PATH_ROOT_IMAGE;
        }
        final String finalPath = path;
        final String finalFileName = fileName;
        //保存图片
        Observable.just(bitmap)
                .map(new Func1<Bitmap, String>() {
                    @Override
                    public String call(Bitmap bitmap) {
                        String url = BitmapUtils.saveBitmapWithName(finalPath, finalFileName, bitmap);
                        return url;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        url[0] = s;
                    }
                });
        return url[0];
    }

    @Override
    public Bitmap getBitmapFromFile(final String urlPath) {

        if (urlPath == null) {
            return null;
        }

        final Bitmap[] mBitmap = {null};

        Observable.just(urlPath)
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                        Bitmap bitmap = BitmapUtils.getCompressedImage(urlPath);
                        return bitmap;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        mBitmap[0] = bitmap;
                    }
                });

        return mBitmap[0];
    }

    /**
     * 得到当前的版本
     *
     * @return
     */
    @Override
    public String getCurrentVersion() {
        try {
            String currentVersion = SystemUtils.getCurrentVersion(context);
            return currentVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
