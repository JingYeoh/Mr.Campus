package com.jkb.core.presenter.entering;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.auth.RegisterEntity;
import com.jkb.api.entity.auth.ResetPasswordEntity;
import com.jkb.core.contract.entering.ResetpasswordContract;
import com.jkb.model.entering.resetpassword.ResetPasswordResponsitory;
import com.jkb.model.utils.FormatUtils;
import com.jkb.model.utils.StringUtils;

import java.util.List;

import retrofit2.Response;

/**
 * 重置密码的Presenter层
 * Created by JustKiddingBaby on 2016/8/5.
 */

public class ResetpasswordPresenter implements ResetpasswordContract.Presenter {

    private ResetpasswordContract.View resetPasswordView;
    private ResetPasswordResponsitory responsitory;

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
        if (!resetPasswordView.isActive()) {
            return;
        }
        //判断手机号或者邮箱
        if (FormatUtils.judgePhoneFormat(value)) {
            resetPasswordWithPhone(identifyCode, value, password);
        } else if (FormatUtils.judgeEmailFormat(value)) {
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
        responsitory.resetPasswordWithEmail(email, password, identifyCode, apiCallback);
    }

    private ApiCallback<ApiResponse<ResetPasswordEntity>> apiCallback = new ApiCallback<ApiResponse<ResetPasswordEntity>>() {
        @Override
        public void onSuccess(Response<ApiResponse<ResetPasswordEntity>> response) {
            resetPasswordView.dismissLoading();
            resetPasswordView.showReqResult("重置密码成功");
            //录入数据库等操作
            resetPasswordSuccess(response.body());
        }

        @Override
        public void onError(Response<ApiResponse<ResetPasswordEntity>> response, String error, ApiResponse<ResetPasswordEntity> apiResponse) {
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

        @Override
        public void onFail() {
            resetPasswordView.dismissLoading();
            resetPasswordView.showReqResult("请求失败");
        }
    };

    /**
     * 重置密码成功
     * 处理数据库入库操作
     * 设置身份切换，切换到登录页面
     *
     * @param response
     */
    private void resetPasswordSuccess(ApiResponse<ResetPasswordEntity> response) {
        responsitory.saveEntityToDb(response.getMsg());//数据库入库操作
        //切换到登录页面
        resetPasswordView.resetPasswordSuccess();
    }

    @Override
    public void start() {

    }
}
