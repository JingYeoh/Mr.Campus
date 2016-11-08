package com.jkb.core.contract.circle;

import android.support.annotation.NonNull;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.data.circle.userInCircle.UserInCircle;

import java.util.List;

/**
 * 关注圈子的用户列表的页面协议类
 * Created by JustKiddingBaby on 2016/11/2.
 */

public interface CircleUserInCircleBlackListContract {

    interface View extends BaseView<Presenter> {

        /**
         * 得到圈子id
         */
        int getCircleId();

        /**
         * 设置用户数据
         *
         * @param userData  用户数据
         * @param isCreator 是否是创建者
         */
        void setUserData(List<UserInCircle> userData, boolean isCreator);

        /**
         * 显示加载的视图
         */
        void showRefreshingView();

        /**
         * 隐藏加载的视图
         */
        void hideRefreshingView();

        /**
         * 打开个人中心视图
         *
         * @param user_id 用户id
         */
        void startPersonCenter(@NonNull int user_id);
    }

    interface Presenter extends BasePresenter {

        /**
         * 初始化圈子id
         */
        void initCircleId();

        /**
         * 初始化用户列表
         */
        void initUserList();

        /**
         * 绑定数据到视图中
         */
        void bindDataToView();

        /**
         * 刷新
         */
        void onRefresh();

        /**
         * 加载
         */
        void onLoadMore();

        /**
         * 当用户被点击的时候
         *
         * @param position 条目数
         */
        void onUserItemClick(int position);

        /**
         * 当关注条目被点击的时候
         *
         * @param position 条目数
         */
        void onPull$PutBlackListItemClick(int position);
    }
}
