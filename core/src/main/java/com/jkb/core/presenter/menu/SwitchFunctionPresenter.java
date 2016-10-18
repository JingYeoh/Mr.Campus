package com.jkb.core.presenter.menu;

import android.support.annotation.NonNull;

import com.jkb.core.contract.menu.MenuContract;
import com.jkb.core.contract.menu.SwitchFunctionContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.core.control.userstate.UserState;
import com.jkb.model.info.SchoolInfoSingleton;
import com.jkb.model.info.UserInfoSingleton;

import jkb.mrcampus.db.entity.Schools;
import jkb.mrcampus.db.entity.Users;

/**
 * 左滑菜单的Presenter层
 * Created by JustKiddingBaby on 2016/7/24.
 */
public class SwitchFunctionPresenter implements SwitchFunctionContract.Presenter {


    private SwitchFunctionContract.View functionView;//左滑菜单的视图

    /**
     * 得到Presenter对象
     */
    public SwitchFunctionPresenter(@NonNull SwitchFunctionContract.View functionView) {
        this.functionView = functionView;
        //向View层中注入Presenter层对象
        this.functionView.setPresenter(this);
    }

    @Override
    public void start() {
        getCurrentSchool();
        getPersonalData();
    }


    @Override
    public boolean isLogined() {
        return LoginContext.getInstance().isLogined();
    }

    @Override
    public void getCurrentSchool() {
        //设置当前学校视图
        initSchoolView();
    }

    /**
     * 初始化学校视图
     */
    private void initSchoolView() {
        SchoolInfoSingleton schoolInfo = SchoolInfoSingleton.getInstance();
        if (schoolInfo.isSelectedSchool()) {
            Schools school = schoolInfo.getSchool();
            functionView.showSchoolView(school.getSchool_name(),
                    school.getBadge(), school.getSummary());
        } else {
            functionView.hideSchoolView();
        }
    }

    @Override
    public void getPersonalData() {
        if (LoginContext.getInstance().isLogined()) {
            functionView.showLoginView();
        } else {
            functionView.showLogoutView();
        }
    }

    @Override
    public String getCurrentNickName() {
        Users users = UserInfoSingleton.getInstance().getUsers();
        if (users != null) {
            return users.getNickname();
        }
        return null;
    }

    @Override
    public String getCurrentHeadImg() {
        //得到本地头像
        return UserInfoSingleton.getInstance().getUsers().getAvatar();
    }

    @Override
    public int getUser_id() {
        int user_id = -1;
        UserInfoSingleton info = UserInfoSingleton.getInstance();
        Users users = info.getUsers();
        if (users == null) {
            LoginContext.getInstance().setUserState(new LogoutState());
        } else {
            user_id = users.getUser_id();
        }
        return user_id;
    }

    @Override
    public void onCircleListClick() {
        if (!LoginContext.getInstance().isLogined()) {
            functionView.showReqResult("请先去登录再进行操作");
            return;
        }
        functionView.startCircleList(getUser_id());
    }

    @Override
    public void onMyFavoriteClick() {
        if (!LoginContext.getInstance().isLogined()) {
            functionView.showReqResult("请先去登录再进行操作");
            return;
        }
        functionView.startMyFavoriteActivity(getUser_id());
    }

    @Override
    public void onUserViewClick() {
        if (LoginContext.getInstance().isLogined()) {
            functionView.startPersonCenterActivity(getUser_id());
        } else {
            functionView.startLoginActivity();
        }
    }
}
