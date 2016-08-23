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
     * @return
     */
    boolean isLogined();

    /**
     * 展示个人信息视图接口
     */
    interface MenuPersonViewListener {
        /**
         * 展示登录状态下个人信息的视图
         */
        void showLoginPersonView();

        /**
         * 展示未登录状态下的个人信息视图
         */
        void showLogoutPersonView();

        /**
         * 登录状态下的个人视图的回调方法
         */
        void onClickLoginPersonView();

        /**
         * 未登录状态下的个人视图的回调方法
         */
        void onClickLogoutPersonView();
    }

    /**
     * 设置左滑菜单的个人信息的监听器
     */
    void setOnMenuPersonViewListener(View view, MenuPersonViewListener listener);

    /**
     * 登录状态变化的视图显示监听器
     */
    interface LoginStatusChangedShowViewListener {
        /**
         * 展示登录状态下的顯示視圖
         */
        void showLoginView();

        /**
         * 展示未登录状态下的顯示視圖
         */
        void showLogoutView();
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
     * 设置右滑菜单的监听器
     *
     * @param listener 监听器
     */
    void setLoginStatusChangedShowViewListener(LoginStatusChangedShowViewListener listener);

    /**
     * 设置右滑菜单信息变化时候的监听
     *
     * @param listener
     */
    void setRightSlideMenuDataViewChangedListener(UsersChangedListener listener);
}
