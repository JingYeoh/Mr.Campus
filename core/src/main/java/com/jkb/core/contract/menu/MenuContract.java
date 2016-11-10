package com.jkb.core.contract.menu;

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
        void showLeftMenuView();

        /**
         * 显示右滑菜单视图
         */
        void showRightMenuView();

        /**
         * 隐藏菜单视图
         */
        void hideMenuView();

        /**
         * 显示首页
         */
        void showIndexView();

        /**
         * 显示地图
         */
        void startMapView();

        /**
         * 显示专题模块
         */
        void showSpecialModelView();

        /**
         * 显示工具模块
         */
        void showToolsView();

        /**
         * 打开消息页面
         */
        void startMessageView();

        /**
         * 显示设置页面
         */
        void showSettingView();

        /**
         * 打开选择学校页面
         */
        void startChooseSchoolsView();

        /**
         * 打开登录页面
         */
        void startLoginActivity();

        /**
         * 隐藏所有的View视图
         */
        void hideAllView();
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
