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
         * 显示热门的视图
         */
        void showHot();

        /**
         * 显示地图视图
         */
        void showMap();

        /**
         * 显示动态
         */
        void showMatters();
    }

    interface Presenter extends BasePresenter {

        /**
         * 显示左滑菜单
         */
        void showLeftMenu();

        /**
         * 显示右滑菜单
         */
        void showRightMenu();
    }
}
