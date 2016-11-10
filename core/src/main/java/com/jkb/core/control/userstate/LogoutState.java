package com.jkb.core.control.userstate;

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
    public void connectIMSuccess() {

    }
}
