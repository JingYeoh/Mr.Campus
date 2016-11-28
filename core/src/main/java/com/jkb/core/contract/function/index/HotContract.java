package com.jkb.core.contract.function.index;

import android.support.annotation.NonNull;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.data.index.hot.HotDynamic;

import java.util.ArrayList;
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
         * 滑动到顶部
         */
        void scrollToTop();

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

        /**
         * 显示分享的页面
         *
         * @param position 需要分享的条目
         */
        void showShareView(@NonNull int position);

        /**
         * 显示图片预览效果
         *
         * @param pictures 图片
         * @param position 显示的条目
         */
        void showImagesBrowserView(ArrayList<String> pictures, int position);

        /**
         * 分享
         */
        void share(String title, String titleUrl, String text, String imageUrl, String url,
                   String site, String siteUrl);
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

        /**
         * 热门动态条目被点击的时候
         *
         * @param position 条目数
         */
        void onHotDynamicItemClick(int position);

        /**
         * 用户关注按钮的条目点击监听事件
         *
         * @param position 条目
         */
        void onUserAttentionItemClick(int position);

        /**
         * 圈子订阅按钮的条目点击监听事件
         *
         * @param position 条目数
         */
        void onCircleSubscribeItemClick(int position);

        /**
         * 头像的点击监听事件
         *
         * @param position 条目数
         */
        void onHeadImgItemClick(int position);

        /**
         * 评论的点击监听事件
         *
         * @param position 条目数
         */
        void onCommentItemClick(int position);

        /**
         * 喜欢的点击监听事件
         *
         * @param position 条目数
         */
        void onLikeItemClick(int position);

        /**
         * 分享的条目点击监听
         */
        void onShareItemClick(int position);

        /**
         * 原作者的点击监听事件
         *
         * @param position 条木数
         */
        void onCreatorUserClick(int position);

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
