package com.jkb.core.control.userstate;

import android.view.View;

/**
 * 未登录状态的单例类
 * Created by JustKiddingBaby on 2016/7/30.
 */
public class LogoutState implements UserState {

    @Override
    public boolean isLogined() {
        return false;
    }

    @Override
    public void setOnMenuPersonViewListener(View view, final MenuPersonViewListener listener) {
        if (listener == null) {
            return;
        }
        listener.showLogoutPersonView();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickLogoutPersonView();
            }
        });
    }

    @Override
    public void setRightSlideMenuListener(SlideMenuRightListener listener) {
        if (listener != null) {
            listener.showLogoutRightMenuView();
        }
    }
}
