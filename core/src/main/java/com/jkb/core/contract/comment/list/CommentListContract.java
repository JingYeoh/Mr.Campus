package com.jkb.core.contract.comment.list;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentData;

import java.util.List;

/**
 * 评论列表的页面协议类
 * Created by JustKiddingBaby on 2016/9/20.
 */

public interface CommentListContract {

    interface View extends BaseView<Presenter> {

        /**
         * 绑定动态id
         *
         * @return 动态id
         */
        int bindDynamicId();

        /**
         * 显示刷新的视图
         */
        void showRefreshView();

        /**
         * 隐藏刷新的视图
         */
        void hideRefreshView();

        /**
         * 设置评论内容
         */
        void setCommentData(List<DynamicDetailCommentData> commentsData);

        /**
         * 提交评论
         */
        void commitComment();

        /**
         * 清除输入的信息并且退出软键盘
         */
        void clearComment$HideSoftInputView();

        /**
         * 打开个人中心页面视图
         *
         * @param user_id 用户id
         */
        void startPersonCenterView(int user_id);

        /**
         * 显示查看所有的评论和回复的视图页面
         *
         * @param comment_id 评论id
         */
        void showViewAllComment$ReplyView(int comment_id);
    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化留言数据
         */
        void initCommentData();

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
         * 提交评论
         *
         * @param comment 评论
         */
        void sendComment(String comment);

        /**
         * 得到用户id
         *
         * @param position 条目
         * @return 用户Id
         */
        int getUser_id(int position);

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
