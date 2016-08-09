package com.jkb.core.contract.function.homepage;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * HomePageFragment的页面协议类
 * Created by JustKiddingBaby on 2016/7/25.
 */
public interface HomePagecontract {

    interface View extends BaseView<Presenter> {

        /**
         * 显示左滑菜单视图
         */
        void showLeftMenu();

        /**
         * 显示右滑菜单视图
         */
        void showRightMenu();

        /**
         * 设置已经登录的右滑菜单的视图
         */
        void setLoginRightMenuView();

        /**
         * 设置未登录的右滑菜单的视图
         */
        void setLogoutRightMenuView();

        /**
         * 显示热门的视图
         */
        void showHot();


        /**
         * 显示动态
         */
        void showMatters();
    }

    interface Presenter extends BasePresenter {

    }
}
