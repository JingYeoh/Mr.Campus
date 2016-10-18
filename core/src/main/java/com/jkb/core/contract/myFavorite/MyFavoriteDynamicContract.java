package com.jkb.core.contract.myFavorite;

import android.support.annotation.NonNull;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.contract.dynamicDetail.data.DynamicDetailArticleData;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.MyFavoriteDynamicData;

import java.util.List;

/**
 * 我的喜欢的动态的页面协议类
 * Created by JustKiddingBaby on 2016/10/14.
 */

public interface MyFavoriteDynamicContract {

    interface View extends BaseView<Presenter> {

        /**
         * 绑定用户id
         *
         * @return 用户id
         */
        int bindUser_id();

        /**
         * 显示刷新视图
         */
        void showRefreshingView();

        /**
         * 隐藏刷新视图
         */
        void hideRefreshingView();

        /**
         * 显示是否确定的的提示框视图
         *
         * @param position 条目数
         */
        void showHintDetermineView(int position);

        /**
         * 显示分享的视图
         *
         * @param position 条目数
         */
        void showShareDynamicView(int position);

        /**
         * 设置我的普通动态数据
         */
        void setMyFavoriteDynamic(List<MyFavoriteDynamicData> articleDatas);

        /**
         * 打开文章动态详情页面
         *
         * @param dynamic_id 动态Id
         */
        void startDynamicDetailArticle(@NonNull int dynamic_id);

        /**
         * 打开普通动态详情页面
         *
         * @param dynamic_id 动态Id
         */
        void startDynamicDetailNormal(@NonNull int dynamic_id);

        /**
         * 打开话题动态详情页面
         *
         * @param dynamic_id 动态Id
         */
        void startDynamicDetailTopic(@NonNull int dynamic_id);

        /**
         * 打开评论列表页面
         *
         * @param dynamic_id 动态id
         */
        void startCommentList(@NonNull int dynamic_id);

        /**
         * 打开个人中心的页面
         *
         * @param user_id 用户id
         */
        void startPersonCenter(@NonNull int user_id);

        /**
         * 打开圈子首页
         *
         * @param circle_id 圈子id
         */
        void startCircleIndex(@NonNull int circle_id);
    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化用戶id
         */
        void initUser_id();

        /**
         * 初始化动态
         */
        void initDynamic();

        /**
         * 绑定数据到视图中
         */
        void bindDataToView();

        /**
         * 刷新
         */
        void onRefresh();

        /**
         * 加载更多
         */
        void onLoadMore();

        /**
         * 动态条目点击监听
         *
         * @param position 条目数
         */
        void onItemDynamicClick(int position);

        /**
         * 喜欢动态条目的点击监听
         *
         * @param position 条目数
         */
        void onItemLikeClick(int position);

        /**
         * 喜欢动态条目
         *
         * @param position 条目数
         */
        void favoriteDynamic(int position);

        /**
         * 评论条目的点击监听
         *
         * @param position 条目数
         */
        void onItemCommentClick(int position);

        /**
         * 条目名称的点击监听
         *
         * @param position 条目数
         */
        void onItemNameClick(int position);
    }
}
