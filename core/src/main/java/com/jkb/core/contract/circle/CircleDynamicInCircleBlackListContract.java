package com.jkb.core.contract.circle;

import android.support.annotation.NonNull;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.data.circle.dynamicInBlackList.DynamicInBlackList;

import java.util.List;

/**
 * 圈子动态（小黑屋）的页面协议类
 * Created by JustKiddingBaby on 2016/11/5.
 */

public interface CircleDynamicInCircleBlackListContract {

    interface View extends BaseView<Presenter> {

        /**
         * 得到圈子id
         */
        int getCircleId();

        /**
         * 设置圈子内动态黑名单数据
         */
        void setDynamicInBlackListData(List<DynamicInBlackList> dynamicInBlackListData);

        /**
         * 显示刷新视图
         */
        void showRefreshingView();

        /**
         * 隐藏刷新视图
         */
        void hideRefreshingView();

        /**
         * 打开动态详情：话题页面
         */
        void startDynamicDetailTopic(@NonNull int dynamic_id);

        /**
         * 打开动态详情：文章页面
         */
        void startDynamicDetailArticle(@NonNull int dynamic_id);

        /**
         * 打开动态详情：普通页面
         */
        void startDynamicDetailNormal(@NonNull int dynamic_id);

        /**
         * 打开个人中心
         */
        void startPersonCenter(@NonNull int user_id);
    }

    interface Presenter extends BasePresenter {

        /**
         * 初始化圈子ID
         */
        void initCircleId();

        /**
         * 初始化圈子动态黑名单
         */
        void initDynamicInCircleBlackList();

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
         * 当动态条目被点击的时候
         *
         * @param position 条目数
         */
        void onDynamicItemClick(int position);

        /**
         * 解除拉黑动态的条目点击事件
         */
        void onPullDynamicOutBlackItemClick(int position);

        /**
         * 用户条目被点击的时候
         */
        void onUserItemClick(int position);
    }
}
