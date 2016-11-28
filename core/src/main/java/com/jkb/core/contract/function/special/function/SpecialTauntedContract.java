package com.jkb.core.contract.function.special.function;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.data.special.SpecialData;

import java.util.List;

/**
 * 吐槽墙的页面协议类
 * Created by JustKiddingBaby on 2016/11/19.
 */

public interface SpecialTauntedContract {

    interface View extends BaseView<Presenter> {

        /**
         * 得到学校的id
         */
        int getSchoolId();

        /**
         * 滑动到顶部
         */
        void scrollToTop();

        /**
         * 显示专题视图
         */
        void showSpecialView();

        /**
         * 隐藏专题视图
         */
        void hideSpecialView();

        /**
         * 显示刷新视图
         */
        void showRefreshingView();

        /**
         * 隐藏刷新视图
         */
        void hideRefreshingView();

        /**
         * 设置专题数据
         */
        void setSpecialData(List<SpecialData> specialData);

        /**
         * 打开吐槽墙详情页面
         *
         * @param dynamicId 动态id
         */
        void startSpecialTauntedDetail(int dynamicId);

        /**
         * 打开评论列表页面
         *
         * @param dynamicId 动态id
         */
        void startCommentList(int dynamicId);
        /**
         * 分享
         */
        void share(String title, String titleUrl, String text, String imageUrl, String url,
                   String site, String siteUrl);

    }

    interface Presenter extends BasePresenter {

        /**
         * 绑定数据到视图中
         */
        void bindDataToView();

        /**
         * 初始化专题数据
         */
        void initSpecialData();

        /**
         * 刷新数据
         */
        void onRefresh();

        /**
         * 加载更多
         */
        void onLoadMore();

        /**
         * 条目被点击的时候
         */
        void onItemClick(int position);

        /**
         * 分享条目被点击
         */
        void onShareItemClick(int position);

        /**
         * 评论条目被点击
         */
        void onCommentItemClick(int position);

        /**
         * 喜欢条目被点击
         */
        void onHeartItemClick(int position);
    }
}
