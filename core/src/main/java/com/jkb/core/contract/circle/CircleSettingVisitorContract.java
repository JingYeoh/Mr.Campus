package com.jkb.core.contract.circle;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * 圈子访客设置的页面协议类
 * Created by JustKiddingBaby on 2016/11/6.
 */

public interface CircleSettingVisitorContract {

    interface View extends BaseView<Presenter> {

        /**
         * 得到圈子id
         */
        int getCircleId();

        /**
         * 设置是否设置为常用圈子的状态
         */
        void setInCommonUseStatus(boolean inCommonUseStatus);

        /**
         * 显示圈子用户列表
         *
         * @param isCircleCreator 是否是创建者
         */
        void showAttentionCircleUserList(boolean isCircleCreator);
    }

    interface Presenter extends BasePresenter {

        /**
         * 绑定信息
         */
        void bindDataToView();

        /**
         * 绑定圈子id
         */
        void initCircleId();

        /**
         * 初始化圈子数据
         */
        void initCircleData();

        /**
         * 刷新
         */
        void onRefresh();

        /**
         * 设置为常用圈子的点击事件
         */
        void onInCommonUseSwitchClick();

        /**
         * 是否是圈子创建者
         */
        boolean isCircleCreator();
    }
}
