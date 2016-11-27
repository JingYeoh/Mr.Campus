package com.jkb.core.contract.function.special.detail.general;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.contract.comment.CommentContract;

import java.util.ArrayList;
import java.util.List;

/**
 * 专题详情页面协议类
 * Created by JustKiddingBaby on 2016/11/24.
 */

public interface SubjectDetailContract {

    interface View extends BaseView<Presenter>, CommentContract.View {

        /**
         * 得到动态id
         */
        int getDynamicId();

        /**
         * 设置专题标题
         */
        void setSubjectTitle(String confessionTitle);

        /**
         * 设置专题主体
         */
        void setSubjectContent(String confessionContent);

        /**
         * 设置专题图片
         */
        void setSubjectImageUrls(List<String> confessionImageUrls);

        /**
         * 设置用户昵称
         */
        void setUserNickName(String userNickName);

        /**
         * 设置用户头像
         */
        void setUserAvatar(String userAvatar);


        /**
         * 设置评论数目
         */
        void setCommentCount(int commentCount);

        /**
         * 设置喜欢数目
         */
        void setFavoriteCount(int favoriteCount);

        /**
         * 设置喜欢的状态
         */
        void setFavoriteStatus(boolean favoriteStatus);

        /**
         * 显示预览大图
         */
        void showPictureBrowserView(ArrayList<String> urls, int position);

        /**
         * 显示整体视图
         */
        void showContentView();

        /**
         * 隐藏整体视图
         */
        void hideContentView();
    }

    interface Presenter extends BasePresenter, CommentContract.Presenter {

        /**
         * 加载数据到视图中
         */
        void bindDataToView();

        /**
         * 初始化专题的数据
         */
        void initSubjectData();

        /**
         * 刷新
         */
        void onRefresh();

        /**
         * 轮转图片的条目点击事件
         */
        void onSubjectPicturesItemClick(int position);

        /**
         * 当用户头像被点击的时候
         */
        void onUserHeadImgClick();

        /**
         * 喜欢点击事件
         */
        void onFavoriteClick();
    }
}
