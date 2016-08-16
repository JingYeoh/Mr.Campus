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
         * 用户数据变化时候的监听器
         *
         * @return UserState.UsersChangedListener
         */
        UserState.UsersChangedListener onUserDataChangedListener();
    }

    interface Presenter extends BasePresenter {

        /**
         * 得到总数
         */
        void getCountData();

        /**
         * 设置用户数据变化时候的监听器
         */
        void setOnUsersDataChangedListener(UserState.UsersChangedListener listener);
    }

}
