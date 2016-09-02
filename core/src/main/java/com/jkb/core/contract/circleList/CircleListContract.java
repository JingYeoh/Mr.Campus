package com.jkb.core.contract.circleList;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.presenter.personCenter.data.CircleData;

import java.util.List;

/**
 * 圈子列表的页面控制器
 * Created by JustKiddingBaby on 2016/9/2.
 */

public interface CircleListContract {


    interface View extends BaseView<Presenter> {


        /**
         * 显示没有数据时候的视图
         */
        void showCircleNonDataView();

        /**
         * 显示刷新的视图
         */
        void showRefreshingView();

        /**
         * 隐藏刷新视图
         */
        void hideRefreshingView();

        /**
         * 设置圈子数据
         */
        void setCircleData(List<CircleData> circleData);


        /**
         * 绑定用户id
         *
         * @return 用户id
         */
        int bindUser_id();
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
         * 初始化圈子数据
         */
        void initCircleListData();

        /**
         * 得到用户id
         */
        void bindUser_id();

        /**
         * 绑定数据
         */
        void bindData();

        /**
         * 得到圈子id
         *
         * @param position 条目数
         * @return 圈子id
         */
        int getCircleId(int position);
    }
}
