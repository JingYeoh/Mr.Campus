package com.jkb.core.contract.map.list;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.data.info.circle.CircleInfo;

import java.util.List;

/**
 * 地图列表——附近的圈子的页面协议类
 * Created by JustKiddingBaby on 2016/11/14.
 */

public interface MapListNearCircleContract {

    interface View extends BaseView<Presenter> {

        /**
         * 顯示沒有选择学校的视图
         */
        void showUnSelectedSchoolView();

        /**
         * 显示选择了学校的视图
         */
        void showSchoolSelectedView();

        /**
         * 显示刷新视图
         */
        void showRefreshing();

        /**
         * 隐藏刷新视图
         */
        void hideRefreshing();

        /**
         * 设置地图信息
         */
        void setCircleInfo(List<CircleInfo> circleInfo);

        /**
         * 打开圈子首页页面
         */
        void startCircleIndex(int circleId);

    }

    interface Presenter extends BasePresenter {
        /**
         * 刷新
         */
        void onRefresh();

        /**
         * 加载更多
         */
        void onLoadMore();

        /**
         * 绑定数据到视图中
         */
        void bindDataToView();

        /**
         * 初始化附近的圈子数据
         */
        void initNearCircleData();

        /**
         * 圈子条目被点击的时候触发
         */
        void onCircleItemClick(int position);
    }
}
