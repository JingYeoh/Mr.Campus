package com.jkb.core.contract.dynamicDetail.topic;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentData;

import java.util.List;

/**
 * 动态详情——话题动态的页面协议类
 * Created by JustKiddingBaby on 2016/9/17.
 */

public interface DynamicDetailTopicContract {

    interface View extends BaseView<Presenter> {

        /**
         * 绑定动态id
         *
         * @return 动态id
         */
        int bindDynamicId();

        /**
         * 设置图片
         *
         * @param imageUrl 图片网址
         */
        void setContentImages(String imageUrl);

        /**
         * 隐藏普通视图的显示
         */
        void hideTopicContentView();

        /**
         * 显示普通视图
         */
        void showTopicContentView();

        /**
         * 设置内容
         */
        void setContentValue(String contentValue);

        /**
         * 设置作者头像
         */
        void setUserHeadImg(String userHeadImg);

        /**
         * 设置用户昵称
         */
        void setUserNickName(String userNickName);

        /**
         * 设置话题标题
         */
        void setTopicTitle(String topicTitle);

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
         * 设置参与数
         *
         * @param partInCount 参与数
         */
        void setPartInCount(int partInCount);

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
         * 加载更多
         */
        void onLoadMore();

        /**
         * 显示加载更多的视图
         */
        void showLoadMoreView();

        /**
         * 隐藏加载更多的视图：即无更多内容
         */
        void hideLoadMoreView();

        /**
         * 加载完成
         */
        void loadCompleted();

        /**
         * 通过热门排序评论
         */
        void setOrderByHotView();

        /**
         * 通过顺序排序评论
         */
        void setOrderByAsc();

        /**
         * 通过逆序排序
         */
        void setOrderByDesc();

        /**
         * 提交评论
         */
        void commitComment();

        /**
         * 清除输入的信息并且退出软键盘
         */
        void clearComment$HideSoftInputView();

        /**
         * 清楚回复状态
         * 当软键盘关闭的时候清除回复状态，设置为评论
         */
        void clearReplyStatus();

        /**
         * 设置回复的状态
         *
         * @param replyName 回复的名称
         */
        void setReplyTargetNickName(String replyName);

        /**
         * 展示回复的状态
         *
         * @param commentPosition 评论条目
         */
        void setReplyTargetNickName(int commentPosition);

        /**
         * 展示回复的状态
         *
         * @param commentPosition 评论条目
         * @param replyPosition   回复条目
         */
        void setReplyReplyStatus(int commentPosition, int replyPosition);

        /**
         * 打开个人中心页面视图
         *
         * @param user_id 用户id
         */
        void startPersonCenterView(int user_id);

        /**
         * 点击头像的时候
         */
        void onUserHeadImgClick();

        /**
         * 打开个人中心页面
         *
         * @param user_id 用户id
         */
        void startPersonCenterActivity(int user_id);

        /**
         * 显示查看所有的评论和回复的视图页面
         *
         * @param comment_id 评论id
         */
        void showViewAllComment$ReplyView(int comment_id);
    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化普通数据
         */
        void initTopicData();

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
         * 喜欢的点击监听方法
         */
        void onLikeCommentClick(int position);

        /**
         * 点击喜欢动态的按钮
         */
        void onLikeDynamicClick();

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

        /**
         * 回复的内容的点击监听方法
         *
         * @param commentPosition 评论回复的条目数
         */
        void onReplyContentClick(int commentPosition);

        /**
         * 回复的内容的点击监听方法
         */
        void onReplyReplyContentClick(int commentPosition, int replyPosition);

        /**
         * 提交评论
         *
         * @param comment 评论
         */
        void sendComment(String comment);

        /**
         * 提交回复
         *
         * @param commentPosition 评论条目数
         * @param comment         评论的内容
         */
        void commitReply(int commentPosition, String comment);

        /**
         * 提交回复
         *
         * @param commentPosition 评论条目数
         * @param replyPosition   回复的条目数
         * @param comment         评论的内容
         */
        void commitReply(int commentPosition, int replyPosition, String comment);

        /**
         * 得到用户id
         *
         * @param position 条目
         * @return 用户Id
         */
        int getUser_id(int position);

        /**
         * 点击头像的时候
         */
        void onUserHeadImgClick();

        /**
         * 点击热门排序的时候
         */
        void onOrderByHot();

        /**
         * 顺序或者逆序排序的时候
         */
        void onOrderByDesc$Asc();
    }
}
