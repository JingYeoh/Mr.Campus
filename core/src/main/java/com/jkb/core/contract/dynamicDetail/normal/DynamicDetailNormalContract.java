package com.jkb.core.contract.dynamicDetail.normal;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentData;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态详情——普通动态的页面协议类
 * Created by JustKiddingBaby on 2016/9/17.
 */

public interface DynamicDetailNormalContract {

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
         * @param imageUrls 图片网址
         */
        void setContentImages(String[] imageUrls);

        /**
         * 隐藏普通视图的显示
         */
        void hideNormalContentView();

        /**
         * 显示普通视图
         */
        void showNormalContentView();

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
         * 提交评论
         */
        void commitComment();

        /**
         * 清除输入的信息并且退出软键盘
         */
        void clearComment$HideSoftInputView();

        /**
         * 显示评论列表的视图
         */
        void startCommentListView();

        /**
         * 显示图片预览
         */
        void showPicturesBrowserView(ArrayList<String> pictures, int position);
    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化普通数据
         */
        void initNormalData();

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
         * 喜欢的点击监听方法
         */
        void onLikeCommentClick(int position);

        /**
         * 点击喜欢动态的按钮
         */
        void onLikeDynamicClick();

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
         * 普通图片被点击的时候
         *
         * @param position 图片条目数
         */
        void onNormalPictureClick(int position);
    }
}
