package com.jkb.core.contract.circle;

import android.graphics.Bitmap;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * 圈子首页的页面协议类
 * Created by JustKiddingBaby on 2016/8/29.
 */

public interface CircleIndexContract {


    interface View extends BaseView<Presenter> {

        /**
         * 提供圈子的id
         */
        int provideCircleId();

        /**
         * 显示视图
         */
        void showContentView();

        /**
         * 隐藏视图
         */
        void hideContentView();

        /**
         * 设置标题栏名称
         */
        void setTitleName(String titleName);

        /**
         * 设置图片
         */
        void setCirclePicture(Bitmap picture);

        /**
         * 设置圈子名称
         */
        void setCircleName(String name);

        /**
         * 设置圈子类型
         */
        void setCircleType(String circleType);

        /**
         * 设置圈子简介信息
         */
        void setCircleIntroduction(String circleIntroduction);

        /**
         * 设置圈子订阅人数
         */
        void setCircleSubscribe_count(int count);

        /**
         * 设置圈子内容总数
         */
        void setCircleOperation_count(int count);

        /**
         * 设置是否订阅的状态
         */
        void setSubscribeStatus(boolean isSubscribe);

        /**
         * 返回
         */
        void back();

        /**
         * 设置
         */
        void setting();

        /**
         * 聊天
         */
        void chat();

        /**
         * 显示大图视图
         */
        void showBigPictureView();

    }

    interface Presenter extends BasePresenter {

        /**
         * 刷新数据
         */
        void onRefresh();

        /**
         * 得到圈子id
         */
        void initCircleId();

        /**
         * 初始化圈子数据
         */
        void initCircleData();

        /**
         * 绑定数据到视图中
         */
        void bindData();

        /**
         * 请求订阅/取消订阅圈子
         */
        void subscribeOrCancleCircle();
    }
}
