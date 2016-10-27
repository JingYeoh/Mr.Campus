package com.jkb.core.contract.menu;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.control.userstate.UserState;

/**
 * 右滑菜单的页面协议类
 * Created by JustKiddingBaby on 2016/8/16.
 */

public interface RightMenuContract {


    interface View extends BaseView<Presenter> {

        /**
         * 设置访客数目
         */
        void setVisitorCount(int count);

        /**
         * 设置关注总数
         */
        void setAttentionCount(int count);

        /**
         * 设置粉丝总数
         */
        void setFansCount(int count);

        /**
         * 显示关注视图
         */
        void showAttentionView();

        /**
         * 显示粉丝视图
         */
        void showFansView();

        /**
         * 显示访客视图
         */
        void showVisitorView();
    }

    interface Presenter extends BasePresenter {

        /**
         * 得到总数
         */
        void getCountData();

        /**
         * 得到用户的id
         */
        int getUser_id();

        /**
         * 更新个人数据
         */
        void updatePersonInfo();
    }

}
