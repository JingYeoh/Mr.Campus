package com.jkb.core.presenter.entering;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.auth.IdentifyCodeEntity;
import com.jkb.core.contract.entering.IdentifyContract;
import com.jkb.model.dataSource.entering.identify.IdentifyCodeResponsitory;
import com.jkb.model.utils.FormatUtils;

import java.util.List;

import retrofit2.Response;

/**
 * 获取验证码页面的Presenter层
 * Created by JustKiddingBaby on 2016/7/31.
 */
public class IdentifyPresenter implements IdentifyContract.Presenter {

    private static final String TAG = "IdentifyPresenter";
    private IdentifyContract.View identifyView;
    private IdentifyCodeResponsitory identifyCodeResponsitory;

    public IdentifyPresenter(@NonNull IdentifyContract.View identifyView,
                             @NonNull IdentifyCodeResponsitory identifyCodeResponsitory) {
        this.identifyView = identifyView;
        this.identifyCodeResponsitory = identifyCodeResponsitory;

        this.identifyView.setPresenter(this);
    }

    @Override
    public void sendEmail(String email) {
        if (!identifyView.isActive()) {
            return;
        }
        identifyView.showLoading("");
        identifyCodeResponsitory.sendEmail(email, getIdentifyCode);
    }


    @Override
    public void sendPhone(String phone) {
        if (!identifyView.isActive()) {
            return;
        }
        identifyView.showLoading("");
        identifyCodeResponsitory.sendPhone(phone, getIdentifyCode);
    }

    @Override
    public void sendIdentifyCode(String value) {
        if (value == null || value.trim().isEmpty()) {
            identifyView.showReqResult("输入内容不能为空！");
            return;
        }
        //判断是手机号还是邮箱
        if (FormatUtils.judgeEmailFormat(value)) {
            sendEmail(value);
        } else if (FormatUtils.judgePhoneFormat(value)) {//判断是否为手机号
            sendPhone(value);
        } else {
            identifyView.showReqResult("请输入正确的手机号或邮箱");
            return;
        }
    }

    @Override
    public void start() {

    }

    private ApiCallback<ApiResponse<IdentifyCodeEntity>> getIdentifyCode =
            new ApiCallback<ApiResponse<IdentifyCodeEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<IdentifyCodeEntity>> response) {
                    identifyView.dismissLoading();
                    identifyView.showReqResult("验证码发送成功");
                    identifyView.sendCodeSuccess();
                    identifyView.showNextView();//加载个人中心页面
                }

                @Override
                public void onError(Response<ApiResponse<IdentifyCodeEntity>> response, String error,
                                    ApiResponse<IdentifyCodeEntity> apiResponse) {

                    Log.d(TAG, "-----error--->" + error);
                    identifyView.dismissLoading();
                    identifyView.showReqResult(error);
                   /* IdentifyCodeEntity identifyCodeEntity = apiResponse.getMsg();
                    if (identifyCodeEntity != null) {
                        List<String> emailErrors = identifyCodeEntity.getEmail();
                        if (emailErrors != null && emailErrors.size() > 0) {
                            String errMsg = emailErrors.get(0);
                            identifyView.showReqResult(errMsg);
                        }
                    } else {
                        identifyView.showReqResult("验证码发送失败");
                    }*/
                }

                @Override
                public void onFail() {
                    Log.d(TAG, "请求失败~");
                    identifyView.dismissLoading();
                    identifyView.showReqResult("请求失败");
                }
            };
}
