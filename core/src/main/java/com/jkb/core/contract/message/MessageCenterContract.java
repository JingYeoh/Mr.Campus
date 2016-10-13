package com.jkb.core.contract.message;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * 消息中心点的页面协议类
 * Created by JustKiddingBaby on 2016/10/10.
 */

public interface MessageCenterContract {

    interface View extends BaseView<Presenter> {

        /**
         * 設置喜欢的消息数据
         *
         * @param unReadCount 未读消息数
         * @param allCount    总消息数
         */
        void setLikeMessageCount(int unReadCount, int allCount);

        /**
         * 設置评论的消息数据
         *
         * @param unReadCount 未读消息数
         * @param allCount    总消息数
         */
        void setCommentMessageCount(int unReadCount, int allCount);

        /**
         * 設置粉丝的消息数据
         *
         * @param unReadCount 未读消息数
         * @param allCount    总消息数
         */
        void setFansMessageCount(int unReadCount, int allCount);

        /**
         * 設置订阅的消息数据
         *
         * @param unReadCount 未读消息数
         * @param allCount    总消息数
         */
        void setSubscribeMessageCount(int unReadCount, int allCount);

        /**
         * 設置圈子的消息数据
         *
         * @param unReadCount 未读消息数
         * @param allCount    总消息数
         */
        void setCircleMessageCount(int unReadCount, int allCount);

        /**
         * 設置系统的消息数据
         *
         * @param unReadCount 未读消息数
         * @param allCount    总消息数
         */
        void setSystemMessageCount(int unReadCount, int allCount);

        /**
         * 显示刷新的视图
         */
        void showRefreshingView();

        /**
         * 隐藏刷新的视图
         */
        void hideRefreshingView();

        /**
         * 显示喜欢的消息的页面
         */
        void showMessageLikeView();

        /**
         * 显示喜欢的消息的页面
         */
        void showMessageCommentView();

        /**
         * 显示喜欢的消息的页面
         */
        void showMessageFansView();

        /**
         * 显示喜欢的消息的页面
         */
        void showMessageSubscribeView();

        /**
         * 显示喜欢的消息的页面
         */
        void showMessageCircleView();

        /**
         * 显示喜欢的消息的页面
         */
        void showMessageSystemView();
    }

    interface Presenter extends BasePresenter {

        /**
         * 初始化用户id
         */
        void initUserId();

        /**
         * 初始化消息数据
         */
        void initMessageData();

        /**
         * 刷新数据
         */
        void onRefresh();

        /**
         * 绑定数据到视图中
         */
        void bindDataToView();
    }
}
