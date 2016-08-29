package com.jkb.model.dataSource.personSetting;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.user.UserInfoEntity;
import com.jkb.api.entity.user.UserUpdateEntity;
import com.jkb.model.intfc.BitmapLoadedCallback;

import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;
import okhttp3.MultipartBody;

/**
 * 个人设置的数据来源类
 * Created by JustKiddingBaby on 2016/8/24.
 */

public interface PersonSettingDataSource {

    /**
     * 存储users表到数据库中
     */
    void saveUsersToDb(Users users);

    /**
     * 存储userAuths表到数据库中
     */
    void saveUserAuthsToDb(UserAuths userAuths);

    /**
     * 得到用户信息
     *
     * @param user_id     用户id
     * @param apiCallback 回调方法
     */
    void getUserInfo(
            @NonNull int user_id,
            @NonNull ApiCallback<ApiResponse<UserInfoEntity>> apiCallback);

    /**
     * 通过网络加载图片
     */
    void loadBitmapByUrl(@NonNull String url, @NonNull BitmapLoadedCallback callback);

    /**
     * 上传头像
     *
     * @param Authorization 头token
     * @param id            用户id
     * @param image         图片文件
     * @param apiCallback   回调
     */
    void uploadHeadImg(
            @NonNull String Authorization,
            @NonNull int id,
            @NonNull MultipartBody.Part image,
            @NonNull ApiCallback<ApiResponse<UserUpdateEntity>> apiCallback);

    /**
     * 上传背景
     *
     * @param Authorization 头token
     * @param id            用户id
     * @param image         图片文件
     * @param apiCallback   回调
     */
    void uploadBackGround(
            @NonNull String Authorization,
            @NonNull int id,
            @NonNull MultipartBody.Part image,
            @NonNull ApiCallback<ApiResponse<UserUpdateEntity>> apiCallback);

    /**
     * 修改用户资料
     *
     * @param Authorization Token
     * @param id            用户id
     * @param column        所修改用户信息的类型.
     * @param value         修改的信息
     * @param apiCallback   ApiCallback
     */
    void updateUserInfo(
            @NonNull String Authorization, @NonNull int id,
            @NonNull String column, @NonNull String value,
            @NonNull ApiCallback<ApiResponse<UserUpdateEntity>> apiCallback);
}
