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
         * 显示刷新的视图
         */
        void showRefreshView();

        /**
         * 隐藏刷新的视图
         */
        void hideRefreshView();

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
         * 设置评论的回复内容
         *
         * @param replyDataList 回复内容
         */
        void setReplyData(List<DynamicDetailCommentReplyData> replyDataList);
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
    }
}
