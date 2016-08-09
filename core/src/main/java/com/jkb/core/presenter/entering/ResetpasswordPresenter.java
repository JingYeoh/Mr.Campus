package com.jkb.core.presenter.entering;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.auth.LoginEntity;
import com.jkb.api.entity.auth.RegisterEntity;
import com.jkb.api.entity.auth.ResetPasswordEntity;
import com.jkb.core.contract.entering.ResetpasswordContract;
import com.jkb.model.entering.resetpassword.ResetPasswordResponsitory;
import com.jkb.model.utils.FormatUtils;
import com.jkb.model.utils.StringUtils;

import java.util.List;

import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;
import retrofit2.Response;

/**
 * 重置密码的Presenter层
 * Created by JustKiddingBaby on 2016/8/5.
 */

public class ResetpasswordPresenter implements ResetpasswordContract.Presenter {

    private ResetpasswordContract.View resetPasswordView;
    private ResetPasswordResponsitory responsitory;

    private String identity_type, credential, identifier;//账户类型，密码，帐号

    public ResetpasswordPresenter(@NonNull ResetpasswordContract.View resetPasswordView,
                                  @NonNull ResetPasswordResponsitory responsitory) {
        this.resetPasswordView = resetPasswordView;
        this.responsitory = responsitory;

        this.resetPasswordView.setPresenter(this);
    }

    @Override
    public void resetPassword(String identifyCode, String value, String password) {
        if (StringUtils.isEmpty(identifyCode, password)) {
            resetPasswordView.showReqResult("验证码或密码不能为空");
            return;
        }
        if (password.length() < 6) {
            resetPasswordView.showReqResult("密码不能少于6位");
            return;
        }
        if (!resetPasswordView.isActive()) {
            return;
        }
        identifier = value;
        credential = password;
        //判断手机号或者邮箱
        if (FormatUtils.judgePhoneFormat(value)) {
            identity_type = Config.KEY_PHONE;
            resetPasswordWithPhone(identifyCode, value, password);
        } else if (FormatUtils.judgeEmailFormat(value)) {
            identity_type = Config.KEY_EMAIL;
            resetPasswordWithEmail(identifyCode, value, password);
        } else {
            resetPasswordView.showReqResult("请输入正确的手机号或者邮箱");
            return;
        }
    }

    /**
     * 手机号重置密码
     *
     * @param identifyCode
     * @param phone
     * @param password
     */
    private void resetPasswordWithPhone(String identifyCode, String phone, String password) {
        resetPasswordView.showLoading("加载中");
        responsitory.resetPasswordWithPhone(phone, password, identifyCode, apiCallback);
    }

    /**
     * 邮箱重置密码
     *
     * @param identifyCode
     * @param email
     * @param password
     */
    private void resetPasswordWithEmail(String identifyCode, String email, String password) {
        resetPasswordView.showLoading("加载中");
        responsitory.resetPasswordWithEmail(email, password, identifyCode, apiCallback);
    }

    private ApiCallback<ApiResponse<ResetPasswordEntity>> apiCallback = new ApiCallback<ApiResponse<ResetPasswordEntity>>() {
        @Override
        public void onSuccess(Response<ApiResponse<ResetPasswordEntity>> response) {
            if (resetPasswordView.isActive()) {
                resetPasswordView.dismissLoading();
                resetPasswordView.showReqResult("重置密码成功");
            }
            //录入数据库等操作
            resetPasswordSuccess(response.body());
        }

        @Override
        public void onError(Response<ApiResponse<ResetPasswordEntity>> response, String error, ApiResponse<ResetPasswordEntity> apiResponse) {
            if (resetPasswordView.isActive()) {
                resetPasswordView.dismissLoading();
                //显示错误信息
                ResetPasswordEntity resetPasswordEntity = apiResponse.getMsg();
                if (resetPasswordEntity != null) {
                    List<String> errors = resetPasswordEntity.getIdentifier();
                    if (errors != null && errors.size() > 0) {
                        String errMsg = errors.get(0);
                        resetPasswordView.showReqResult(errMsg);
                    }
                } else {
                    resetPasswordView.showReqResult("失败了，宝宝好烦");
                }
            }
        }

        @Override
        public void onFail() {
            if (resetPasswordView.isActive()) {
                resetPasswordView.dismissLoading();
                resetPasswordView.showReqResult("请求失败");
            }
        }
    };

    /**
     * 重置密码成功
     * 处理数据库入库操作
     * 设置身份切换，切换到登录页面
     */
    private void resetPasswordSuccess(ApiResponse<ResetPasswordEntity> response) {
        //存到数据库中
        saveUsersToDb(response.getMsg());
        saveUserAuthsToDb(response.getMsg());
        if (resetPasswordView.isActive()) {
            //切换到登录页面
            resetPasswordView.resetPasswordSuccess();
        }
    }

    /**
     * 存储用户数据到数据库中
     */
    private void saveUserAuthsToDb(ResetPasswordEntity msg) {
        ResetPasswordEntity.UserInfoBean bean = msg.getUserInfo();
        if (bean == null) {
            return;
        }
        UserAuths userAuths = new UserAuths();
        userAuths.setUser_id(bean.getId());
        userAuths.setIdentity_type(identity_type);
        userAuths.setCredential(credential);
        userAuths.setIdentifier(identifier);
        userAuths.setUpdated_at(StringUtils.getSystemCurrentTime());
        responsitory.saveUserAuthsToDb(userAuths);
    }

    /**
     * 存储数据到数据库中
     */
    private void saveUsersToDb(ResetPasswordEntity msg) {
        ResetPasswordEntity.UserInfoBean bean = msg.getUserInfo();
        if (bean == null) {
            return;
        }
        Users users = new Users();
        users.setUser_id(bean.getId());
        users.setUID(bean.getUID());
        users.setNickname(bean.getNickname());
        users.setAvatar(bean.getAvatar());
        users.setSex(bean.getSex());
        users.setName(bean.getName());
        users.setBref_introduction(bean.getBref_introduction());
        users.setBackground(bean.getBackground());
        ResetPasswordEntity.UserInfoBean.SchoolInfoBean schoolInfoBean = bean.getSchoolInfo();
        if (schoolInfoBean != null) {
            users.setSchool_id(schoolInfoBean.getId());
        }
        users.setUpdated_at(StringUtils.getSystemCurrentTime());
        responsitory.saveUsersToDb(users);
    }

    @Override
    public void start() {

    }
}
