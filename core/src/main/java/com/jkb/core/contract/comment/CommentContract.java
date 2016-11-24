package com.jkb.core.contract.comment;

import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentData;

import java.util.List;

/**
 * 评论的页面协议类
 * Created by JustKiddingBaby on 2016/11/24.
 */

public interface CommentContract {

    interface View {
        /**
         * 设置对象名称
         */
        void setTargetName(String targetName);

        /**
         * 隐藏加载更多的视图：即无更多内容
         */
        void hideLoadMoreView();

        /**
         * 显示加载更多的视图
         */
        void showLoadMoreView();

        /**
         * 显示无评论时视图
         */
        void showNoneCommentView();

        /**
         * 加载完成
         */
        void loadCompleted();

        /**
         * 设置评论内容
         */
        void setCommentData(List<DynamicDetailCommentData> commentsData);

        /**
         * 通过热门排序评论
         */
        void showOrderByHotView();

        /**
         * 通过顺序排序评论
         */
        void showOrderByAsc();

        /**
         * 通过逆序排序
         */
        void showOrderByDesc();

        /**
         * 提交评论
         */
        void commitComment();

        /**
         * 评论成功
         */
        void onCommentSuccess();

        /**
         * 打开个人中心
         */
        void startPersonCenter(int user_id);

        /**
         * 打开评论列表
         */
        void startCommentList(int dynamicId);

        /**
         * 打开评论详情
         */
        void startCommentDetail(int dynamicId, int commentId);
    }

    interface Presenter {

        /**
         * 刷新评论
         */
        void onRefreshComment();

        /**
         * 加载更多评论
         */
        void loadMoreComment();

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
        void sendCommitReply(int commentPosition, String comment);

        /**
         * 提交回复
         *
         * @param commentPosition 评论条目数
         * @param replyPosition   回复的条目数
         * @param comment         评论的内容
         */
        void sendCommitReply(int commentPosition, int replyPosition, String comment);

        /**
         * 点击热门排序的时候
         */
        void onOrderByHot();

        /**
         * 顺序或者逆序排序的时候
         */
        void onOrderByDesc$Asc();

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
         * 当喜欢评论条目的点击监听
         */
        void onConfessionCommentItemClick(int position);

        /**
         * 评论的用户点击事件
         */
        void onCommentUserItemClick(int position);

        /**
         * 回复发送者的用户点击事件
         */
        void onReplySenderUserItemClick(int commentPosition, int replyPosition);

        /**
         * 回复：目标用户的点击事件
         */
        void onReplyTargetUserItemClick(int commentPosition, int replyPosition);

        /**
         * 查看全部回复
         */
        void onReplyLoadMoreItemClick(int commentPosition);
    }
}
