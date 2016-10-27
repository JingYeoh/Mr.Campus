package com.jkb.core.contract.message;

import android.support.annotation.NonNull;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

import java.util.List;

import jkb.mrcampus.db.entity.Messages;

/**
 * 动态消息的页面协议类
 * Created by JustKiddingBaby on 2016/10/26.
 */

public interface MessageDynamicContract {

    interface View extends BaseView<Presenter> {

        /**
         * 设置动态的消息
         */
        void setDynamicMessages(List<Messages> dynamicMessages);

        /**
         * 读取动态消息
         *
         * @param message_id 消息id
         */
        void readDynamicMessage(int message_id);

        /**
         * 读取动态消息
         *
         * @param message 消息对象
         */
        void readDynamicMessage(Messages message);

        /**
         * 打开文章动态页面
         *
         * @param dynamic_id 动态id
         */
        void startDynamicArticle(@NonNull int dynamic_id);

        /**
         * 打开普通动态页面
         *
         * @param dynamic_id 动态id
         */
        void startDynamicNormal(@NonNull int dynamic_id);

        /**
         * 打开话题动态页面
         *
         * @param dynamic_id 动态id
         */
        void startDynamicTopic(@NonNull int dynamic_id);
    }

    interface Presenter extends BasePresenter {

        /**
         * 动态的消息数据被点击的时候
         *
         * @param position 条目数
         */
        void onDynamicMessageItemClick(int position);

        /**
         * 设置动态的消息数据
         */
        void setDynamicMessages(List<Messages> dynamicMessages);
    }
}
