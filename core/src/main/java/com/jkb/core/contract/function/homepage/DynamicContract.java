package com.jkb.core.contract.function.homepage;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * 动态的页面协议类
 * Created by JustKiddingBaby on 2016/8/23.
 */

public interface DynamicContract {

    interface View extends BaseView<Presenter> {

        /**
         * 显示登录了的视图
         */
        void showLoginedView();

        /**
         * 显示未登录状态下的视图
         */
        void showUnLoginView();

        /**
         * 显示加载的下拉刷新控件
         */
        void showRefreshingView();

        /**
         * 隐藏加载的下拉刷新控件
         */
        void hideRefreshingView();

        /**
         * 显示评论的页面
         */
        void showCommentView();

        /**
         * 显示分享的页面
         */
        void showShareView();

        /**
         * 打开动态详情页面
         */
        void startDynamicDetailView();

        /**
         * 点击喜欢
         */
        void like();
    }

    interface Presenter extends BasePresenter {

    }
}
