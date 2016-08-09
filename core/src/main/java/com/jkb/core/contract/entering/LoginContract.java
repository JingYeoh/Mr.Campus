package com.jkb.core.contract.entering;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * 注册页面的页面协议类
 * Created by JustKiddingBaby on 2016/7/30.
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {

        /**
         * 设置用户名
         *
         * @param userName
         */
        void setUserName(String userName);

        /**
         * 设置密码
         *
         * @param passWord
         */
        void setPassWord(String passWord);

        /**
         * 显示登录返回的信息
         *
         * @param result
         */
        void showLoginResult(String result);

        /**
         * 通过QQ登录
         */
        void loginByQQ();

        /**
         * 通过微信登录
         */
        void loginByWeChat();

        /**
         * 通过微博登录
         */
        void loginByWeiBo();

        /**
         * 通过人人登录
         */
        void loginByRenRen();

        /**
         * 通过豆瓣登录
         */
        void loginByDouBan();

        /**
         * 通过帐号密码登录
         */
        void loginByInput();

        /**
         * 显示注册页面
         */
        void showRegister();

        /**
         * 显示菌菌协议页面
         */
        void showMrCampusAgreement();

        /**
         * 展示重置密码页面
         */
        void showResetPassWordView();

        /**
         * 登录成功
         */
        void loginSuccess();
    }

    interface Presenter extends BasePresenter {

        /**
         * 通过帐号密码登录
         *
         * @param userName
         * @param passWord
         */
        void loginByInput(String userName, String passWord);

        /**
         * 通过QQ登录
         */
        void loginByQQ();

        /**
         * 通过微信登录
         */
        void loginByWeChat();

        /**
         * 通过微博登录
         */
        void loginByWeiBo();

        /**
         * 通过人人登录
         */
        void loginByRenRen();

        /**
         * 通过豆瓣登录
         */
        void loginByDouBan();

        /**
         * 懒加载登录页面的输入框
         *
         * @param userName 用户名
         * @param passWord 密码
         */
        void lazyLoginIntput(String userName, String passWord);
    }
}
