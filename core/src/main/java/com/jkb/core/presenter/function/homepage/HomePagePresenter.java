package com.jkb.core.presenter.function.homepage;

import android.support.annotation.NonNull;

import com.jkb.core.contract.function.homepage.HomePagecontract;
import com.jkb.core.contract.menu.MenuContract;
import com.jkb.core.control.userstate.LoginContext;

/**
 * HomePageFragment的Presenter层
 * Created by JustKiddingBaby on 2016/7/25.
 */
public class HomePagePresenter implements HomePagecontract.Presenter {

    private HomePagecontract.View homePageView;
    private MenuContract.View menuView;

    public HomePagePresenter(@NonNull HomePagecontract.View homePageView,
                             @NonNull MenuContract.View menuView) {
        this.homePageView = homePageView;
        this.menuView = menuView;

        this.homePageView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
