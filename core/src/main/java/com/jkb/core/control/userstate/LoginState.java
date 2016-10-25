package com.jkb.core.control.userstate;

/**
 * 已经登录的状态单例模式
 * Created by JustKiddingBaby on 2016/7/30.
 */
public class LoginState implements UserState {

    @Override
    public boolean isLogined() {
        return true;
    }
}
