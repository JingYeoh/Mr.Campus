package com.jkb.core.contract.function.index;

import android.support.annotation.NonNull;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.contract.function.data.hot.HotDynamic;

import java.util.List;

/**
 * 热门动态的页面协议类
 * Created by JustKiddingBaby on 2016/9/30.
 */

public interface HotContract {

    interface View extends BaseView<Presenter> {

        /**
         * 当没有选择学校的时候，隐藏热门动态的显示
         */
        void hideHotView();

        /**
         * 显示热门动态视图
         */
        void showHotView();

        /**
         * 显示正在刷新的视图
         */
        void showRefreshingView();

        /**
         * 隐藏正在刷新的视图
         */
        void hideRefreshingView();

        /**
         * 设置热门的动态数据
         *
         * @param hotDynamicData 热门动态数据
         */
        void setHotDynamicData(List<HotDynamic> hotDynamicData);

        /**
         * 打开动态详情页面
         *
         * @param dynamic_id  动态id
         * @param dynamicType 动态类型
         */
        void startDynamicDetail(@NonNull int dynamic_id, @NonNull String dynamicType);

        /**
         * 打开评论页面
         *
         * @param dynamic_id 动态id
         */
        void startCommentList(@NonNull int dynamic_id);

        /**
         * 打开个人中心页面
         *
         * @param user_id 用户id
         */
        void startPersonCenter(@NonNull int user_id);

        /**
         * 打开圈子首页页面
         *
         * @param circle_id 圈子id
         */
        void startCircleIndex(@NonNull int circle_id);
    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化学校id
         */
        void initSchoolId();

        /**
         * 初始化热门动态
         */
        void initHotDynamic();

        /**
         * 绑定数据到视图中
         */
        void bindDataToView();

        /**
         * 刷新数据的时候
         */
        void onRefresh();

        /**
         * 加载更多
         */
        void onLoadMore();
    }
}
