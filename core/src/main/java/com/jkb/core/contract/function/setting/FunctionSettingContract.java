package com.jkb.core.contract.function.setting;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * 功能设置的页面协议类
 * Created by JustKiddingBaby on 2016/10/8.
 */

public interface FunctionSettingContract {

    interface View extends BaseView<Presenter> {

        /**
         * 显示登录了的视图
         */
        void showLoginView();

        /**
         * 显示登出的页面视图
         */
        void showLogoutView();

        /**
         * 设置缓存的大小
         *
         * @param cacheSize 缓存大小
         */
        void setCacheSize(double cacheSize);

        /**
         * 设置系统的版本号
         *
         * @param currentSystemVision 当前的系统版本号
         */
        void setCurrentSystemVision(String currentSystemVision);

        /**
         * 显示关于软件页面
         */
        void showAboutSoftwareView();

        /**
         * 显示分享的页面
         */
        void showShareView();

        /**
         * 显示提示框提醒是否清楚缓存
         */
        void showHintDetermineView();

        /**
         * 清楚缓存
         */
        void clearCache();

        /**
         * 显示登录的页面
         */
        void startLoginActivity();

        /**
         * 打开关于菌菌页面
         */
        void startAboutSoft();

        /**
         * 打開常見問題
         */
        void startCommonQuestion();
    }

    interface Presenter extends BasePresenter {

        /**
         * 初始化登录状态
         */
        void initLoginStatus();

        /**
         * 设置缓存的状态
         */
        void initCacheStatus();

        /**
         * 退出登录被点击的时候
         */
        void onLogin$LogoutClick();

        /**
         * 清除缓存被点击的时候
         */
        void onClearCacheClick();
    }
}
