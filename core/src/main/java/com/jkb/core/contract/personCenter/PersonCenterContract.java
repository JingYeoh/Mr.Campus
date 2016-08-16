package com.jkb.core.contract.personCenter;

import android.graphics.Bitmap;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * 个人中心的页面控制器
 * Created by JustKiddingBaby on 2016/8/15.
 */

public interface PersonCenterContract {

    interface View extends BaseView<Presenter> {

        /**
         * 设置头像
         */
        void setHeadImg(Bitmap headImg);

        /**
         * 设置昵称
         */
        void setUserName(String userName);

        /**
         * 设置名称
         */
        void setName(String name);

        /**
         * 设置用户签名
         */
        void setUserSign(String userSign);

        /**
         * 设置关注的数目
         */
        void setWatchedNum(int watched);

        /**
         * 设置粉丝的数目
         */
        void setFansNum(int fans);

        /**
         * 设置访客的数目
         */
        void setVistiorsNum(int visitors);

        /**
         * 设置没有数据时候的圈子视图
         */
        void showCircleNonDataView();

        /**
         * 显示圈子数据视图
         *
         * @param data 数据
         */
        void showCircleView(Object data);

        /**
         * 显示个人设置页面
         */
        void showPersonalSettingView();

        /**
         * 显示头像大图视图
         */
        void showHeadImgView();

        /**
         * 显示签名视图
         */
        void showSignView();

        /**
         * 显示关注视图
         */
        void showWatchedView();

        /**
         * 显示粉丝视图
         */
        void showFansView();

        /**
         * 显示访客视图
         */
        void showVisitorsView();

        /**
         * 显示全部圈子视图
         */
        void showAllCirclesView();

        /**
         * 显示我的喜欢视图
         */
        void showMyLikeView();

        /**
         * 显示全部动态视图
         */
        void showAllDynamicView();

        /**
         * 显示文动态章视图
         */
        void showArticleView();

        /**
         * 显示话题动态视图
         */
        void showTopicView();

        /**
         * 显示普通动态视图
         */
        void showNormalView();

        /**
         * 显示圈子动态视图
         */
        void showCircleView();
    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化用户数据
         */
        void initUserData();

        /**
         * 获取化圈子数据
         */
        void getCircleData();
    }
}
