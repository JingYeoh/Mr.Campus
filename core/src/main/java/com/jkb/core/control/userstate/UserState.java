package com.jkb.core.control.userstate;

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
     * 连接融云成功
     */
    void connectIMSuccess();
}
