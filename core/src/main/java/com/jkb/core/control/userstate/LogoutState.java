package com.jkb.core.control.userstate;

import android.view.View;

/**
 * Created by JustKiddingBaby on 2016/7/30.
 */
public class LogoutState implements UserState {

    @Override
    public boolean isLogined() {
        return false;
    }

    @Override
    public void setOnMenuPersonViewListener(View view, final MenuPersonViewListener listener) {
        listener.showLogoutPersonView();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickLogoutPersonView();
                }
            }
        });
    }
}
