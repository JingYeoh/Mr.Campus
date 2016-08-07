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
    public boolean isLogined();

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
    public void setOnMenuPersonViewListener(View view, MenuPersonViewListener listener);
}