package com.jkb.core.control.userstate;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * 登录的状态类：单例
 * Created by JustKiddingBaby on 2016/7/30.
 */
public class LoginContext implements UserState {

    private UserState mState = new LogoutState();

    private static LoginContext sLoginContext = new LoginContext();

    private LoginContext() {
    }

    /**
     * 返回当前对象的单例实例
     *
     * @return
     */
    public static LoginContext getInstance() {
        return sLoginContext;
    }

    /**
     * 设置登录状态
     *
     * @param userState
     */
    private void setUserState(@NonNull UserState userState) {
        mState = userState;
    }

    @Override
    public boolean isLogined() {
        return mState.isLogined();
    }

    @Override
    public void setOnMenuPersonViewListener(View view, MenuPersonViewListener listener) {
        mState.setOnMenuPersonViewListener(view, listener);
    }
}
