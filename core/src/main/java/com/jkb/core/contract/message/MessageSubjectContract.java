package com.jkb.core.contract.message;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

import java.util.List;

import jkb.mrcampus.db.entity.Messages;

/**
 * 专题消息的页面适配器
 * Created by JustKiddingBaby on 2016/11/21.
 */

public interface MessageSubjectContract {

    interface View extends BaseView<Presenter> {

        /**
         * 显示刷新视图
         */
        void showRefreshingView();

        /**
         * 隐藏刷新视图
         */
        void hideRefreshingView();

        /**
         * 设置动态的消息数据
         */
        void setSubjectMessages(List<Messages> dynamicMessages);

        /**
         * 读取专题消息
         */
        void readSubjectMessage(Messages messages);

        /**
         * 打开专题详情：表白墙
         */
        void startSubjectDetailConfession(int dynamicId);

        /**
         * 打开专题详情：失物招领
         */
        void startSubjectDetailLostAndFound(int dynamicId);

        /**
         * 打开专题详情：跳蚤市场
         */
        void startSubjectDetailFleaMarket(int dynamicId);

        /**
         * 打开专题详情：吐槽墙
         */
        void startSubjectDetailTaunted(int dynamicId);

        /**
         * 打开专题详情：寻伙伴
         */
        void startSubjectDetailWantedPartner(int dynamicId);

        /**
         * 打开专题详情：求学霸
         */
        void startSubjectDetailWantedSavant(int dynamicId);

        /**
         * 打开个人详情页面
         */
        void startPersonCenter(int userId);
    }

    interface Presenter extends BasePresenter {
        /**
         * 刷新
         */
        void onRefresh();

        /**
         * 加载更多
         */
        void onLoadMore();

        /**
         * 设置动态的消息数据
         */
        void setSubjectMessages(List<Messages> dynamicMessages);

        /**
         * 专题条目被点击
         */
        void onSubjectItemClick(int position);

        /**
         * 用户条目被电击
         */
        void onUserItemClick(int position);
    }
}
