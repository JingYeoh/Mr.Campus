package com.jkb.core.contract.usersList;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.presenter.usersList.data.UserData;

import java.util.List;

/**
 * 访客列表的页面协议类
 * Created by JustKiddingBaby on 2016/8/17.
 */

public interface VisitorContract {

    interface View extends BaseView<Presenter> {

        /**
         * 显示用户主页
         *
         * @param user_id 用户id
         */
        void showPersonCenter(int user_id);

        /**
         * 取消刷新或者加载的效果显示
         */
        void dismissRefresh$Loaded();

        /**
         * 显示下拉刷新效果
         */
        void showRefreshing();

        /**
         * 得到用户id
         *
         * @return 用户id
         */
        int getUser_id();

        /**
         * 设置用户id
         *
         * @param user_id 用户id
         */
        void setUser_id(int user_id);

        /**
         * 更新显示的数据
         */
        void updataViewData(List<UserData> userDatas);

        /**
         * 点击关注按钮的时候
         *
         * @param position 条目数
         */
        void clickPayAttention(int position);

        /**
         * 点击头像的时候
         *
         * @param position 条目数
         */
        void clickHeadImg(int position);
    }

    interface Presenter extends BasePresenter {

        /**
         * 关注或者取消关注
         *
         * @param position 条目数
         */
        void onPayAttentionCLicked(int position);


        /**
         * 头像点击的时候
         *
         * @param position 条目数
         */
        void onHeadImgClicked(int position);

        /**
         * 获取用户id
         */
        void getUser_id();

        /**
         * 得到关注用户列表数据
         */
        void getVisitorsUsersListData();

        /**
         * 绑定数据到视图中
         */
        void bindDataToView();

        /**
         * 关注/取消关注的请求
         *
         * @param target_id 目标id
         */
        void payAttentionOrCancle(int target_id);

        /**
         * 下拉刷新触发方法
         */
        void onRefresh();

        /**
         * 上拉加载触发方法
         */
        void onLoaded();
    }
}
