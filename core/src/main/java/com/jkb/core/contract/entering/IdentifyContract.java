package com.jkb.core.contract.entering;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * 发送验证码页面的协议类
 * Created by JustKiddingBaby on 2016/7/31.
 */
public interface IdentifyContract {

    interface View extends BaseView<Presenter> {

        /**
         * 显示加载效果
         *
         * @param value
         */
        void showLoading(String value);

        /**
         * 取消加载效果
         */
        void dismissLoading();

        /**
         * 发送验证码
         */
        void sendCode();

        /**
         * 发送验证码成功
         */
        void sendCodeSuccess();

        /**
         * 不允许发送验证码
         */
        void notAllowToSendCode();

        /**
         * 显示应该显示的下一个页面
         */
        void showNextView();

        /**
         * 显示菌菌协议页面
         */
        void showMrCampusAgreement();

        /**
         * 显示请求后的结果
         *
         * @param msg
         */
        void showReqResult(String msg);

        /**
         * 是否被销毁
         *
         * @return
         */
        boolean isActive();
    }

    interface Presenter extends BasePresenter {

        /**
         * 发送邮箱验证码
         *
         * @param email
         */
        void sendEmail(String email);

        /**
         * 发送手机号验证码
         *
         * @param phone
         */
        void sendPhone(String phone);

        /**
         * 发送验证码
         *
         * @param value
         */
        void sendIdentifyCode(String value);
    }
}
