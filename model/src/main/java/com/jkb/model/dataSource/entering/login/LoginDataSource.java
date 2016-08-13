package com.jkb.model.dataSource.entering.login;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.auth.LoginEntity;

import java.util.List;

import jkb.mrcampus.db.entity.Status;
import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;

/**
 * 登录的数据仓库接口
 * Created by JustKiddingBaby on 2016/8/3.
 */
public interface LoginDataSource {

    /**
     * 第三方登录的回调接口
     */
    interface ThirdPlatformListener {
        /**
         * 成功的回调
         *
         * @param thirdPlatformData
         */
        void onSuccess(ThirdPlatformData thirdPlatformData);

        /**
         * 失败的回调
         *
         * @param value
         */
        void onFail(String value);

        /**
         * 取消登录的回调
         */
        void onCancle();
    }

    interface UserAuthsDataCallback {

        /**
         * 数据加载成功
         */
        void onUserDataLoaded(List<UserAuths> userAuthses);

        /**
         * 数据加载失败
         */
        void onDataNotAvailable();
    }

    interface BitmapDataCallback {

        /**
         * 数据加载成功
         */
        void onBitmapDataLoaded(Bitmap bitmap);

        /**
         * 数据加载失败
         */
        void onDataNotAvailable();
    }

    interface BitmapToFileDataCallback {

        /**
         * 数据加载成功
         */
        void onBitmapDataLoaded(String path);

        /**
         * 数据加载失败
         */
        void onDataNotAvailable(Bitmap bitmap);
    }

    /**
     * 通过QQ登录
     */
    void loginByQQ(ThirdPlatformListener listener);

    /**
     * 通过微信登录
     */
    void loginByWechat(ThirdPlatformListener listener);

    /**
     * 通过微博登录
     */
    void loginByWeibo(ThirdPlatformListener listener);

    /**
     * 通过人人登录
     */
    void loginByRenRen(ThirdPlatformListener listener);

    /**
     * 通过豆瓣登录
     */
    void loginByDouBan(ThirdPlatformListener listener);

    /**
     * 通过手机号密码登录
     */
    void loginWithPhone(String phone, String passWord, ApiCallback<ApiResponse<LoginEntity>> apiCallback);

    /**
     * 通过邮箱号密码登录
     */
    void loginWithEmail(String email, String passWord, ApiCallback<ApiResponse<LoginEntity>> apiCallback);


    /**
     * 第三方登录
     *
     * @param nickname      昵称
     * @param identifier    第三方登录唯一标识
     * @param identity_type 登录类型
     * @param sex           性别
     * @param avatar        头像
     * @param credential    第三方登录Token
     * @param background    背景
     * @param apiCallback   回调接口
     */
    void loginByThirdPlatform(
            @NonNull String nickname,
            @NonNull String identifier, @NonNull String identity_type,
            String sex, String avatar, String credential, String background,
            ApiCallback<ApiResponse<LoginEntity>> apiCallback);


    /**
     * 存储Users到数据库中
     */
    void saveUserToDb(Users users);

    /**
     * 存储UserAuths到数据库中
     */
    void saveUserAuthsToDb(UserAuths userAuths);

    /**
     * 保存Status到数据库中
     */
    void saveStatusToDb(Status status);

    /**
     * 从数据库中取出数据
     */
    void getUserAuthsDataFromDb(@NonNull UserAuthsDataCallback callback);

    /**
     * 得到当前版本号
     */
    String getCurrentVersion();

    /**
     * 加载Url为Bitmap
     */
    void getBitmapFromUrl(String url, BitmapDataCallback callback);

    /**
     * Bitmap缓存为图片的方法
     */
    void cacheBitmapToFile(String path, String name, Bitmap bitmap, @NonNull BitmapToFileDataCallback callback);
}
