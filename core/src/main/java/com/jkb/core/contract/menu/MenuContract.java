package com.jkb.core.contract.menu;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * MainActivity的页面协议类
 * Created by JustKiddingBaby on 2016/7/24.
 */
public interface MenuContract {


    /**
     * MainActivity的View层接口
     */
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
         * 隐藏菜单视图
         */
        void hideMenu();

        /**
         * 设置侧滑菜单的关闭时候的监听器
         *
         * @param onClosedListener
         */
        void setMenuOnCloseListener(SlidingMenu.OnCloseListener onClosedListener);

        /**
         * 设置侧滑菜单关闭后的监听器
         *
         * @param onClosedListener
         */
        void setMenuOnClosedListener(SlidingMenu.OnClosedListener onClosedListener);

        /**
         * 设置侧滑菜单打开时候的监听器
         *
         * @param openListener
         */
        void setMenuOpenListener(SlidingMenu.OnOpenListener openListener);

        /**
         * 设置侧滑菜单打开后的监听器
         *
         * @param openedListener
         */
        void setMenuOpenedListener(SlidingMenu.OnOpenedListener openedListener);

        /**
         * 显示首页
         */
        void showIndex();

        /**
         * 显示地图
         */
        void startMap();

        /**
         * 显示专题模块
         */
        void showSpecialModel();

        /**
         * 显示工具模块
         */
        void showTools();

        /**
         * 打开消息页面
         */
        void startMessage();

        /**
         * 显示设置页面
         */
        void showSetting();

        /**
         * 打开选择学校页面
         */
        void startChooseSchools();

        /**
         * 打开搜索的视图页面
         */
        void startSearchView();

        /**
         * 打开我喜欢的视图页面
         */
        void startMyLoveView();

        /**
         * 打开我关注的圈子页面
         */
        void startMyCircleWord();

        /**
         * 打开登录页面
         */
        void startLoginActivity();

        /**
         * 隐藏所有的View视图
         */
        void hideAllView();


        /**
         * 设置动态列表页面登录状态改变用于显示的监听器
         *
         * @param listener DynamicLoginStatusChangedListener
         */
        void setDynamicLoginStatusChangedListener(DynamicLoginStatusChangedListener listener);

        /**
         * 动态列表页面登录状态改变时候的监听器
         */
        interface DynamicLoginStatusChangedListener {
            /**
             * 显示登录状态下的动态视图
             */
            void showLoginDynamicView();

            /**
             * 显示未登录的动态视图
             */
            void showLogoutDynamicView();
        }
    }


    /**
     * MainActivity的Presenter层接口
     */
    interface Presenter extends BasePresenter {

        /**
         * 得到是否登录的权限身份
         */
        boolean getIdentity();

        /**
         * 请求登录接口
         */
        void ReqLogin();
    }
}
