package com.jkb.core.presenter.first;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.core.contract.first.FirstContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LoginState;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.model.dataSource.first.firstlogic.FirstDataResponsitory;
import com.jkb.model.dataSource.first.firstlogic.FirstDataSource;
import com.jkb.model.info.SchoolInfoSingleton;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.StringUtils;

import jkb.mrcampus.db.entity.Schools;
import jkb.mrcampus.db.entity.Status;
import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * FirstActivity控制器的逻辑Presenter类
 * Created by JustKiddingBaby on 2016/7/22.
 */
public class FirstPresenter implements FirstContract.Presenter {

    private FirstDataResponsitory firstDataResponsitory;
    private FirstContract.View firstView;

    private static final String TAG = "FirstPresenter";

    /**
     * 初始化Presenter并且绑定View
     */
    public FirstPresenter(@NonNull FirstDataResponsitory firstDataResponsitory, @NonNull FirstContract.View firstView) {
        this.firstDataResponsitory = checkNotNull(firstDataResponsitory, "firstDataResponsitory不能为空");
        this.firstView = checkNotNull(firstView, "firstView不能为空");

        this.firstView.setPresenter(this);
    }

    @Override
    public void start() {
        chooseFragment();
    }

    @Override
    public void chooseFragment() {
        //得到状态表的数据
        firstDataResponsitory.getStatusData(statusDataCallback);
    }

    /**
     * 得到状态表对象的回调
     */
    FirstDataSource.StatusDataCallback statusDataCallback = new FirstDataSource.StatusDataCallback() {
        @Override
        public void onStatusDataLoaded(Status status) {
            boolean isLogin = status.getFlag_login();
            boolean isSelectedSchool = status.getFlag_select_school();
            String cacheVersion = status.getVersion();
            String currentVersion = firstDataResponsitory.getCurrentVersion();
            if (cacheVersion.equals(currentVersion)) {
                showWelcome();
            } else {
                firstDataResponsitory.cacheStatus(currentVersion, isLogin,
                        isSelectedSchool, status.getSchool_id(), status.getUser_id(),
                        StringUtils.getSystemCurrentTime());
                showGuide();
            }
            //设置选择的学校的状态表
            if (isSelectedSchool) {
                //设置得到的学校信息
                int school_id = status.getSchool_id();
                SchoolInfoSingleton.getInstance().setSelectedSchool(true);
                getSelectedSchool_id(school_id);//得到选择的学校id
            } else {
                SchoolInfoSingleton.getInstance().setSelectedSchool(false);
            }
            //设置为未登录状态
            if (isLogin) {
                //得到个人信息并设置到个人的信息单例类中
                getUsersData(status.getUser_id());
            } else {//设置为已登录状态
                LoginContext.getInstance().setUserState(new LogoutState());
            }
        }

        @Override
        public void onDataNotAvailable() {
            Log.d(TAG, "getStatusData-------->>没有得到数据");
            firstView.showFragment(1);
            //设置状态为未登录状态
            firstDataResponsitory.cacheStatus(firstDataResponsitory.getCurrentVersion(), false,
                    false, 0, 0,
                    StringUtils.getSystemCurrentTime());
            //设置为未登录在状态
            LoginContext.getInstance().setUserState(new LogoutState());
        }
    };

    /**
     * 得到选择的学校id
     */
    private void getSelectedSchool_id(int school_id) {
        Log.d(TAG, "getSelectedSchool_id--------->schoolId=" + school_id);
        Schools school = firstDataResponsitory.getSchoolFromDb(school_id);
        Log.d(TAG, "getSelectedSchool_id--------->school=" + school);
        if (school == null) {
            //设置为没有选择学校
            SchoolInfoSingleton.getInstance().setSelectedSchool(false);
        } else {
            Log.d(TAG, "getSelectedSchool_id--------->getSchool_id=" + school.getSchool_id());
            if (school.getSchool_id() == null) {
                SchoolInfoSingleton.getInstance().setSelectedSchool(false);
                return;
            }
            if (school.getSchool_id() <= 0) {
                SchoolInfoSingleton.getInstance().setSelectedSchool(false);
                return;
            }
            SchoolInfoSingleton.getInstance().setSchool(school);
            SchoolInfoSingleton.getInstance().setSelectedSchool(true);
        }
    }

    /**
     * 得到个人的信息
     */
    private void getUsersData(int userId) {
        firstDataResponsitory.getUsersData(userId, new FirstDataSource.UsersDataCallback() {
            @Override
            public void onUserDataLoaded(Users users) {
                UserInfoSingleton.getInstance().setUsers(users);
                getUserAuthData(users.getUser_id());
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG, "getUsersData-->onDataNotAvailable-------->>没有得到数据");
                LoginContext.getInstance().setUserState(new LogoutState());
                firstDataResponsitory.cacheStatus(
                        firstDataResponsitory.getCurrentVersion(), false, false, 0, 0,
                        StringUtils.getSystemCurrentTime());//缓存为未登录
            }
        });
    }

    /**
     * 得到userAuth数据
     */
    private void getUserAuthData(int userId) {
        firstDataResponsitory.getUserAuthData(userId, new FirstDataSource.UserAuthsDataCallback() {
            @Override
            public void onUserDataLoaded(UserAuths userAuths) {
                UserInfoSingleton.getInstance().setUserAuths(userAuths);
                LoginContext.getInstance().setUserState(new LoginState());
            }

            @Override
            public void onDataNotAvailable() {//设置为未登录状态
                Log.d(TAG, "getUserAuthData-->onDataNotAvailable-------->>没有得到数据");
                LoginContext.getInstance().setUserState(new LogoutState());
                firstDataResponsitory.cacheStatus(
                        firstDataResponsitory.getCurrentVersion(), false,
                        false, 0, 0, StringUtils.getSystemCurrentTime());//缓存为未登录
            }
        });
    }

    /**
     * 显示引导页面
     */
    private void showGuide() {
        firstView.showFragment(1);
    }

    /**
     * 显示欢迎页面
     */
    private void showWelcome() {
        firstView.showFragment(0);
    }
}
