package com.jkb.core.contract.personCenter;

import android.graphics.Bitmap;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.presenter.personCenter.data.CircleData;
import com.jkb.model.dataSource.first.firstlogic.FirstDataSource;
import com.jkb.model.intfc.BitmapLoadedCallback;

import java.util.List;

/**
 * 个人中心的页面控制器
 * Created by JustKiddingBaby on 2016/8/15.
 */

public interface PersonCenterContract {

    interface View extends BaseView<Presenter> {


        /**
         * 得到用户的id
         *
         * @return 用户id
         */
        int getUser_id();

        /**
         * 显示为用户自己的视图
         */
        void setSelfConfig();

        /**
         * 显示为非自己的视图
         */
        void setNonSelfConfig();

        /**
         * 显示自己的标题栏样式
         */
        void showSelfTitleStyle();

        /**
         * 显示非自己的标题栏样式
         */
        void showNonSelfTitleStyle();

        /**
         * 设置为聊天的浮动按钮
         */
        void showChatFloatBtView();

        /**
         * 设置为写的浮动按钮
         */
        void showWriteFloatBtView();

        /**
         * 设置头像
         */
        void setHeadImg(String headImg);

        /**
         * 设置背景图片
         */
        void setBackGround(String bitmap);

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
         * 显示刷新时候的视图
         */
        void showRefreshingView();

        /**
         * 隐藏刷新时候的视图
         */
        void hideRefreshingView();

        /**
         * 设置没有数据时候的圈子视图
         */
        void showCircleNonDataView();

        /**
         * 显示圈子数据视图
         *
         * @param data 数据
         */
        void setCircleViewData(List<CircleData> data);

        /**
         * 显示个人设置页面
         */
        void showPersonalSettingView();

        /**
         * 显示头像大图视图
         */
        void showHeadImgView();

        /**
         * 浮动按钮被点击的时候
         */
        void onFloatBtClick();

        /**
         * 显示签名视图
         */
        void showSignView();

        /**
         * 显示被关注的视图
         */
        void showPayAttentionView();

        /**
         * 显示没有被关注的视图
         */
        void showUnPayAttentionView();

        /**
         * 关注或者取消关注
         */
        void payAttentionOrCancle();

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
        void showDynamicArticleView();

        /**
         * 显示话题动态视图
         */
        void showDynamicTopicView();

        /**
         * 显示普通动态视图
         */
        void showDynamicNormalView();

        /**
         * 显示圈子动态视图
         */
        void showDynamicCircleView();

        /**
         * 聊天
         */
        void chat();

        /**
         * 显示写动态视图
         */
        void showWriteDynamicView();

        /**
         * 隐藏所有的视图显示
         * 调用时机：无数据或者初始化时候调用
         */
        void hideContentView();

        /**
         * 显示包含的视图
         * 调用时机：加载完成，有数据时
         */
        void showContentView();
    }

    interface Presenter extends BasePresenter {
        /**
         * 获取用户数据
         */
        void getUserData();

        /**
         * 判断用户设置标题栏样式
         */
        void judgeUserToSetTitleStyle();

        /**
         * 获取化圈子数据
         */
        void getCircleData();

        /**
         * 得到访客的id
         */
        int getCircleId(int position);

        /**
         * 请求访客接口
         */
        void visit();

        /**
         * 判断是否被关注
         */
        void verifyIfPayAttention();

        /**
         * 关注或者取消关注
         */
        void payAttentionOrCancle();

        /**
         * 刷新数据时候调用
         */
        void onRefresh();

        /**
         * 通知数据过期
         */
        void notifyDataChanged();
    }
}
