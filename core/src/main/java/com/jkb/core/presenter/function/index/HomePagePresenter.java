package com.jkb.core.presenter.function.index;

import android.support.annotation.NonNull;

import com.jkb.core.contract.function.index.HomePageContract;
import com.jkb.core.contract.menu.MenuContract;
import com.jkb.core.control.userstate.LoginContext;

/**
 * HomePageFragment的Presenter层
 * Created by JustKiddingBaby on 2016/7/25.
 */
public class HomePagePresenter implements HomePageContract.Presenter {

    private HomePageContract.View homePageView;
    private MenuContract.View menuView;

    public HomePagePresenter(@NonNull HomePageContract.View homePageView,
                             @NonNull MenuContract.View menuView) {
        this.homePageView = homePageView;
        this.menuView = menuView;

        this.homePageView.setPresenter(this);
    }

    @Override
    public void start() {
        //判断身份用来展示视图
        judgeStatusToShowView();
    }

    @Override
    public void judgeStatusToShowView() {
        //判断是否登录，设置标题栏右侧的按钮视图
        if (LoginContext.getInstance().isLogined()) {
            homePageView.setLoginRightMenuView();
        } else {
            homePageView.setLogoutRightMenuView();
        }
    }
}
