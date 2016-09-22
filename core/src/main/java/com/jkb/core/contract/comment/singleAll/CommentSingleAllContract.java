package com.jkb.core.contract.comment.singleAll;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentReplyData;

import java.util.List;

/**
 * 单个所有评论和回复的页面协议类
 * Created by JustKiddingBaby on 2016/9/21.
 */

public interface CommentSingleAllContract {


    interface View extends BaseView<Presenter> {
        /**
         * 绑定评论id
         *
         * @return 评论id
         */
        int bindCommentId();

        /**
         * 绑定动态id
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
         * 清除输入数据并隐藏软键盘的显示
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
         * 发布评论
         */
        void commitComment$Reply();

        /**
         * 设置评论的数据
         *
         * @param commentContent 评论的主体数据
         */
        void setCommentContent(String commentContent);

        /**
         * 设置评论的用户名称
         *
         * @param commentUserName 评论的用户昵称
         */
        void setCommentUserName(String commentUserName);

        /**
         * 设置评论的用户头像
         *
         * @param commentUserAvatar 评论的用户头像
         */
        void setCommentUserAvatar(String commentUserAvatar);

        /**
         * 设置评论时间
         *
         * @param commentCreate_time 评论时间
         */
        void setCommentCreate_Time(String commentCreate_time);

        /**
         * 设置喜欢数
         *
         * @param likeCount 喜欢数目
         */
        void setLikeCount(int likeCount);

        /**
         * 设置是否喜欢
         *
         * @param hasFavorite 是否喜欢
         */
        void setHasFavorite(boolean hasFavorite);

        /**
         * 设置评论的回复内容
         *
         * @param replyDataList 回复内容
         */
        void setReplyData(List<DynamicDetailCommentReplyData> replyDataList);

        /**
         * 打开个人中心页面视图
         *
         * @param user_id 用户id
         */
        void startPersonCenterView(int user_id);
    }

    interface Presenter extends BasePresenter {

        /**
         * 初始化评论和回复数据
         */
        void initComment$ApplyData();

        /**
         * 初始化评论id
         */
        void initComment_id();

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
         * 提交评论
         *
         * @param comment 评论
         */
        void sendComment(String comment);

        /**
         * 提交回复
         *
         * @param comment 评论的内容
         */
        void commentReply(String comment);

        /**
         * 提交回复
         *
         * @param replyPosition 回复的条目数
         * @param comment       评论的内容
         */
        void commentReply(int replyPosition, String comment);

        /**
         * 点击喜欢的动作
         */
        void onLikeClick();

        /**
         * 评论用户头像的点击事件
         */
        void onCommentUserClick();

        /**
         * 点击回复用户的方法
         */
        void onReplyUserClick(int position);

        /**
         * 点击目标回复用户的方法
         */
        void onTargetReplyUserClick(int position);

        /**
         * 回复的内容的点击监听方法
         */
        void onReplyContentClick();

        /**
         * 回复的内容的点击监听方法
         */
        void onReplyReplyContentClick(int replyPosition);
    }
}
