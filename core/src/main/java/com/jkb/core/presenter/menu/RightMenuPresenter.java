package com.jkb.core.presenter.menu;

import com.jkb.core.contract.menu.RightMenuContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.core.control.userstate.UserState;
import com.jkb.model.info.UserInfoSingleton;

import jkb.mrcampus.db.entity.Users;

/**
 * 右滑菜单的P层
 * Created by JustKiddingBaby on 2016/8/16.
 */

public class RightMenuPresenter implements RightMenuContract.Presenter {

    private RightMenuContract.View view;

    public RightMenuPresenter(RightMenuContract.View view) {
        this.view = view;

        this.view.setPresenter(this);
    }

    @Override
    public void getCountData() {
        //设置计数的数据
        UserInfoSingleton info = UserInfoSingleton.getInstance();
        Users users = info.getUsers();
        if (users == null) {
            //切换身份
            LoginContext.getInstance().setUserState(new LogoutState());
            return;
        }
        if (view.isActive()) {
            int attentionUserCount = (users.getAttentionUserCount() == null ? 0 :
                    users.getAttentionUserCount());
            int fansCount = (users.getFansCount() == null ? 0 : users.getFansCount());
            int visitorCount = (users.getVisitorCount() == null ? 0 : users.getVisitorCount());
            view.setAttentionCount(attentionUserCount);
            view.setFansCount(fansCount);
            view.setVisitorCount(visitorCount);
        }
    }

    @Override
    public void setOnUsersDataChangedListener(UserState.UsersChangedListener listener) {
        LoginContext.getInstance().setRightSlideMenuDataViewChangedListener(listener);
    }

    @Override
    public int getUser_id() {
        UserInfoSingleton info = UserInfoSingleton.getInstance();
        Users users = info.getUsers();
        return users.getUser_id();
    }

    @Override
    public void start() {
        setOnUsersDataChangedListener(view.onUserDataChangedListener());
    }
}
