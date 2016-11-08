package com.jkb.core.contract.message;

import android.support.annotation.NonNull;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.control.messageState.MessageObservable;

import java.util.List;

import jkb.mrcampus.db.entity.Messages;

/**
 * 订阅的消息页面协议类
 * Created by JustKiddingBaby on 2016/11/6.
 */

public interface MessageSubscribeContract {

    interface View extends BaseView<Presenter> {
        /**
         * 读取所有的订阅消息
         */
        void readAllSubscribeMessage();

        /**
         * 设置动态的消息
         */
        void setSubscribeMessages(List<Messages> subscribeMessages);

        /**
         * 打开个人中心页面
         */
        void startPersonCenter(@NonNull int user_id);

        /**
         * 打开圈子首页
         */
        void startCircleIndex(@NonNull int circle_id);
    }

    interface Presenter extends BasePresenter {

        /**
         * 用户被点击的时候
         *
         * @param position 条目数
         */
        void onUserItemClick(int position);

        /**
         * 当圈子被点击的时候
         *
         * @param position 条目数
         */
        void onCircleItemClick(int position);

        /**
         * 设置订阅的消息数据
         */
        void setSubscribeMessages(List<Messages> subscribeMessages);
    }
}
