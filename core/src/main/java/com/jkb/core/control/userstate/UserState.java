package com.jkb.core.control.userstate;

import android.view.View;

/**
 * 用户状态的单例类接口
 * Created by JustKiddingBaby on 2016/7/30.
 */
public interface UserState {

    /**
     * 是否登录
     * true:已经登录
     * false:未登录
     *
     * @return 是否登录
     */
    boolean isLogined();

    /**
     * 登录状态变化的视图显示监听器
     */
    interface LoginStatusChangedListener {
        /**
         * 展示登录状态下
         */
        void onLogin();

        /**
         * 展示未登录状态下
         */
        void onLogout();
    }

    /**
     * 右滑菜单的信息视图监听器
     */
    interface UsersChangedListener {
        /**
         * 数据变化时候调用的方法
         */
        void onUserDataChanged();
    }

    /**
     * 设置右滑菜单信息变化时候的监听
     */
    void setRightSlideMenuDataViewChangedListener(UsersChangedListener listener);

    /**
     * 设置登录状态变化的监听器
     *
     * @param loginStatusChangedListeners 监听器
     */
    void setLoginStatusChangedListener(LoginStatusChangedListener loginStatusChangedListeners);
}
