package com.jkb.core.contract.im.conversationList;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * 聊天列表的页面协议类
 * Created by JustKiddingBaby on 2016/10/21.
 */

public interface ConversationListContract {

    interface View extends BaseView<Presenter> {

        /**
         * 刷新聊天列表
         */
        void refreshConversationList();

        /**
         * 显示刷新的视图
         */
        void showRefreshView();

        /**
         * 隐藏刷新的视图
         */
        void hideRefreshView();
    }

    interface Presenter extends BasePresenter {

    }
}
