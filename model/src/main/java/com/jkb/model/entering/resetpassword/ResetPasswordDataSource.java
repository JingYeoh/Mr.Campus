package com.jkb.model.entering.resetpassword;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.auth.ResetPasswordEntity;

import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;

/**
 * 重置密码的数据接口
 * Created by JustKiddingBaby on 2016/8/5.
 */

public interface ResetPasswordDataSource {

    /**
     * 邮箱号重置密码接口
     *
     * @param email       邮箱
     * @param credential  新密码
     * @param code        验证码
     * @param apiCallback 回调接口
     */
    void resetPasswordWithEmail(
            String email, String credential, String code,
            ApiCallback<ApiResponse<ResetPasswordEntity>> apiCallback);

    /**
     * 手机号重置密码接口
     *
     * @param phone       手机
     * @param credential  新密码
     * @param code        验证码
     * @param apiCallback 回调接口
     */
    void resetPasswordWithPhone(
            String phone, String credential, String code,
            ApiCallback<ApiResponse<ResetPasswordEntity>> apiCallback);

    /**
     * 存储Users信息到数据库中
     */
    void saveUsersToDb(Users users);

    /**
     * 存储UserAuths数据到数据库中
     */
    void saveUserAuthsToDb(UserAuths userAuths);
}
