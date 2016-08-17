package com.jkb.core.contract.usersList;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * 关注的页面协议类
 * Created by JustKiddingBaby on 2016/8/17.
 */

public interface AttentionContract {

    interface View extends BaseView<Presenter> {

        /**
         * 显示用户主页
         *
         * @param user_id 用户id
         */
        void showPersonCenter(int user_id);
    }

    interface Presenter extends BasePresenter {

        /**
         * 当用户被点击的时候
         *
         * @param position 被点击的条目数
         */
        void onUserClicked(int position);
    }
}
