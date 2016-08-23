package com.jkb.core.control.userstate;

import android.view.View;

/**
 * 已经登录的状态单例模式
 * Created by JustKiddingBaby on 2016/7/30.
 */
public class LoginState implements UserState {

    @Override
    public boolean isLogined() {
        return true;
    }

    @Override
    public void setOnMenuPersonViewListener(View view, final MenuPersonViewListener listener) {
        if (listener == null) {
            return;
        }
        listener.showLoginPersonView();
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickLoginPersonView();
                }
            });
        }
    }

    @Override
    public void setLoginStatusChangedShowViewListener(LoginStatusChangedShowViewListener listener) {
        if (listener != null) {
            listener.showLoginView();
        }
    }

    @Override
    public void setRightSlideMenuDataViewChangedListener(UsersChangedListener listener) {
        if (listener != null) {
            listener.onUserDataChanged();
        }
    }

}
