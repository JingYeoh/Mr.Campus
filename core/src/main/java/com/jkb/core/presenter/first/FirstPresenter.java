package com.jkb.core.presenter.first;

import android.support.annotation.NonNull;

import com.jkb.core.contract.first.FirstContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LoginState;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.model.first.firstlogic.FirstData;
import com.jkb.model.first.firstlogic.FirstDataResponsitory;
import com.jkb.model.first.firstlogic.FirstDataSource;
import com.jkb.model.utils.StringUtils;

import jkb.mrcampus.db.entity.Status;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * FirstActivity控制器的逻辑Presenter类
 * Created by JustKiddingBaby on 2016/7/22.
 */
public class FirstPresenter implements FirstContract.Presenter {

    private FirstDataResponsitory firstDataResponsitory;
    private FirstContract.View firstView;

    /**
     * 初始化Presenter并且绑定View
     *
     * @param firstDataResponsitory
     * @param firstView
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
        firstDataResponsitory.getStatusData(statusDataCallback);
    }

    FirstDataSource.StatusDataCallback statusDataCallback = new FirstDataSource.StatusDataCallback() {
        @Override
        public void onStatusDataLoaded(Status status) {
            boolean isLogin = status.getFlag_login();
            String cacheVersion = status.getVersion();
            String currentVersion = firstDataResponsitory.getCurrentVersion();
            if (cacheVersion.equals(currentVersion)) {
                showWelcome();
            } else {
                firstDataResponsitory.cacheStatus(currentVersion, status.getFlag_login(), status.getUser_id(),
                        StringUtils.getSystemCurrentTime());
                showGuide();
            }
            //设置为未登录状态
            if (isLogin) {
                LoginContext loginContext = LoginContext.getInstance();
                loginContext.setUserState(new LoginState());
            } else {
                LoginContext loginContext = LoginContext.getInstance();
                loginContext.setUserState(new LogoutState());
            }
        }

        @Override
        public void onDataNotAvailable() {
            firstView.showFragment(1);
            //设置状态为未登录状态
            firstDataResponsitory.cacheStatus(firstDataResponsitory.getCurrentVersion(), false, 0, StringUtils.getSystemCurrentTime());
            LoginContext loginContext = LoginContext.getInstance();
            loginContext.setUserState(new LogoutState());
        }
    };

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
