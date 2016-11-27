package com.jkb.core.contract.function.special.detail.taunted;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.contract.comment.CommentContract;

import java.util.ArrayList;
import java.util.List;

/**
 * 专题详情：吐槽墙页面协议类
 * Created by JustKiddingBaby on 2016/11/24.
 */

public interface SubjectDetailTauntedContract {

    interface View extends BaseView<Presenter>, CommentContract.View {

        /**
         * 得到动态id
         */
        int getDynamicId();

        /**
         * 设置告白标题
         */
        void setLostAndFoundTitle(String confessionTitle);

        /**
         * 设置告白主体
         */
        void setLostAndFoundContent(String confessionContent);

        /**
         * 设置告白图片
         */
        void setLostAndFoundImageUrls(List<String> confessionImageUrls);

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
         * 初始化告白的数据
         */
        void initConfessionData();

        /**
         * 刷新
         */
        void onRefresh();

        /**
         * 轮转图片的条目点击事件
         */
        void onGalleryItemClick(int position);

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
