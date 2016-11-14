package com.jkb.core.contract.map;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.data.info.user.UserInfo;

import java.util.List;

/**
 * 地图列表的页面协议类
 * Created by JustKiddingBaby on 2016/11/12.
 */

public interface MapListContract {

    interface View extends BaseView<Presenter> {

        /**
         * 设置标题栏内容
         */
        void setTitleText(String titleText);

        /**
         * 显示刷新视图
         */
        void showRefreshing();

        /**
         * 隐藏刷新视图
         */
        void hideRefreshing();

        /**
         * 显示地图筛选栏
         */
        void showFloatMapFilter();

        /**
         * 请求附近的人的数据
         */
        void reqNearUserInfo(int currentPage);

        /**
         * 关闭附近的用户显示
         */
        void clearNearUserInfo();

        /**
         * 设置用户信息
         */
        void setUserInfo(List<UserInfo> userInfo);

        /**
         * 提醒打開附近的人
         */
        void showHintForOpenNearUser();
    }

    interface Presenter extends BasePresenter {
        /**
         * 绑定数据到视图中
         */
        void bindDataToView();

        /**
         * 初始化圈子列表的数据
         */
        void initCircleListData();

        /**
         * 无条件筛选触发
         */
        void onNoFilterSelected();

        /**
         * 圈子选择触发
         */
        void onCircleSelected();

        /**
         * 用户被被选择时触发
         *
         * @param sex 性别
         *            0不筛选
         *            1男
         *            2女
         */
        void onUserSelected(int sex);

        /**
         * 刷新
         */
        void onRefresh();

        /**
         * 加载更多
         */
        void onLoadMore();

        /**
         * 附近的用户不允许被得到
         */
        void onNearUserNotAble();

        /**
         * 设置附近的用户
         */
        void setNearUserInfo(List<UserInfo> nearUserInfo);

        /**
         * 得到附近的用户数据失败
         */
        void getNearUserInfoFailed();
    }
}
