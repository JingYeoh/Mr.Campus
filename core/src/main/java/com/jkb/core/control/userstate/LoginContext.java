package com.jkb.core.control.userstate;

import android.support.annotation.NonNull;
import android.view.View;

import com.jkb.core.control.messageState.MessageObservable;
import com.jkb.model.info.UserInfoSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * 登录的状态类：单例
 * 设置其为观察者模式
 * Created by JustKiddingBaby on 2016/7/30.
 */
public class LoginContext extends Observable implements UserState {

    private UserState mState = new LogoutState();

    private static LoginContext sLoginContext = new LoginContext();

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
        update();
        //设置消息的变化
        if (isLogined()) {
            MessageObservable.newInstance().setUser_id
                    (UserInfoSingleton.getInstance().getUserAuths().getUser_id());
            MessageObservable.newInstance().setMessageUserState(true);//设置消息的状态
        } else {
            MessageObservable.newInstance().setUser_id(0);
            MessageObservable.newInstance().setMessageUserState(false);//设置消息的状态
        }
    }

    @Override
    public boolean isLogined() {
        return mState.isLogined();
    }

    @Override
    public void connectIMSuccess() {
        update();
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
        update();
    }

    /**
     * 更新
     */
    private void update() {
        setChanged();
        notifyObservers();
    }
}
