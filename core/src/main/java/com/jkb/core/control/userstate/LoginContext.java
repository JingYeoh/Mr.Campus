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

    //给侧滑菜单视图使用的对象
    private MenuPersonViewListener menuPersonViewListener;
    private View menuPersonLoginView;

    //右滑菜单的保存对象
    private SlideMenuRightListener slideMenuRightListener;

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
    public void setUserState(@NonNull UserState userState) {
        mState = userState;
        //身份状态改变的时候监听
        if (menuPersonLoginView != null && menuPersonViewListener != null) {
            setOnMenuPersonViewListener(menuPersonLoginView, menuPersonViewListener);
        }
        if (slideMenuRightListener != null) {
            setRightSlideMenuListener(slideMenuRightListener);
        }
    }

    @Override
    public boolean isLogined() {
        return mState.isLogined();
    }

    @Override
    public void setOnMenuPersonViewListener(View view, MenuPersonViewListener listener) {
        menuPersonViewListener = listener;
        menuPersonLoginView = view;
        mState.setOnMenuPersonViewListener(view, listener);
    }

    @Override
    public void setRightSlideMenuListener(SlideMenuRightListener listener) {
        slideMenuRightListener = listener;
        mState.setRightSlideMenuListener(listener);
    }
}
