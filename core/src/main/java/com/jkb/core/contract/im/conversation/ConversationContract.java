package com.jkb.core.contract.im.conversation;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.data.info.user.UserInfo;

import java.util.List;

/**
 * 会话页面的页面协议类
 * Created by JustKiddingBaby on 2016/10/20.
 */

public interface ConversationContract {

    interface View extends BaseView<Presenter> {

        /**
         * 设置标题栏名称
         *
         * @param titleName 标题栏名称，可能是讨论组名称/用户昵称
         */
        void setTitleName(String titleName);

        /**
         * 设置用户信息
         */
        void setUserInfo(List<UserInfo> userInfo);
    }

    interface Presenter extends BasePresenter {
        /**
         * 绑定数据到视图中
         */
        void bindDataToView();

        /**
         * 刷新用户信息
         */
        void refreshUserData();

        /**
         * 请求用户数据
         *
         * @param user_id 用户id
         */
        void reqUserInfoData(int user_id);

        /**
         * 设置私聊的用户id
         */
        void setPrivateConversationUserId(int userId);
    }
}
