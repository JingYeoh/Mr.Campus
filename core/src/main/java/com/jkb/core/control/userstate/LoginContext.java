package com.jkb.core.control.userstate;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录的状态类：单例
 * Created by JustKiddingBaby on 2016/7/30.
 */
public class LoginContext implements UserState {

    private UserState mState = new LogoutState();

    private static LoginContext sLoginContext = new LoginContext();

    //右滑菜单数据变化时候的监听器
    private UsersChangedListener slideRightDataViewListener;

    private List<LoginStatusChangedListener> loginStatusChangedListeners;

    private LoginContext() {
    }

    /**
     * 返回当前对象的单例实例
     */
    public static LoginContext getInstance() {
        return sLoginContext;
    }

    /**
     * 设置登录状态
     */
    public void setUserState(@NonNull UserState userState) {
        mState = userState;
        //身份状态改变的时候监听
        if (slideRightDataViewListener != null) {
            setRightSlideMenuDataViewChangedListener(slideRightDataViewListener);
        }
        onLoginStatusChanged();
    }

    @Override
    public boolean isLogined() {
        return mState.isLogined();
    }


    @Override
    public void setRightSlideMenuDataViewChangedListener(UsersChangedListener listener) {
        slideRightDataViewListener = listener;
        mState.setRightSlideMenuDataViewChangedListener(slideRightDataViewListener);
    }

    @Override
    public void setLoginStatusChangedListener(
            LoginStatusChangedListener loginStatusChangedListener) {
        if (loginStatusChangedListeners == null) {
            loginStatusChangedListeners = new ArrayList<>();
        }
        loginStatusChangedListeners.add(loginStatusChangedListener);
        onLoginStatusChanged();
    }

    /**
     * 登录状态变化时候调用
     */
    private void onLoginStatusChanged() {
        if (loginStatusChangedListeners == null || loginStatusChangedListeners.size() <= 0) {
            return;
        }
        for (LoginStatusChangedListener loginStatusChangedListener : loginStatusChangedListeners) {
            if (isLogined()) {
                loginStatusChangedListener.onLogin();
            } else {
                loginStatusChangedListener.onLogout();
            }
        }
    }
}
