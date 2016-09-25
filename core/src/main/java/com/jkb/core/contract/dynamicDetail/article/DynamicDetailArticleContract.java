package com.jkb.core.contract.dynamicDetail.article;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.contract.dynamicDetail.data.DynamicDetailArticleData;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentData;

import java.util.List;

/**
 * 动态详情：文章的页面协议类
 * Created by JustKiddingBaby on 2016/9/25.
 */

public interface DynamicDetailArticleContract {

    interface View extends BaseView<Presenter> {
        /**
         * 绑定动态id
         *
         * @return 动态id
         */
        int bindDynamicId();

        /**
         * 隐藏文章视图的显示
         */
        void hideArticleContentView();

        /**
         * 显示文章视图
         */
        void showArticleContentView();

        /**
         * 设置作者头像
         */
        void setUserHeadImg(String userHeadImg);

        /**
         * 设置用户昵称
         */
        void setUserNickName(String userNickName);

        /**
         * 设置文章标题
         */
        void setArticleTitle(String topicTitle);

        /**
         * 设置发表时间
         */
        void setPostTime(String postTime);

        /**
         * 设置评论数
         *
         * @param commentCount 评论数
         */
        void setCommentCount(int commentCount);

        /**
         * 设置喜欢数
         *
         * @param favoriteCount 喜欢数
         */
        void setFavoriteCount(int favoriteCount);

        /**
         * 设置是否喜欢
         */
        void setHasFavorite(boolean hasFavorite);

        /**
         * 设置标签
         */
        void setTag(String tag);

        /**
         * 设置标签数目
         *
         * @param tagCount 标签数目
         */
        void setTagCount(int tagCount);

        /**
         * 设置文章背景图
         *
         * @param articleBgImg 文章背景图
         */
        void setArticleBgImg(String articleBgImg);

        /**
         * 设置文章内容
         *
         * @param articleContent 文章内容
         */
        void setArticleContent(List<DynamicDetailArticleData.ArticleContent> articleContent);

        /**
         * 显示刷新的视图
         */
        void showRefreshView();

        /**
         * 隐藏刷新的视图
         */
        void hideRefreshView();

        /**
         * 显示没有评论数据时候的视图
         */
        void showNoneCommentView();

        /**
         * 设置评论内容
         */
        void setCommentData(List<DynamicDetailCommentData> commentsData);

        /**
         * 打开个人中心视图
         *
         * @param user_id 用户id
         */
        void startPersonCenterActivity(int user_id);

        /**
         * 打开评论列表页面
         *
         * @param dynamic_id 动态id
         */
        void startCommentListActivity(int dynamic_id);

        /**
         * 打开评论详情页面
         *
         * @param comment_id 评论id
         */
        void showViewAllComment$ReplyView(int comment_id);
    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化普通数据
         */
        void initArticleData();

        /**
         * 初始化动态id
         */
        void initDynamic_id();

        /**
         * 绑定数据到视图中
         */
        void bindDataToView();

        /**
         * 刷新数据
         */
        void onRefresh();

        /**
         * 加载更多
         */
        void onLoadMore();

        /**
         * 点击作者的时候
         */
        void onAuthClick();

        /**
         * 点击喜欢动态的按钮
         */
        void onLikeDynamicClick();

        /**
         * 喜欢的点击监听方法
         */
        void onLikeCommentClick(int position);

        /**
         * 评论用户头像的点击事件
         */
        void onCommentUserClick(int commentPosition);

        /**
         * 回复用户的点击事件
         */
        void onReplyUserClick(int commentPosition, int replyPosition);

        /**
         * 回复用户的点击事件
         */
        void onTargetReplyUserClick(int commentPosition, int replyPosition);

        /**
         * 查看所有的评论和回复的点击事件
         */
        void onViewAllComment$ReplyClick(int commentPosition);
    }
}
