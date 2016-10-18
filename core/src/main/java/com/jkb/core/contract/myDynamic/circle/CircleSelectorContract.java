package com.jkb.core.contract.myDynamic.circle;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.data.info.circle.CircleInfo;

import java.util.List;

/**
 * 圈子选择的页面协议类
 * Created by JustKiddingBaby on 2016/10/18.
 */

public interface CircleSelectorContract {

    interface View extends BaseView<Presenter> {
        /**
         * 显示刷新的视图
         */
        void showRefreshingView();

        /**
         * 隐藏刷新的视图
         */
        void hideRefreshingView();

        /**
         * 设置圈子数据
         */
        void setCircleDatas(List<CircleInfo> circleDatas);

        /**
         * 绑定用户Id
         *
         * @return 用户id
         */
        int bindUserId();
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
         * 初始化用户id
         */
        void initUserId();

        /**
         * 初始化圈子数据
         */
        void initCircleData();

        /**
         * 当圈子条目被点击的时候
         *
         * @param position 条目数
         */
        void onCircleItemClick(int position);
    }
}
