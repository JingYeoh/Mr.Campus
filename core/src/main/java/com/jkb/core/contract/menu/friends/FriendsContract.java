package com.jkb.core.contract.menu.friends;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.contract.menu.data.FriendsData;

import java.util.List;

/**
 * 好友列表的页面控协议类
 * Created by JustKiddingBaby on 2016/9/6.
 */

public interface FriendsContract {


    interface View extends BaseView<Presenter> {

        /**
         * 显示没有好友数据的视图
         */
        void showNonFriendsDataView();

        /**
         * 设置好友数据到视图中
         */
        void setFriendsData(List<FriendsData> friendsData);

        /**
         * 显示刷新视图
         */
        void showRefreshingView();

        /**
         * 隐藏刷新视图
         */
        void hideRefreshingView();
    }

    interface Presenter extends BasePresenter {

        /**
         * 刷新数据
         */
        void onRefresh();

        /**
         * 加载更多
         */
        void onLoadMore();

        /**
         * 绑定数据
         */
        void bindData();

        /**
         * 初始化好友数据
         */
        void initFriendsData();

        /**
         * 得到用户id
         */
        int getUserId(int position);

        /**
         * 设置缓存过期
         */
        void setCacheExpired();
    }
}
