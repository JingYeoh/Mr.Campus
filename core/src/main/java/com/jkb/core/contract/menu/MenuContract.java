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

        /**
         * 链接融云的服务器
         *
         * @param token 融云的Token
         */
        void connectRongIM(String token);

        /**
         * 登出融云聊天
         */
        void breakConnectRongIM();

        /**
         * 设置极光推送的别名
         *
         * @param user_id 用户id
         */
        void setJPushAlias(int user_id);

        /**
         * 退出激光推送
         */
        void quitJPush();
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

        /**
         * 连接融云聊天的服务
         */
        void connectRongIM();

        /**
         * 登出融云的聊天服务
         */
        void logoutRongIM();

        /**
         * 融云Token过期
         */
        void rongIMTokenIncorrect();

        /**
         * 设置极光推送别名
         */
        void initJPushAlias();

        /**
         * 退出极光推送
         */
        void quitJPush();
    }
}
