package com.jkb.core.contract.function.index;

import android.support.annotation.NonNull;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.data.dynamic.dynamic.DynamicBaseData;
import com.jkb.core.data.index.dynamic.IndexDynamicData;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态的页面协议类
 * Created by JustKiddingBaby on 2016/8/23.
 */

public interface DynamicContract2 {

    interface View extends BaseView<Presenter> {

        /**
         * 设置未读的消息数目
         */
        void setUnReadDynamicMessageCount(int messageCount);

        /**
         * 滚动到顶部
         */
        void scrollToTop();

        /**
         * 显示登录了的视图
         */
        void showLoginView();

        /**
         * 显示未登录状态下的视图
         */
        void showLogoutView();

        /**
         * 显示加载的下拉刷新控件
         */
        void showRefreshingView();

        /**
         * 隐藏加载的下拉刷新控件
         */
        void hideRefreshingView();

        /**
         * 显示书写动态视图
         */
        void showWriteDynamicView();

        /**
         * 显示评论的页面
         */
        void showCommentView();

        /**
         * 分享
         */
        void share(String title, String titleUrl, String text, String imageUrl, String url,
                   String site, String siteUrl);

        /**
         * 设置动态数据到视图中
         */
        void setDynamicDataToView(List<IndexDynamicData> dynamicData);

        /**
         * 打开文章动态详情页面
         *
         * @param dynamic_id 动态id
         */
        void startArticleDynamicDetail(int dynamic_id);


        /**
         * 打开普通的动态详情页面
         */
        void startNormalDynamicDetail(int dynamic_id);

        /**
         * 打开话题的动态详情页面
         */
        void startTopicDynamicDetail(int dynamic_id);

        /**
         * 打开评论页面
         *
         * @param dynamic_id 动态id
         */
        void startCommentActivity(int dynamic_id);

        /**
         * 打开动态消息的页面
         */
        void startDynamicMessageActivity();

        /**
         * 打开个人中心
         */
        void startPersonCenter(int user_id);

        /**
         * 打开圈子首页
         */
        void startCircleIndex(int circle_id);

        /**
         * 显示图片预览效果
         *
         * @param pictures 图片
         * @param position 显示的条目
         */
        void showImagesBrowserView(ArrayList<String> pictures, int position);
    }

    interface Presenter extends BasePresenter {

        /**
         * 刷新数据
         */
        void onRefresh();

        /**
         * 加载更多
         */
        void onLoadMore();

        /**
         * 初始化动态数据
         */
        void initDynamicData();

        /**
         * 绑定数据到视图中
         */
        void bindDataToView();

        /**
         * 当主体被点击的回调方法
         */
        void onContentItemClick(int position);

        /**
         * 当头像被点击的回调方法
         */
        void onHeadImgItemClick(int position);

        /**
         * 当分享条目被点击的回调方法
         */
        void onShareItemClick(int position);

        /**
         * 当评论条目的点击触发
         */
        void onCommentItemClick(int position);

        /**
         * 当喜欢动态的条目的点击触发
         */
        void onLikeItemClick(int position);

        /**
         * 当创建者的点击触发
         */
        void onCreatorItemClick(int position);

        /**
         * 设置缓存过期
         */
        void setCacheExpired();

        /**
         * 图片被点击的回调
         *
         * @param position      被点击的条目
         * @param clickPosition 被点击的图片条目
         */
        void onPicturesClick(int position, int clickPosition);
    }
}
