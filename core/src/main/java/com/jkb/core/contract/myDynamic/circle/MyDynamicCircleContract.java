package com.jkb.core.contract.myDynamic.circle;

import android.support.annotation.NonNull;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.contract.dynamicDetail.data.DynamicDetailArticleData;
import com.jkb.core.data.dynamic.myOriginal.myCircle.DynamicInCircleDynamic;
import com.jkb.core.data.info.circle.CircleInfo;

import java.util.List;

/**
 * 我的圈子动态的页面协议类
 * Created by JustKiddingBaby on 2016/10/14.
 */

public interface MyDynamicCircleContract {

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
         * 设置选择的圈子的名称
         *
         * @param circleName 圈子名称
         */
        void setCircleSelectedName(String circleName);

        /**
         * 显示是否删除的刷新视图
         *
         * @param position 条目数
         */
        void showToastOfDelete(int position);

        /**
         * 显示圈子筛选的页面视图
         */
        void showFilterCircleSelectorView();


        /**
         * 设置我的普通动态数据
         */
        void setMyCircleDynamic(List<DynamicInCircleDynamic> myCircleDynamic, boolean isOriginal);


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
         * 打开圈子首页
         *
         * @param circle_id 圈子id
         */
        void startCircleIndex(@NonNull int circle_id);

        /**
         * 打开评论列表页面
         *
         * @param dynamic_id 动态id
         */
        void startCommentList(@NonNull int dynamic_id);
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
         * 条目删除点击监听
         *
         * @param position 条目数
         */
        void onItemDeleteClick(int position);

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
         * 评论条目的点击监听
         *
         * @param position 条目数
         */
        void onItemCommentClick(int position);

        /**
         * 子条目的圈子图片点击监听
         *
         * @param position 条目数
         */
        void onItemCircleClick(int position);

        /**
         * 当选择所有圈子的时候
         */
        void onAllCircleSelected();

        /**
         * 选择圈子的时候
         *
         * @param circleInfo 圈子信息实体类
         */
        void onCircleSelected(CircleInfo circleInfo);
    }
}
